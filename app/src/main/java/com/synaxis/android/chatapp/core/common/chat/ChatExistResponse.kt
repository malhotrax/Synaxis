package com.synaxis.android.chatapp.core.common.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatExistResponse(
    val chatExists: Boolean = false
)