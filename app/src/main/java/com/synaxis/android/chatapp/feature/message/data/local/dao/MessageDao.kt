package com.synaxis.android.chatapp.feature.message.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.synaxis.android.chatapp.feature.message.data.local.entity.MessageEntity

@Dao
interface MessageDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(messages: List<MessageEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun insert(message: MessageEntity)

    @Query("DELETE FROM messages")
    suspend fun deleteAll()

    @Query("SELECT * FROM messages")
    fun getMessages(): PagingSource<String, MessageEntity>

}