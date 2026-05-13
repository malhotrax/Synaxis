package com.synaxis.android.chatapp.feature.auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    val email: String,
    val password: String
)
