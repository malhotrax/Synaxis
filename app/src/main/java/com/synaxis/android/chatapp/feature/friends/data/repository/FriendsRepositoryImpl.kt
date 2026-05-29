package com.synaxis.android.chatapp.feature.friends.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.synaxis.android.chatapp.core.common.network.FriendRequest
import com.synaxis.android.chatapp.core.common.network.GetFriendRequestsResponse
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.core.database.AppDatabase
import com.synaxis.android.chatapp.core.database.remote_keys.RemoteKeysDao
import com.synaxis.android.chatapp.feature.friends.data.local.dao.FriendDao
import com.synaxis.android.chatapp.feature.friends.data.mapper.toDomain
import com.synaxis.android.chatapp.feature.friends.data.mediator.FriendsRemoteMediator
import com.synaxis.android.chatapp.feature.friends.data.remote.FriendsRemoteDatasource
import com.synaxis.android.chatapp.feature.friends.domain.model.Friend
import com.synaxis.android.chatapp.feature.friends.domain.repository.FriendsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(
    private val friendsRemoteDatasource: FriendsRemoteDatasource,
    private val friendDao: FriendDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val appDatabase: AppDatabase
) : FriendsRepository {
    override suspend fun sendFriendRequest(userId: String): ApiResult<MessageResponse> {
        return friendsRemoteDatasource.sendFriendRequest(userId)
    }

    override suspend fun acceptFriendRequest(id: String): ApiResult<MessageResponse> {
        return friendsRemoteDatasource.acceptFriendRequest(id)
    }

    override suspend fun rejectFriendRequest(id: String): ApiResult<MessageResponse> {
        return friendsRemoteDatasource.rejectFriendRequest(id)
    }

    override suspend fun deleteFriendRequest(id: String): ApiResult<MessageResponse> {
        return friendsRemoteDatasource.deleteFriendRequest(id)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getFriends(): Flow<PagingData<Friend>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            remoteMediator = FriendsRemoteMediator(
                remoteKeysDao = remoteKeysDao,
                appDatabase = appDatabase,
                friendDao = friendDao,
                friendsRemoteDatasource = friendsRemoteDatasource
            ),
            pagingSourceFactory = {
                friendDao.getFriends()
            },
        ).flow.map {
            it.map { friendEntity -> friendEntity.toDomain() }
        }
    }

    override suspend fun removeFriend(id: String): ApiResult<MessageResponse> {
        return appDatabase.withTransaction {
            friendDao.removeFriend(id)
            friendsRemoteDatasource.removeFriend(id)
        }

    }

    override suspend fun getFriendRequests(): ApiResult<GetFriendRequestsResponse> {
        return friendsRemoteDatasource.getFriendRequests()
    }
}