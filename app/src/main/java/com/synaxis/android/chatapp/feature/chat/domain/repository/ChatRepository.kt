package com.synaxis.android.chatapp.feature.chat.domain.repository

import androidx.paging.PagingData
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun createChat(chat: Chat): ApiResult<MessageResponse>
    fun getChats(): Flow<PagingData<Chat>>
}