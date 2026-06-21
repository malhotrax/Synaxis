package com.synaxis.android.chatapp.feature.message.data.mediator

import com.synaxis.android.chatapp.core.common.remote_mediator.BaseRemoteMediator
import com.synaxis.android.chatapp.core.common.remote_mediator.PagingLocalDatasource
import com.synaxis.android.chatapp.core.common.remote_mediator.PagingResponse
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.database.AppDatabase
import com.synaxis.android.chatapp.core.database.remote_keys.ItemType
import com.synaxis.android.chatapp.core.database.remote_keys.RemoteKeysDao
import com.synaxis.android.chatapp.feature.message.data.local.dao.MessageDao
import com.synaxis.android.chatapp.feature.message.data.local.entity.MessageEntity
import com.synaxis.android.chatapp.feature.message.data.remote.MessageRemoteDatasource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRemoteMediator @Inject constructor(
    private val chatId: String,
    private val appDatabase: AppDatabase,
    private val remoteKeysDao: RemoteKeysDao,
    private val messageDao: MessageDao,
    private val messageRemoteDatasource: MessageRemoteDatasource
) : BaseRemoteMediator<MessageEntity>(appDatabase = appDatabase, remoteKeysDao = remoteKeysDao) {

    override val itemType: ItemType
        get() = ItemType.MESSAGE

    override suspend fun fetch(
        cursor: String?, limit: Int
    ): ApiResult<PagingResponse<MessageEntity>> {
        return messageRemoteDatasource.getMessages(chatId,cursor, limit)
    }

    override fun entityId(entity: MessageEntity): String = entity.id

    override fun localDatasource(): PagingLocalDatasource<MessageEntity> {
        return object : PagingLocalDatasource<MessageEntity> {
            override suspend fun deleteAll() {
                messageDao.deleteAll()
            }
            override suspend fun insertAll(items: List<MessageEntity>) {
                messageDao.insertAll(items)
            }
        }
    }

}