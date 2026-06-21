package com.synaxis.android.chatapp.feature.message.presentation

data class ConversationState(
    val userId: String? = null,
    val message: String = "",
    val isLoading: Boolean = false,
    val name: String = "",
    val lastActivity: String? = null,
    val avatarUrl: String? = null,
)
