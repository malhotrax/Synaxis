package com.synaxis.android.chatapp.feature.user.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user")
data class UserEntity(
    @PrimaryKey val id: String,
    val username: String,
    val email: String,
    @ColumnInfo("full_name") val fullName: String? = null,
    @ColumnInfo("avatar_url") val avatarUrl: String? = null,
    @ColumnInfo("created_at") val createdAt: String
)
