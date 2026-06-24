package com.synaxis.android.chatapp.feature.search

sealed class SearchEvent {
    data class QueryChanged(val query: String) : SearchEvent()
    data class SendRequest(val userId: String) : SearchEvent()
    data class SendMessage(val userId: String) : SearchEvent()

    object ClearMessages : SearchEvent()
}