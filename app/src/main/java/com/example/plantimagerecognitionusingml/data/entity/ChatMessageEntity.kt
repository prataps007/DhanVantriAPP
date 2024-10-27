package com.example.plantimagerecognitionusingml.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plantimagerecognitionusingml.utils.ChatParticipant

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey val id: String,
    val message: String,
    val timestamp: String,
    val participant: ChatParticipant
)

