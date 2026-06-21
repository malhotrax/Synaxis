package com.synaxis.android.chatapp.feature.user.presentation.update.username

data class UpdateUsernameState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val username: String = "",
)