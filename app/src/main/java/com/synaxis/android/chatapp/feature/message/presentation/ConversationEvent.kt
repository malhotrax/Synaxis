package com.synaxis.android.chatapp.feature.message.presentation

sealed class ConversationEvent {

    object SendMessage : ConversationEvent()
    data class MessageChanged(val message: String) : ConversationEvent()
    data class DeleteMessage(val id: String) : ConversationEvent()
    data class UpdateMessage(val id: String, val message: String) : ConversationEvent()
    object NavigateBack : ConversationEvent()

    object JoinChat : ConversationEvent()

    object LeaveChat : ConversationEvent()
}


sealed class ConversationUiEvent {
    object NavigateBack : ConversationUiEvent()
    data class ShowSnackBar(val message: String) : ConversationUiEvent()
}