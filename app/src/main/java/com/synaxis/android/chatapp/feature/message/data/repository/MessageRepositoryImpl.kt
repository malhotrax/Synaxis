package com.synaxis.android.chatapp.feature.message.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.synaxis.android.chatapp.core.database.AppDatabase
import com.synaxis.android.chatapp.core.database.remote_keys.RemoteKeysDao
import com.synaxis.android.chatapp.feature.message.data.MessageRemoteMediator
import com.synaxis.android.chatapp.feature.message.data.local.dao.MessageDao
import com.synaxis.android.chatapp.feature.message.data.mapper.toDomain
import com.synaxis.android.chatapp.feature.message.data.remote.MessageRemoteDatasource
import com.synaxis.android.chatapp.feature.message.domain.model.Message
import com.synaxis.android.chatapp.feature.message.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val remoteKeysDao: RemoteKeysDao,
    private val messageRemoteDatasource: MessageRemoteDatasource,
    private val messageDao: MessageDao
): MessageRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getMessages(): Flow<PagingData<Message>> {
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
                messageRemoteDatasource = messageRemoteDatasource
            ),
            pagingSourceFactory = {
                messageDao.getMessages()
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }
}