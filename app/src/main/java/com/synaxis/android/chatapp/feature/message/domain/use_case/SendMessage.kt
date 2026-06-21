package com.synaxis.android.chatapp.feature.message.domain.use_case

import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.feature.message.domain.model.Message
import com.synaxis.android.chatapp.feature.message.domain.repository.MessageRepository
import javax.inject.Inject

class SendMessage @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(content: String,chatId: String): ApiResult<Unit> {
        return messageRepository.sendMessage(
            content = content,
            chatId = chatId
        )
    }
}