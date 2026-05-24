package com.synaxis.android.chatapp.feature.chat.domain.model

import com.synaxis.android.chatapp.core.common.chat.ChatMember
import java.util.UUID
import kotlin.time.Clock


data class Chat(
    val id: String = UUID.randomUUID().toString(),
    val name: String? = null,
    val lastActivity: Long? = null,
    val lastMessage: String? = null,
    val chatMembers: List<ChatMember> = emptyList(),
    val createdAt: Long = Clock.System.now().toEpochMilliseconds()
)

