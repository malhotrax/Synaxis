package com.synaxis.android.chatapp.feature.user.data.remote.dto

import com.synaxis.android.chatapp.core.common.user.GetSearchUser
import kotlinx.serialization.Serializable

@Serializable
data class GetUserResponseDto(
    val users: List<GetSearchUser>
)
