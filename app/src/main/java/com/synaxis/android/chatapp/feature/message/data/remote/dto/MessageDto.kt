package com.synaxis.android.chatapp.feature.message.data.remote.dto

import com.synaxis.android.chatapp.core.common.util.MessageStatus
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id: String,
    val text: String,
    val senderId: String,
    val chatId: String,
    val status: MessageStatus = MessageStatus.PENDING,
    val createdAt: String,
    val updatedAt: String? = null

)
