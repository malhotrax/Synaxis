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

    @Query("SELECT * FROM messages WHERE chat_id = :chatId ORDER BY created_at DESC")
    fun getMessages(chatId: String): PagingSource<Int, MessageEntity>

    @Query("SELECT * FROM messages WHERE id = :id")
    fun getMessageById(id: String): MessageEntity?

    @Query("DELETE FROM messages WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("UPDATE messages SET text = :content AND updated_at = :updatedAt WHERE id = :id ")
    suspend fun updateMessageById(id:String, content: String, updatedAt: String)

}