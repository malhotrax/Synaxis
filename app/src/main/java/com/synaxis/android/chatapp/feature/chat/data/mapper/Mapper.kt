package com.synaxis.android.chatapp.feature.chat.data.mapper

import com.synaxis.android.chatapp.core.common.util.DateTimeUtil.isoToMillis
import com.synaxis.android.chatapp.core.common.util.DateTimeUtil.toIso
import com.synaxis.android.chatapp.feature.chat.data.local.entity.ChatEntity
import com.synaxis.android.chatapp.feature.chat.data.remote.dto.ChatDto
import com.synaxis.android.chatapp.feature.chat.domain.model.Chat

fun ChatDto.toDomain() = Chat(
    id = this.id,
    name = this.name,
    avatarUrl = this.avatarUrl,
    lastActivity = this.lastActivity?.isoToMillis(),
    lastMessage = this.lastMessage,
    members = this.members,
    createdAt = this.createdAt.isoToMillis()
)

fun Chat.toDto() = ChatDto(
    id = this.id,
    name = this.name,
    lastActivity = this.lastActivity?.toIso(),
    lastMessage = this.lastMessage,
    avatarUrl = this.avatarUrl,
    members = this.members,
    createdAt = this.createdAt.toIso()
)

fun Chat.toEntity() = ChatEntity(
    id = this.id,
    name = this.name,
    lastActivity = this.lastActivity,
    lastMessage = this.lastMessage,
    avatarUrl = this.avatarUrl,
    members = this.members,
    createdAt = this.createdAt
)

fun ChatDto.toEntity() = ChatEntity(
    id = this.id,
    name = this.name,
    lastActivity = this.lastActivity?.isoToMillis(),
    lastMessage = this.lastMessage,
    members = this.members,
    avatarUrl = this.avatarUrl,
    createdAt = this.createdAt.isoToMillis()
)

fun ChatEntity.toDomain() = Chat(
    id = this.id,
    name = this.name,
    lastActivity = this.lastActivity,
    lastMessage = this.lastMessage,
    avatarUrl = this.avatarUrl,
    members = this.members,
    createdAt = this.createdAt
)