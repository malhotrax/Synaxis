package com.synaxis.android.chatapp.feature.user.presentation.profile.update.username.event

sealed class UsernameUpdateEvent {
    data class UsernameChanged(val username: String): UsernameUpdateEvent()
    object Update: UsernameUpdateEvent()
    object NavigateBack: UsernameUpdateEvent()
}