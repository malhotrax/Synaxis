package com.synaxis.android.chatapp.feature.friends.data.remote

import com.synaxis.android.chatapp.core.common.network.GetFriendRequestsResponse
import com.synaxis.android.chatapp.core.common.remote_mediator.PagingResponse
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.core.common.resource.flatmap
import com.synaxis.android.chatapp.core.common.resource.safeApiCall
import com.synaxis.android.chatapp.feature.friends.data.local.entity.FriendEntity
import com.synaxis.android.chatapp.feature.friends.data.mapper.toEntity
import com.synaxis.android.chatapp.feature.friends.data.remote.api.FriendsApi
import javax.inject.Inject

class FriendsRemoteDatasource @Inject constructor(
    private val friendsApi: FriendsApi
) {
    suspend fun sendFriendRequest(userId: String): ApiResult<MessageResponse> {
        return safeApiCall {
            friendsApi.sendFriendRequest(userId = userId)
        }
    }

    suspend fun acceptFriendRequest(id: String): ApiResult<MessageResponse> {
        return safeApiCall {
            friendsApi.acceptFriendRequest(id)
        }
    }

    suspend fun rejectFriendRequest(id: String): ApiResult<MessageResponse> {
        return safeApiCall {
            friendsApi.rejectFriendRequest(id)
        }
    }

    suspend fun removeFriend(id: String): ApiResult<MessageResponse> {
        return safeApiCall {
            friendsApi.removeFriend(id)
        }
    }

    suspend fun deleteFriendRequest(id: String) : ApiResult<MessageResponse> {
        return safeApiCall {
            friendsApi.deleteFriendRequest(id)
        }
    }
    suspend fun getFriends(): ApiResult<PagingResponse<FriendEntity>> {
        return safeApiCall {
            friendsApi.getFriends()
        }.flatmap { pagingResponse ->
            PagingResponse(
                items = pagingResponse.items.map { it.toEntity() },
                nextCursor = pagingResponse.nextCursor,
                hasMore = pagingResponse.hasMore
            )
        }
    }
    suspend fun getFriendRequests(): ApiResult<GetFriendRequestsResponse> {
        return safeApiCall {
            friendsApi.getFriendRequests()
        }
    }
}