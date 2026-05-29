package com.synaxis.android.chatapp.feature.friends.presentation

import com.synaxis.android.chatapp.core.common.network.FriendRequest

data class FriendsState(
    val isLoading: Boolean = false,
    val requests: List<FriendRequest> = emptyList(),
    val error: String? = null,
    val successMessage: String? = null
)
