package com.synaxis.android.chatapp.feature.chat.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.synaxis.android.chatapp.core.common.chat.ChatMember


@Entity(tableName = "chat_table")
data class ChatEntity(
    @PrimaryKey val id: String,
    val name: String? = null,
    @ColumnInfo("last_activity") val lastActivity: Long? = null,
    @ColumnInfo("last_message") val lastMessage: String? = null,
    @ColumnInfo("chat_members") val chatMembers: List<ChatMember>,
    @ColumnInfo("created_at") val createdAt: Long
)
