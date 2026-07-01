package com.synaxis.android.chatapp.feature.chat.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.synaxis.android.chatapp.core.common.chat.ChatExistResponse
import com.synaxis.android.chatapp.core.common.chat.ChatMember
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.ErrorType
import com.synaxis.android.chatapp.core.database.AppDatabase
import com.synaxis.android.chatapp.core.database.remote_keys.RemoteKeysDao
import com.synaxis.android.chatapp.core.datastore.session.SessionDatasource
import com.synaxis.android.chatapp.core.socket.SocketManager
import com.synaxis.android.chatapp.feature.chat.data.local.dao.ChatDao
import com.synaxis.android.chatapp.feature.chat.data.mapper.toDomain
import com.synaxis.android.chatapp.feature.chat.data.mapper.toEntity
import com.synaxis.android.chatapp.feature.chat.data.mediator.ChatRemoteMediator
import com.synaxis.android.chatapp.feature.chat.data.remote.ChatRemoteDatasource
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat
import com.synaxis.android.chatapp.feature.chat.domain.repository.ChatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatRemoteDatasource: ChatRemoteDatasource,
    private val appDatabase: AppDatabase,
    private val chatDao: ChatDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val socketManager: SocketManager,
    private val sessionDatasource: SessionDatasource,
    private val appScope: CoroutineScope
) : ChatRepository {

    init {
        observeAuthAndConnect()
    }

    private fun observeAuthAndConnect() {
        sessionDatasource.accessToken().distinctUntilChanged().onEach { token ->
                if (token != null) socketManager.connect(token)
                else socketManager.disconnect()
            }.launchIn(appScope)
    }

    override suspend fun createChat(userId: String): ApiResult<Chat> {
        // if there is already a chat then return that chat
        val otherUser = ChatMember(userId)
        val members = listOf(otherUser)
        val chat = Chat(members = members)
        return appDatabase.withTransaction {
            val result = chatRemoteDatasource.createChat(chat)
            if (result is ApiResult.Success) {
                chatDao.insert(result.data.toEntity())
            }
            result
        }
    }

    override suspend fun chatExists(userId: String): ApiResult<ChatExistResponse> {
        return chatRemoteDatasource.chatExists(userId)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getChats(query: String): Flow<PagingData<Chat>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, enablePlaceholders = false, prefetchDistance = 5
            ), remoteMediator = ChatRemoteMediator(
                chatRemoteDatasource = chatRemoteDatasource,
                chatDao = chatDao,
                remoteKeysDao = remoteKeysDao,
                appDatabase = appDatabase
            ), pagingSourceFactory = {
                chatDao.getChats(query)
            }).flow.map {
            it.map { chatEntity ->
                chatEntity.toDomain()
            }
        }
    }

    override suspend fun getChat(chatId: String): ApiResult<Chat> {
        return try {
            val chat = chatDao.getChat(chatId) ?: return ApiResult.error(
                "No chat found", errorType = ErrorType.NOT_FOUND
            )
            ApiResult.success(chat.toDomain())
        } catch (e: Exception) {
            ApiResult.error(e.message)
        }
    }
}