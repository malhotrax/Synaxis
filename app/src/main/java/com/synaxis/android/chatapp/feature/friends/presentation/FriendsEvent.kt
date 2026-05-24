package com.synaxis.android.chatapp.feature.friends.presentation

sealed class FriendsEvent {
    data class OpenChat(val userId: String) : FriendsEvent()
    data class UnFriend(val userId: String) : FriendsEvent()
}