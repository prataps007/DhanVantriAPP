package com.example.plantimagerecognitionusingml.model

import com.example.plantimagerecognitionusingml.utils.ChatParticipant
import java.util.UUID

data class ChatMessage(

    val id: String = UUID.randomUUID().toString(),
    val message: String,
    val timestamp: String,
    val participant: ChatParticipant
)

