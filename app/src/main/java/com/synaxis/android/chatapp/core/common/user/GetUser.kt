package com.synaxis.android.chatapp.core.common.user

import kotlinx.serialization.Serializable

@Serializable
data class GetUser(
    val username: String,
    val fullName: String? = null
)
