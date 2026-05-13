package com.synaxis.android.chatapp.feature.user.presentation.profile.update.full_name.event

sealed class FullNameUpdateEvent {
    data class FullNameChanged(val fullName: String): FullNameUpdateEvent()
    object Update: FullNameUpdateEvent()
    object NavigateBack: FullNameUpdateEvent()
}
