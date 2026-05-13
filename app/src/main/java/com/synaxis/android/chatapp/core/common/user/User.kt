package com.synaxis.android.chatapp.core.common.user

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class User(
    val id: String,
    val username: String,
    val email: String,
    val fullName: String?= null,
    val avatarUrl: String?= null,
    val createdAt: String
)
