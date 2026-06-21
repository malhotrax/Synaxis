package com.synaxis.android.chatapp.feature.friends.presentation

import com.synaxis.android.chatapp.feature.chat.domain.model.Chat

sealed class FriendsEvent {
    data class OpenChat(val userId: String) : FriendsEvent()
    data class UnFriend(val userId: String) : FriendsEvent()

    data class AcceptRequest(val id: String) : FriendsEvent()

    data class RejectRequest(val id: String) : FriendsEvent()

    object LoadFriendRequest : FriendsEvent()
    object ClearError : FriendsEvent()
}

sealed class FriendsUiEvent {
    data class ShowSnackBar(val message: String) : FriendsUiEvent()
    data class NavigateToConversation(val chat: Chat) : FriendsUiEvent()
}