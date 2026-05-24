package com.synaxis.android.chatapp.core.common.network

import kotlinx.serialization.Serializable

@Serializable
data class GetFriendRequestsResponse(
    val requests: List<FriendRequest>
)