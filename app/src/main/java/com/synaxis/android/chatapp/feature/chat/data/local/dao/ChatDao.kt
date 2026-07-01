package com.synaxis.android.chatapp.feature.chat.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.synaxis.android.chatapp.feature.chat.data.local.entity.ChatEntity

@Dao
interface ChatDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(chat: ChatEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(chats: List<ChatEntity>)

    @Query("DELETE  FROM chat_table")
    suspend fun deleteAll()

    @Query("DELETE FROM chat_table WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM chat_table WHERE :query = '' OR name LIKE '%' || :query || '%' ORDER BY last_activity DESC")
    fun getChats(query: String): PagingSource<Int, ChatEntity>

    @Query("SELECT * FROM chat_table WHERE id = :chatId")
    suspend fun getChat(chatId: String) : ChatEntity?

    @Query("UPDATE chat_table SET last_activity = :lasActivity WHERE id = :id")
    suspend fun updateLastActivity(id: String, lasActivity: Long)


    @Query("UPDATE chat_table SET last_message = :lastMessage WHERE id = :id")
    suspend fun updateLastMessage(id: String, lastMessage: String)
}