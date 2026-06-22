package com.synaxis.android.chatapp.feature.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat
import com.synaxis.android.chatapp.feature.chat.domain.use_case.ChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatListVM @Inject constructor(
    private val chatUseCase: ChatUseCase
): ViewModel() {


    private val _state = MutableStateFlow(ChatListState())
    val state = _state.asStateFlow()
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asSharedFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val chats: Flow<PagingData<Chat>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            chatUseCase.getChats(query)
        }
        .cachedIn(viewModelScope)
    private val _uiEvent = MutableSharedFlow<ChatListUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(e: ChatListEvent) {
        when(e) {
            is ChatListEvent.NavigateToConversation -> onNavigateToConversation(e.chat)
            is ChatListEvent.SearchQueryChanged -> onSearchQueryChanged(e.query)
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

    private fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}