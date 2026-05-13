package com.synaxis.android.chatapp.feature.user.presentation.profile.update.full_name.state

data class FullNameUpdateState(
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val fullName: String = "",
    val isLoading: Boolean = false,
    val isFullNameValid: Boolean = true
)
