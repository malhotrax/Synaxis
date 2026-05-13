package com.synaxis.android.chatapp.feature.auth.domain.model

import com.synaxis.android.chatapp.core.common.user.User

data class AuthUser(
    val refreshToken: String,
    val accessToken: String,
    val user: User
)
