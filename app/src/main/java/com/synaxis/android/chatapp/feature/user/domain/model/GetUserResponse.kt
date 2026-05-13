package com.synaxis.android.chatapp.feature.user.domain.model

import com.synaxis.android.chatapp.core.common.user.GetUser
import kotlinx.serialization.Serializable

@Serializable
data class GetUserResponse(
    val users: List<GetUser>
)
