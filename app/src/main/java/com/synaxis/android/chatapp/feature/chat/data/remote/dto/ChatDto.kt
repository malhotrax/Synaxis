package com.synaxis.android.chatapp.feature.chat.data.remote.dto

import com.synaxis.android.chatapp.core.common.chat.ChatMember
import kotlinx.serialization.Serializable

@Serializable
data class ChatDto(
    val id: String,
    val name: String? = null,
    val lastActivity: String? = null,
    val lastMessage: String? = null,
    val chatMembers: List<ChatMember> = emptyList(),
    val createdAt: String
)
