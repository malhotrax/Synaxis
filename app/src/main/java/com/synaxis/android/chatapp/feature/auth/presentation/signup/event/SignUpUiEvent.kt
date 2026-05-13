package com.synaxis.android.chatapp.feature.auth.presentation.signup.event

sealed class SignUpUiEvent {
    data class ShowSnackBar(val message: String? = null) : SignUpUiEvent()
    object NavigateToHome : SignUpUiEvent()
    object NavigateToLogin : SignUpUiEvent()
}