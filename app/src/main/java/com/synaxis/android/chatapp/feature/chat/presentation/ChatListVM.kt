package com.synaxis.android.chatapp.feature.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat
import com.synaxis.android.chatapp.feature.chat.domain.use_case.ChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class ChatListVM @Inject constructor(
    private val chatUseCase: ChatUseCase
): ViewModel() {
    val chats: Flow<PagingData<Chat>> = chatUseCase
        .getChats()
        .cachedIn(viewModelScope)

}