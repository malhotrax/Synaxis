package com.synaxis.android.chatapp.feature.auth.presentation.login.state

data class LoginState(
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val isLoading: Boolean = false,
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true
)
