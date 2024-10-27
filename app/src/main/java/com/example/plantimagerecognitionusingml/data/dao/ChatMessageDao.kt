package com.example.plantimagerecognitionusingml.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.plantimagerecognitionusingml.data.entity.ChatMessageEntity

@Dao
interface ChatMessageDao {
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    suspend fun getAllMessages(): List<ChatMessageEntity>

    @Insert
    suspend fun insertMessage(message: ChatMessageEntity)

    @Query("DELETE FROM chat_messages")
    suspend fun deleteAllMessages()

    @Query("DELETE FROM chat_messages WHERE id = :messageId")
    suspend fun deleteMessageById(messageId: String)
}
