package com.synaxis.android.chatapp.core.common.user

import kotlinx.serialization.Serializable

@Serializable
data class GetSearchUser(
    val id: String,
    val avatarUrl: String ? = null,
    val username: String,
    val fullName: String? = null,
    val isFriend: Boolean = false
)
