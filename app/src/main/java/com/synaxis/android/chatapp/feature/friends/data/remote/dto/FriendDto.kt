package com.synaxis.android.chatapp.feature.friends.data.remote.dto

import com.synaxis.android.chatapp.core.common.user.GetUser
import kotlinx.serialization.Serializable

@Serializable
data class FriendDto(
    val id: String,
    val createdAt: String,
    val friend: GetUser
)

