package com.synaxis.android.chatapp.feature.friends.domain.model

import com.synaxis.android.chatapp.core.common.user.GetUser

data class Friend(
    val id: String,
    val createdAt: Long,
    val friend: GetUser
)
