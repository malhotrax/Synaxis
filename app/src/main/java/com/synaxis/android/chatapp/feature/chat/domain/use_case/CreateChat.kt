package com.synaxis.android.chatapp.feature.chat.domain.use_case

import android.util.Log
import com.synaxis.android.chatapp.core.common.chat.ChatMember
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat
import com.synaxis.android.chatapp.feature.chat.domain.repository.ChatRepository
import java.util.UUID
import javax.inject.Inject

class CreateChat @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(userId: String): ApiResult<Chat> {
        val result = chatRepository.createChat(userId)
        Log.e("CreateChat",result.toString())
        return result
    }
}