package com.synaxis.android.chatapp.feature.user.presentation.profile.event

sealed class ProfileEvent {
    object Logout: ProfileEvent()
}