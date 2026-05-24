package com.synaxis.android.chatapp.feature.user.presentation.profile.state

data class ProfileState(
    val isLoading: Boolean = false,
    val username: String = "",
    val fullName: String ="",
    val email: String = ""
)
