package com.synaxis.android.chatapp.feature.message.domain.use_case

import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.feature.message.domain.repository.MessageRepository
import javax.inject.Inject

class UpdateMessage @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(id: String, content: String) : ApiResult<Unit> {
        return messageRepository.updateMessage(id = id,  message = content)
    }
}