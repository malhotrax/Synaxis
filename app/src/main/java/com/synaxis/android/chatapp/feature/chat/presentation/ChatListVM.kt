package com.synaxis.android.chatapp.feature.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat
import com.synaxis.android.chatapp.feature.chat.domain.use_case.ChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatListVM @Inject constructor(
    private val chatUseCase: ChatUseCase
): ViewModel() {
    val chats: Flow<PagingData<Chat>> = chatUseCase
        .getChats()
        .cachedIn(viewModelScope)

    private val _state = MutableStateFlow(ChatListState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ChatListUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(e: ChatListEvent) {
        when(e) {
            is ChatListEvent.NavigateToConversation -> onNavigateToConversation(e.chat)
        }
    }

    private fun sendUiEvent(e: ChatListUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(e)
        }
    }

    private fun onNavigateToConversation(chat: Chat) {
        sendUiEvent(ChatListUiEvent.NavigateToConversation(chat))
    }
}