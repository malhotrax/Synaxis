package com.synaxis.android.chatapp.feature.auth.presentation.signup.event

sealed class SignUpEvent {
    data class EmailChanged(val email: String) : SignUpEvent()
    data class PasswordChanged(val password: String) : SignUpEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpEvent()
    data class UsernameChanged(val username: String) : SignUpEvent()
    object TogglePasswordVisibility : SignUpEvent()
    object ToggleConfirmedPasswordVisibility : SignUpEvent()
    object SignUp: SignUpEvent()
    object NavigateToLogin : SignUpEvent()
}