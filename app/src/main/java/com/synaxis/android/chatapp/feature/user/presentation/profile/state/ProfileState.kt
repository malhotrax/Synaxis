package com.synaxis.android.chatapp.feature.user.presentation.profile.state

data class ProfileState(
    val username: String = "",
    val email: String = "",
    val fullName: String? = null,
    val avatarUrl: String? = null,
    val isLoading: Boolean,
)
