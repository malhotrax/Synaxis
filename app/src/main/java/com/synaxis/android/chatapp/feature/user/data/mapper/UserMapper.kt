package com.synaxis.android.chatapp.feature.user.data.mapper

import com.synaxis.android.chatapp.feature.user.data.remote.dto.GetUserResponseDto
import com.synaxis.android.chatapp.feature.user.domain.model.GetUserResponse

fun GetUserResponseDto.toDomain() = GetUserResponse(
    users = this.users
)