package com.synaxis.android.chatapp.feature.user.presentation.update.full_name

sealed class UpdateFullNameEvent {
    object UpdateName: UpdateFullNameEvent()
    data class NameChanged(val name: String) : UpdateFullNameEvent()
    object  ClearMessages : UpdateFullNameEvent()

    object NavigateBack: UpdateFullNameEvent()
}

sealed class UpdateFullNameUiEvent {
    data class ShowSnackBar(val message: String) : UpdateFullNameUiEvent()
    object NavigateBack: UpdateFullNameUiEvent()
}
