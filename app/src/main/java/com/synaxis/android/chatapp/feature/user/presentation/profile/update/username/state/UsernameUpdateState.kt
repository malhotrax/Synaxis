package com.synaxis.android.chatapp.feature.user.presentation.profile.update.username.state

data class UsernameUpdateState(
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val username: String = "",
    val isLoading: Boolean = false,
    val isUsernameValid: Boolean = true
)