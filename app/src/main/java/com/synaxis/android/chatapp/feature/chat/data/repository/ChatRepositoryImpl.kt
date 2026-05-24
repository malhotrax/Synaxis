package com.synaxis.android.chatapp.feature.chat.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.core.database.AppDatabase
import com.synaxis.android.chatapp.core.database.remote_keys.RemoteKeysDao
import com.synaxis.android.chatapp.feature.chat.data.local.dao.ChatDao
import com.synaxis.android.chatapp.feature.chat.data.mapper.toDomain
import com.synaxis.android.chatapp.feature.chat.data.mapper.toEntity
import com.synaxis.android.chatapp.feature.chat.data.mediator.ChatRemoteMediator
import com.synaxis.android.chatapp.feature.chat.data.remote.ChatRemoteDatasource
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat
import com.synaxis.android.chatapp.feature.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatRemoteDatasource: ChatRemoteDatasource,
    private val appDatabase: AppDatabase,
    private val chatDao: ChatDao,
    private val remoteKeysDao: RemoteKeysDao
): ChatRepository {
    override suspend fun createChat(chat: Chat): ApiResult<MessageResponse> {
        return appDatabase.withTransaction {
            chatDao.insert(chat.toEntity())
            chatRemoteDatasource.createChat(chat)
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getChats(): Flow<PagingData<Chat>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            remoteMediator = ChatRemoteMediator(
                chatRemoteDatasource = chatRemoteDatasource,
                chatDao = chatDao,
                remoteKeysDao = remoteKeysDao,
                appDatabase = appDatabase
            ),
            pagingSourceFactory = {
                chatDao.getChats()
            }
        ).flow.map {
            it.map { chatEntity ->
                chatEntity.toDomain()
            }
        }
    }
}