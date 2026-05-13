package com.synaxis.android.chatapp.feature.user.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUsernameReq(
    val username: String
)
