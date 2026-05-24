package com.synaxis.android.chatapp.core.common.network

import com.synaxis.android.chatapp.core.common.user.GetSearchUser
import com.synaxis.android.chatapp.core.common.user.GetUser
import com.synaxis.android.chatapp.feature.friends.data.remote.dto.FriendDto
import com.synaxis.android.chatapp.feature.friends.domain.model.Friend
import kotlinx.serialization.Serializable

@Serializable
data class GetFriendResponse(
    val friends: List<FriendDto>
)