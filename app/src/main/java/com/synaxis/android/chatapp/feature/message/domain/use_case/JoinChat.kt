package com.synaxis.android.chatapp.feature.message.domain.use_case

import com.synaxis.android.chatapp.feature.message.domain.repository.MessageRepository
import javax.inject.Inject

class JoinChat @Inject constructor(
    private val messageRepository: MessageRepository
) {
    operator fun invoke(chatId: String) {
        messageRepository.joinChat(chatId)
    }
}