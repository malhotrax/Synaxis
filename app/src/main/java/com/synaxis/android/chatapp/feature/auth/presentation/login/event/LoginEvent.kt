package com.synaxis.android.chatapp.feature.auth.presentation.login.event

sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object Login : LoginEvent()
    object ToggleShowPassword: LoginEvent()
    object ForgetPassword : LoginEvent()
    object NavigateToSignUp : LoginEvent()
    object NavigateToForgetPassword : LoginEvent()
}