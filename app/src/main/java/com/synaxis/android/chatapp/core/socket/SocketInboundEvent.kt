package com.synaxis.android.chatapp.core.socket

import com.synaxis.android.chatapp.feature.message.data.remote.dto.MessageDto

sealed class SocketInboundEvent {

    data class MessageReceived(val messageDto: MessageDto) : SocketInboundEvent()
    data class MessageUpdated(val messageId: String, val content: String, val updatedAt: String) : SocketInboundEvent()
    data class MessageDeleted(val messageId: String) : SocketInboundEvent()

}