package com.synaxis.android.chatapp.feature.auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserDto(
    val email: String,
    val username: String,
    val password: String,
)