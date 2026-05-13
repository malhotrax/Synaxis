package com.synaxis.android.chatapp.core.common.resource

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    val message: String
)