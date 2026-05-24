package com.synaxis.android.chatapp.core.common.chat

import kotlinx.serialization.Serializable
import kotlin.time.Clock

@Serializable
data class ChatMember(
    val userId: String,
    val joinedAt: Long = Clock.System.now().toEpochMilliseconds()
)

