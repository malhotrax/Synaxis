package com.synaxis.android.chatapp.feature.friends.data.remote.api

import com.synaxis.android.chatapp.core.common.network.GetFriendRequestsResponse
import com.synaxis.android.chatapp.core.common.network.GetFriendResponse
import com.synaxis.android.chatapp.core.common.remote_mediator.PagingResponse
import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.feature.friends.data.remote.dto.FriendDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FriendsApi {

    @POST("friends/request/send/{userId}")
    suspend fun sendFriendRequest(
        @Path("userId") userId: String
    ): Response<MessageResponse>

    @POST("friends/request/accept/{id}")
    suspend fun acceptFriendRequest(
        @Path("id") id: String
    ): Response<MessageResponse>

    @POST("friends/request/reject/{id}")
    suspend fun rejectFriendRequest(
        @Path("id") id: String
    ): Response<MessageResponse>

    @DELETE("friends/request/delete/{id}")
    suspend fun deleteFriendRequest(
        @Path("id") id: String
    ): Response<MessageResponse>

    @DELETE("friends/remove/{friendId}")
    suspend fun removeFriend(
        @Path("id") friendId: String
    ): Response<MessageResponse>

    @GET("friends")
    suspend fun getFriends(): Response<PagingResponse<FriendDto>>

    @GET("friends/requests")
    suspend fun getFriendRequests(): Response<GetFriendRequestsResponse>
}