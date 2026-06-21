package com.synaxis.android.chatapp.feature.user.presentation

import com.synaxis.android.chatapp.core.common.user.User

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User = User.getEmptyUser(),
    val errorMessage: String? = null,
    val successMessage: String? = null
)