package com.synaxis.android.chatapp.core.socket

import com.synaxis.android.chatapp.feature.message.data.remote.dto.MessageDto

sealed class SocketOutboundEvent {
    data class SendMessage(val messageDto: MessageDto) : SocketOutboundEvent()
    data class UpdateMessage(val messageId: String, val content: String) : SocketOutboundEvent()
    data class DeleteMessage(val messageId: String) : SocketOutboundEvent()

    data class JoinChat(val chatId: String) : SocketOutboundEvent()
    data class LeaveChat(val chatId: String) : SocketOutboundEvent()
}