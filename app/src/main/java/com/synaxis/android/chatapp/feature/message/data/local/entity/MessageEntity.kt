package com.synaxis.android.chatapp.feature.message.data.local.entity

import androidx.room.Entity
import com.synaxis.android.chatapp.core.common.util.MessageStatus


@Entity("messages")
data class MessageEntity(
    val id: String,
    val text: String,
    val senderId: String,
    val chatId: String,
    val status: MessageStatus = MessageStatus.PENDING,
    val createdAt: Long,
    val updatedAt: Long?
)
