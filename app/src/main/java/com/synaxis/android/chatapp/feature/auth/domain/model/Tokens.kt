package com.synaxis.android.chatapp.feature.auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Tokens(
    val accessToken: String,
    val refreshToken: String
)
