package com.synaxis.android.chatapp.feature.message.data.remote.api

import com.synaxis.android.chatapp.core.common.remote_mediator.PagingResponse
import com.synaxis.android.chatapp.feature.message.data.remote.dto.MessageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MessageApi {

    @GET("message")
    suspend fun getMessages(
        @Query("cursor") cursor: String?,
        @Query("limit") limit: Int
    ): Response<PagingResponse<MessageDto>>
}