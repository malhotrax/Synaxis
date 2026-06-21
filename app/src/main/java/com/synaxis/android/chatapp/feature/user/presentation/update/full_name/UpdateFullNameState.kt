package com.synaxis.android.chatapp.feature.user.presentation.update.full_name


data class UpdateFullNameState(
    val fullName: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)