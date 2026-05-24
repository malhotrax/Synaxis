package com.synaxis.android.chatapp.feature.chat.data.mediator

import androidx.paging.ExperimentalPagingApi
import com.synaxis.android.chatapp.core.common.remote_mediator.BaseRemoteMediator
import com.synaxis.android.chatapp.core.common.remote_mediator.PagingLocalDatasource
import com.synaxis.android.chatapp.core.common.remote_mediator.PagingResponse
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.database.AppDatabase
import com.synaxis.android.chatapp.core.database.remote_keys.ItemType
import com.synaxis.android.chatapp.core.database.remote_keys.RemoteKeysDao
import com.synaxis.android.chatapp.feature.chat.data.local.dao.ChatDao
import com.synaxis.android.chatapp.feature.chat.data.local.entity.ChatEntity
import com.synaxis.android.chatapp.feature.chat.data.remote.ChatRemoteDatasource
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class ChatRemoteMediator @Inject constructor(
    private val chatRemoteDatasource: ChatRemoteDatasource,
    private val chatDao: ChatDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val appDatabase: AppDatabase
) : BaseRemoteMediator<ChatEntity>(remoteKeysDao = remoteKeysDao, appDatabase = appDatabase) {
    override val itemType: ItemType
        get() = ItemType.CHAT

    override suspend fun fetch(
        cursor: String?,
        limit: Int
    ): ApiResult<PagingResponse<ChatEntity>> {
        return chatRemoteDatasource.getChats(cursor, limit)
    }

    override fun entityId(entity: ChatEntity): String = entity.id

    override fun localDatasource(): PagingLocalDatasource<ChatEntity> =
        object : PagingLocalDatasource<ChatEntity> {
            override suspend fun deleteAll() {
                chatDao.deleteAll()
            }

            override suspend fun insertAll(items: List<ChatEntity>) {
                chatDao.insertAll(items)
            }
        }

}