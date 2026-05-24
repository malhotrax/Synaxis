package com.synaxis.android.chatapp.core.database.remote_keys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey @ColumnInfo("item_id") val itemId: String,
    @ColumnInfo("item_type") val itemType: ItemType,
    @ColumnInfo("prev_cursor") val prevCursor: String? = null,
    @ColumnInfo("next_cursor") val nextCursor: String? = null
)

enum class ItemType {
    CHAT,
    FRIEND,
    MESSAGE
}