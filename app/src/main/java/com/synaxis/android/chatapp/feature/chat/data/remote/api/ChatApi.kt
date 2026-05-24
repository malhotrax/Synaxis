package com.synaxis.android.chatapp.feature.chat.data.remote.api

import com.synaxis.android.chatapp.core.common.remote_mediator.PagingResponse
import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.feature.chat.data.local.entity.ChatEntity
import com.synaxis.android.chatapp.feature.chat.data.remote.dto.ChatDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatApi {
    @POST("chat")
    suspend fun createChat(
        @Body chatDto: ChatDto
    ): Response<MessageResponse>

    @GET("chat")
    suspend fun getChats(
        @Query("cursor") cursor: String?,
        @Query("limit") limit: Int
    ): Response<PagingResponse<ChatEntity>>
}