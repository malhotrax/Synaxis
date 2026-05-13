package com.synaxis.android.chatapp.feature.auth.data.remote.dto

import com.synaxis.android.chatapp.core.common.user.User
import kotlinx.serialization.Serializable


@Serializable
data class AuthUserDto(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)
