package com.synaxis.android.chatapp.feature.user.data.mapper

import com.synaxis.android.chatapp.core.common.user.User
import com.synaxis.android.chatapp.feature.user.data.local.entity.UserEntity
import com.synaxis.android.chatapp.feature.user.data.remote.dto.GetUserResponseDto
import com.synaxis.android.chatapp.feature.user.domain.model.GetUserResponse

fun GetUserResponseDto.toDomain() = GetUserResponse(
    users = this.users
)


fun UserEntity.toDomain() = User(
    id = this.id,
    fullName = this.fullName,
    username = this.username,
    email  = this.email,
    avatarUrl = this.avatarUrl,
    createdAt = this.createdAt,
)

fun User.toEntity() = UserEntity(
    id = this.id,
    fullName = this.fullName,
    username = this.username,
    email  = this.email,
    avatarUrl = this.avatarUrl,
    createdAt = this.createdAt,
)