package com.synaxis.android.chatapp.feature.auth.presentation.login.event

sealed class LoginUiEvent {
    object NavigateToHome : LoginUiEvent()
    object NavigateToSignUp : LoginUiEvent()
    object NavigateToForgetPassword : LoginUiEvent()
    data class ShowSnackBar(val message: String? = null) : LoginUiEvent()
}