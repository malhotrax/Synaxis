package com.synaxis.android.chatapp.core.common.network

import com.synaxis.android.chatapp.core.common.user.GetUser
import com.synaxis.android.chatapp.feature.friends.data.local.entity.FriendEntity
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class FriendRequest(
    val id: String,
    val senderId: String,
    val receiverId: String,
    val status: FriendRequestStatus = FriendRequestStatus.PENDING,
    val createdAt: Instant,
    val sender: GetUser
)


enum class FriendRequestStatus {
    REJECT,
    PENDING
}