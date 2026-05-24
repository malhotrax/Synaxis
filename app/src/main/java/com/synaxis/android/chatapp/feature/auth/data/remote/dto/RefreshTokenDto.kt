package com.synaxis.android.chatapp.feature.auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenDto(
    val refreshToken: String
)