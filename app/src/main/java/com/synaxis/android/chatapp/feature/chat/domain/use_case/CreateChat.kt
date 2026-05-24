package com.synaxis.android.chatapp.feature.chat.domain.use_case

import com.synaxis.android.chatapp.core.common.resource.ApiResult
import com.synaxis.android.chatapp.core.common.resource.MessageResponse
import com.synaxis.android.chatapp.feature.chat.domain.repository.ChatRepository
import javax.inject.Inject

class CreateChat @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(): ApiResult<MessageResponse> {

    }
}