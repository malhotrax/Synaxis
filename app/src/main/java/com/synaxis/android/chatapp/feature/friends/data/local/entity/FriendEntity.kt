package com.synaxis.android.chatapp.feature.friends.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.synaxis.android.chatapp.core.common.user.GetUser

@Entity("friends")
data class FriendEntity(
    @PrimaryKey val id: String,
    @ColumnInfo("created_at") val createdAt: Long,
    val friend: GetUser
)
