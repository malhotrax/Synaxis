package com.synaxis.android.chatapp.feature.message.domain.model

import com.synaxis.android.chatapp.core.common.util.MessageStatus
import java.time.Instant
import java.util.UUID


data class Message(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val senderId: String,
    val chatId: String,
    val status: MessageStatus = MessageStatus.PENDING,
    val createdAt: Long = Instant.now().toEpochMilli(),
    val updatedAt: Long? = null
)

