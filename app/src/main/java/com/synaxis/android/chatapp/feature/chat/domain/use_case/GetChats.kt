package com.synaxis.android.chatapp.feature.chat.domain.use_case

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat
import com.synaxis.android.chatapp.feature.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChats @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(): Flow<PagingData<Chat>> {
        return chatRepository.getChats()
    }
}