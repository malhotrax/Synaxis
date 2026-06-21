package com.synaxis.android.chatapp.feature.message.domain.use_case

import androidx.paging.PagingData
import com.synaxis.android.chatapp.feature.message.domain.model.Message
import com.synaxis.android.chatapp.feature.message.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessages @Inject constructor(
    private val messageRepository: MessageRepository
) {

    operator fun invoke(chatId: String): Flow<PagingData<Message>> {
        return messageRepository.getMessages(chatId)
    }
}