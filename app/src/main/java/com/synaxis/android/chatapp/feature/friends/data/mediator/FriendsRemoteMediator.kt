package com.synaxis.android.chatapp.feature.friends.data.mediator

import com.synaxis.android.chatapp.core.common.remote_mediator.BaseRemoteMediator
import com.synaxis.android.chatapp.core.common.remote_mediator.PagingLocalDatasource
import com.synaxis.android.chatapp.core.common.remote_mediator.PagingResponse
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.database.AppDatabase
import com.synaxis.android.chatapp.core.database.remote_keys.ItemType
import com.synaxis.android.chatapp.core.database.remote_keys.RemoteKeysDao
import com.synaxis.android.chatapp.feature.friends.data.local.dao.FriendDao
import com.synaxis.android.chatapp.feature.friends.data.local.entity.FriendEntity
import com.synaxis.android.chatapp.feature.friends.data.remote.FriendsRemoteDatasource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendsRemoteMediator @Inject constructor(
    private val remoteKeysDao: RemoteKeysDao,
    private val appDatabase: AppDatabase,
    private val friendDao: FriendDao,
    private val friendsRemoteDatasource: FriendsRemoteDatasource
) : BaseRemoteMediator<FriendEntity>(appDatabase = appDatabase, remoteKeysDao = remoteKeysDao) {
    override val itemType: ItemType
        get() = ItemType.FRIEND

    override suspend fun fetch(
        cursor: String?,
        limit: Int
    ): ApiResult<PagingResponse<FriendEntity>> {
        return friendsRemoteDatasource.getFriends()
    }

    override fun entityId(entity: FriendEntity): String = entity.id

    override fun localDatasource(): PagingLocalDatasource<FriendEntity> =
        object : PagingLocalDatasource<FriendEntity> {
            override suspend fun deleteAll() {
                friendDao.deleteAll()
            }
            override suspend fun insertAll(items: List<FriendEntity>) {
                friendDao.insertAll(items)
            }
        }

}