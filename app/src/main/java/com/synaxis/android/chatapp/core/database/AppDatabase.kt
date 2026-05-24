package com.synaxis.android.chatapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.synaxis.android.chatapp.core.database.converters.Converters
import com.synaxis.android.chatapp.core.database.remote_keys.RemoteKeys
import com.synaxis.android.chatapp.core.database.remote_keys.RemoteKeysDao
import com.synaxis.android.chatapp.feature.chat.data.local.dao.ChatDao
import com.synaxis.android.chatapp.feature.chat.data.local.entity.ChatEntity
import com.synaxis.android.chatapp.feature.friends.data.local.dao.FriendDao
import com.synaxis.android.chatapp.feature.friends.data.local.entity.FriendEntity


@Database(
    entities = [
        ChatEntity::class,
        RemoteKeys::class,
        FriendEntity::class
    ], exportSchema = false, version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun remoteKeyDao() : RemoteKeysDao
    abstract fun friendDao() : FriendDao
}