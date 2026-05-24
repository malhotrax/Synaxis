package com.synaxis.android.chatapp.feature.chat.data.remote

import com.synaxis.android.chatapp.core.common.remote_mediator.PagingResponse
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.core.common.resource.flatmap
import com.synaxis.android.chatapp.core.common.resource.safeApiCall
import com.synaxis.android.chatapp.feature.chat.data.local.entity.ChatEntity
import com.synaxis.android.chatapp.feature.chat.data.mapper.toDto
import com.synaxis.android.chatapp.feature.chat.data.mapper.toEntity
import com.synaxis.android.chatapp.feature.chat.data.remote.api.ChatApi
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat
import javax.inject.Inject

class ChatRemoteDatasource  @Inject constructor(
    private val chatApi: ChatApi
) {
    suspend fun createChat(chat: Chat): ApiResult<MessageResponse> {
        return safeApiCall {
            chatApi.createChat(chat.toDto())
        }
    }

    suspend fun getChats(cursor: String? = null, limit: Int = 10): ApiResult<PagingResponse<ChatEntity>> {
        return safeApiCall {
           chatApi.getChats(cursor, limit)
        }
    }
}