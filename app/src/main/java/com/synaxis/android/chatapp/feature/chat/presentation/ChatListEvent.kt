package com.synaxis.android.chatapp.feature.chat.presentation

import com.synaxis.android.chatapp.feature.chat.domain.model.Chat

sealed class ChatListEvent {
    data class NavigateToConversation(val chat: Chat) : ChatListEvent()
    data class SearchQueryChanged(val query: String) : ChatListEvent()
}


sealed class ChatListUiEvent {
    data class NavigateToConversation(val chat: Chat) : ChatListUiEvent()
}