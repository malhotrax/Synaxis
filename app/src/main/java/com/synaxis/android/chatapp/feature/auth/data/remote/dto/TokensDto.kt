package com.synaxis.android.chatapp.feature.auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TokensDto(
    val accessToken: String,
    val refreshToken: String
)