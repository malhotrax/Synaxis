package com.synaxis.android.chatapp.feature.message.domain.use_case

data class MessageUseCase(
    val getMessages: GetMessages,
    val sendMessage: SendMessage,
    val updateMessage: UpdateMessage,
    val deleteMessage: DeleteMessage,
    val joinChat: JoinChat,
    val leaveChat: LeaveChat
)