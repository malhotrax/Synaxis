package com.synaxis.android.chatapp.feature.chat.domain.use_case

import com.synaxis.android.chatapp.core.common.chat.ChatExistResponse
import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.feature.chat.domain.repository.ChatRepository
import javax.inject.Inject

class ChatExists @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(userId: String): ApiResult<ChatExistResponse>  {
        return chatRepository.chatExists(userId)
    }
}