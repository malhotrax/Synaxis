package com.synaxis.android.chatapp.core.common.user

import kotlinx.serialization.Serializable



@Serializable
data class GetUser(
    val id: String,
    val username: String,
    val fullName: String? = null,
    val createdAt: String,
    val avatarUrl: String? = null
)