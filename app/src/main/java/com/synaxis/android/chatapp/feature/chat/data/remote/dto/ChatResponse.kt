package com.synaxis.android.chatapp.feature.chat.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    val chat: ChatDto
)