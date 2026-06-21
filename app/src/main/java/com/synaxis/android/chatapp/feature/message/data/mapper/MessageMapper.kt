package com.synaxis.android.chatapp.feature.message.data.mapper

import com.synaxis.android.chatapp.core.common.util.DateTimeUtil.isoToMillis
import com.synaxis.android.chatapp.core.common.util.DateTimeUtil.toIso
import com.synaxis.android.chatapp.feature.message.data.local.dao.MessageDao
import com.synaxis.android.chatapp.feature.message.data.local.entity.MessageEntity
import com.synaxis.android.chatapp.feature.message.data.remote.dto.MessageDto
import com.synaxis.android.chatapp.feature.message.domain.model.Message

fun MessageDto.toEntity(): MessageEntity = MessageEntity(
    id = this.id,
    text = this.text,
    senderId = this.senderId,
    chatId = this.chatId,
    status = this.status,
    createdAt = this.createdAt.isoToMillis(),
    updatedAt = this.updatedAt?.isoToMillis()
)

fun Message.toDto(): MessageDto = MessageDto(
    id = this.id,
    text = this.text,
    senderId = this.senderId,
    chatId = this.chatId,
    status = this.status,
    createdAt = this.createdAt.toIso(),
    updatedAt = this.updatedAt?.toIso()
)

fun MessageEntity.toDomain(): Message = Message(
    id = this.id,
    text = this.text,
    senderId = this.senderId,
    chatId = this.chatId,
    status = this.status,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun Message.toEntity(): MessageEntity = MessageEntity(
    id = this.id,
    text = this.text,
    senderId = this.senderId,
    chatId = this.chatId,
    status = this.status,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)