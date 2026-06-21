package com.synaxis.android.chatapp.core.common.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ChatMember(
    @SerialName("id") val userId: String
)