package com.synaxis.android.chatapp.feature.friends.domain.repository

import androidx.paging.PagingData
import com.synaxis.android.chatapp.core.common.network.GetFriendRequestsResponse
import com.synaxis.android.chatapp.core.common.network.GetFriendResponse
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.feature.friends.domain.model.Friend
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {
    suspend fun sendFriendRequest(userId: String): ApiResult<MessageResponse>
    suspend fun acceptFriendRequest(id: String): ApiResult<MessageResponse>
    suspend fun rejectFriendRequest(id: String): ApiResult<MessageResponse>
    suspend fun deleteFriendRequest(id: String): ApiResult<MessageResponse>
    fun getFriends(): Flow<PagingData<Friend>>
    suspend fun removeFriend(id: String): ApiResult<MessageResponse>
    suspend fun getFriendRequests(): ApiResult<GetFriendRequestsResponse>
}