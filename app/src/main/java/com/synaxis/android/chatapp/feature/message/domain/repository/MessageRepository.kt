package com.synaxis.android.chatapp.feature.message.domain.repository

import androidx.paging.PagingData
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat
import com.synaxis.android.chatapp.feature.message.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    fun getMessages(chatId: String): Flow<PagingData<Message>>
    suspend fun sendMessage(content: String, chatId: String): ApiResult<Unit>
    suspend fun updateMessage(id: String, message: String): ApiResult<Unit>
    suspend fun deleteMessage(id: String): ApiResult<Unit>

    fun joinChat(chatId: String)

    fun leaveChat(chatId: String)
}