package com.synaxis.android.chatapp.feature.friends.data.mapper

import com.synaxis.android.chatapp.core.common.util.DateTimeUtil.isoToMillis
import com.synaxis.android.chatapp.core.common.util.DateTimeUtil.toIso
import com.synaxis.android.chatapp.feature.friends.data.local.entity.FriendEntity
import com.synaxis.android.chatapp.feature.friends.data.remote.dto.FriendDto
import com.synaxis.android.chatapp.feature.friends.domain.model.Friend

fun Friend.toEntity() = FriendEntity(
    id = this.id,
    createdAt = this.createdAt,
    friend = this.friend
)

fun Friend.toDto() = FriendDto(
    id = this.id,
    createdAt = this.createdAt.toIso(),
    friend = this.friend
)

fun FriendDto.toEntity() = FriendEntity(
    id = this.id,
    createdAt = this.createdAt.isoToMillis(),
    friend = this.friend
)

fun FriendEntity.toDomain() = Friend(
    id = this.id,
    createdAt = this.createdAt,
    friend = this.friend
)