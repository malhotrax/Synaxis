package com.synaxis.android.chatapp.feature.user.presentation.update.username

sealed class UpdateUsernameEvent {
    data class UsernameChanged(val username: String) : UpdateUsernameEvent()
    object UpdateUsername : UpdateUsernameEvent()
    object ClearErrorMessage: UpdateUsernameEvent()
}

sealed class UpdateUsernameUiEvent {
    data class ShowSnackBar(val message: String) : UpdateUsernameUiEvent()
}