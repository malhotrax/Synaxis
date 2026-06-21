package com.synaxis.android.chatapp.feature.message.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.synaxis.android.chatapp.core.common.util.MessageStatus


@Entity("messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    val text: String,
    @ColumnInfo("sender_id") val senderId: String,
    @ColumnInfo("chat_id") val chatId: String,
    val status: MessageStatus = MessageStatus.PENDING,
    @ColumnInfo("created_at") val createdAt: Long,
    @ColumnInfo("updated_at") val updatedAt: Long?
)
