package com.synaxis.android.chatapp.core.common.user

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class GetUser(
    val id: String,
    val username: String,
    val fullName: String? = null,
    val createdAt: Instant,
    val avatarUrl: String? = null
)