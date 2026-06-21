package com.synaxis.android.chatapp.feature.chat.domain.repository

import androidx.paging.PagingData
import com.synaxis.android.chatapp.core.common.chat.ChatExistResponse
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun createChat(userId: String): ApiResult<Chat>
    suspend fun chatExists(userId: String): ApiResult<ChatExistResponse>
    fun getChats(): Flow<PagingData<Chat>>

    suspend fun getChat(chatId: String): ApiResult<Chat>
}