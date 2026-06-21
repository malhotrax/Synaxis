package com.synaxis.android.chatapp.feature.user.presentation

sealed class ProfileEvent {
    object LogOut: ProfileEvent()
    object DeleteAccount: ProfileEvent()
    object NavigateToUpdateFullName: ProfileEvent()
    object NavigateToUpdateUsername: ProfileEvent()
    object LoadUser: ProfileEvent()
}


sealed class ProfileUiEvent {
    object NavigateToUpdateFullNameScreen : ProfileUiEvent()
    object NavigateToUpdateUsernameScreen : ProfileUiEvent()
}