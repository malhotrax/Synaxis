package com.synaxis.android.chatapp.feature.message.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.synaxis.android.chatapp.core.common.notification.MessageNotificationService
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.util.MessageStatus
import com.synaxis.android.chatapp.core.database.AppDatabase
import com.synaxis.android.chatapp.core.database.remote_keys.RemoteKeysDao
import com.synaxis.android.chatapp.core.datastore.session.SessionDatasource
import com.synaxis.android.chatapp.core.socket.SocketInboundEvent
import com.synaxis.android.chatapp.core.socket.SocketManager
import com.synaxis.android.chatapp.core.socket.SocketOutboundEvent
import com.synaxis.android.chatapp.feature.chat.data.local.dao.ChatDao
import com.synaxis.android.chatapp.feature.message.data.local.dao.MessageDao
import com.synaxis.android.chatapp.feature.message.data.mapper.toDomain
import com.synaxis.android.chatapp.feature.message.data.mapper.toDto
import com.synaxis.android.chatapp.feature.message.data.mapper.toEntity
import com.synaxis.android.chatapp.feature.message.data.mediator.MessageRemoteMediator
import com.synaxis.android.chatapp.feature.message.data.remote.MessageRemoteDatasource
import com.synaxis.android.chatapp.feature.message.data.remote.dto.MessageDto
import com.synaxis.android.chatapp.feature.message.data.remote.dto.NotificationMessageDto
import com.synaxis.android.chatapp.feature.message.domain.model.Message
import com.synaxis.android.chatapp.feature.message.domain.repository.MessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val remoteKeysDao: RemoteKeysDao,
    private val messageRemoteDatasource: MessageRemoteDatasource,
    private val messageDao: MessageDao,
    private val socketManager: SocketManager,
    private val appScope: CoroutineScope,
    private val sessionDatasource: SessionDatasource,
    private val messageNotificationService: MessageNotificationService,
    private val chatDao: ChatDao
) : MessageRepository {
    init {
        observeSocketEvent()
    }

    private fun observeSocketEvent() {
        appScope.launch {
            socketManager.socketInboundEvent.collect { e ->
                runCatching {
                    when (e) {
                        is SocketInboundEvent.MessageDeleted -> handleMessageDelete(e.messageId)
                        is SocketInboundEvent.MessageReceived -> handleMessageReceived(e.messageDto)
                        is SocketInboundEvent.MessageUpdated -> handleMessageUpdated(
                            e.messageId,
                            e.content,
                            e.updatedAt
                        )

                        is SocketInboundEvent.NotificationNewMessage -> handleNotificationOfNewMessage(e.notificationMessageDto)
                    }
                }.onFailure { error ->
                    Log.e("MessageRepository", "Inbound sync execution failure : $error")
                }
            }
        }
    }

    private suspend fun handleNotificationOfNewMessage(notificationMessageDto: NotificationMessageDto) {
        Log.d("MessageRepoImpl", "Notification event")
        if(messageDao.getMessageById(notificationMessageDto.messageDto.id) != null) return
        val messageEntity = notificationMessageDto.messageDto.toEntity()
        messageDao.insert(messageEntity)
        messageNotificationService.showNotification(notificationMessageDto)
    }

    private suspend fun handleMessageReceived(messageDto: MessageDto) {
        if(messageDao.getMessageById(messageDto.id) != null) return
        chatDao.updateLastMessage(id = messageDto.chatId, lastMessage = messageDto.text)
        chatDao.updateLastActivity(id = messageDto.chatId, lasActivity = Instant.now().toEpochMilli())
        messageDao.insert(messageDto.toEntity())
    }

    private suspend fun handleMessageUpdated(id: String, content: String, updatedAt: String) {
        messageDao.updateMessageById(id = id, content = content, updatedAt = updatedAt)
    }

    private suspend fun handleMessageDelete(id: String) {

    }
    @OptIn(ExperimentalPagingApi::class)
    override fun getMessages(chatId: String): Flow<PagingData<Message>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                enablePlaceholders = false,
            ),
            remoteMediator = MessageRemoteMediator(
                appDatabase = appDatabase,
                remoteKeysDao = remoteKeysDao,
                messageDao = messageDao,
                messageRemoteDatasource = messageRemoteDatasource,
                chatId = chatId
            ),
            pagingSourceFactory = {
                messageDao.getMessages(chatId)
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun sendMessage(
        content: String,
        chatId: String
    ): ApiResult<Unit> {
        return try {
            val userId = sessionDatasource.userId().first { it != null} ?: return ApiResult.error("No user id is found")
            val message = Message(
                text = content,
                chatId = chatId,
                senderId = userId,
                status = MessageStatus.SENT
            )
            messageDao.insert(message.toEntity())

            socketManager.emit(SocketOutboundEvent.SendMessage(message.toDto()))
            ApiResult.success(Unit)
        } catch (e: Exception) {
            ApiResult.error(e.message)
        }
    }

    override suspend fun updateMessage(
        id: String,
        message: String
    ): ApiResult<Unit> {
        return try {
            messageDao.updateMessageById(id, message, updatedAt = Instant.now().toString())
            socketManager.emit(SocketOutboundEvent.UpdateMessage(id, message))
            ApiResult.success(Unit)
        } catch (e: Exception) {
            ApiResult.error(e.message)
        }
    }

    override suspend fun deleteMessage(id: String): ApiResult<Unit> {
        return try {
            messageDao.deleteById(id)
            socketManager.emit(SocketOutboundEvent.DeleteMessage(id))
            ApiResult.success(Unit)
        } catch (e: Exception) {
            ApiResult.error(e.message)
        }
    }

    override fun joinChat(chatId: String) {
        socketManager.emit(SocketOutboundEvent.JoinChat(chatId))
    }

    override fun leaveChat(chatId: String){
        socketManager.emit(SocketOutboundEvent.LeaveChat(chatId))
    }

}