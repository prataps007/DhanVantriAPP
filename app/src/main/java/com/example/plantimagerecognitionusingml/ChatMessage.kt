package com.example.plantimagerecognitionusingml

import java.util.UUID

data class ChatMessage(

    val id: String = UUID.randomUUID().toString(),
    val message: String,
    val timestamp: String,
    val participant: ChatParticipant
)

