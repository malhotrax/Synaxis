package com.synaxis.android.chatapp.feature.auth.presentation.signup.state

data class SignUpState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val username: String = "",
    val showPassword: Boolean = false,
    val showConfirmedPassword: Boolean = false,
    val isLoading: Boolean = false,
    val isEmailValid: Boolean = true,
    val isConfirmPasswordValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isUsernameValid: Boolean = true
)
