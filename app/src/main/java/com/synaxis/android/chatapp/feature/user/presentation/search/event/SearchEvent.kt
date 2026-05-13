package com.synaxis.android.chatapp.feature.user.presentation.search.event

sealed class SearchEvent {
    data class QueryChanged(val query: String): SearchEvent()
    object Search: SearchEvent()
    data class SendFriendRequest(val username: String) : SearchEvent()
}