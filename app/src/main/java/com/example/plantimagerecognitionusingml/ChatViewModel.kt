package com.example.plantimagerecognitionusingml

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.plantimagerecognitionusingml.Model.model
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<List<ChatMessage>>(emptyList())
    val uiState: StateFlow<List<ChatMessage>> = _uiState.asStateFlow()

    private val chat = model.startChat(
        history = listOf(
            content("model") { text("Hi! How can I help you today?") }
        )
    )

    init {
        // Initialize chat history
        _uiState.value = chat.history.map { content ->
            ChatMessage(
                message = content.parts.first().asTextOrNull() ?: "",
                timestamp = "",
                participant = if (content.role == "user") ChatParticipant.USER else ChatParticipant.AI
            )
        }
    }

     suspend fun sendMessage(userMessage: String, isPlantDetailQuery: Boolean = false) {
         val timestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

         // Add the user's message to the UI state
         val userMessageEntry = ChatMessage(
             message = userMessage,
             timestamp = timestamp,
             participant = ChatParticipant.USER
         )
         _uiState.value = _uiState.value + userMessageEntry

         // Send the user message and get the AI response
         val response = chat.sendMessage(content("user") { text(userMessage) })
         //Log.d("API Response", response.toString()) // Log the response for inspection
         val aiResponse = response.candidates.first().content.parts.first().asTextOrNull()
             ?: "Oops, something went wrong!"



         // Start building the AI message word by word
         val words = aiResponse.split(" ")

         Log.d("API Response", words.toString().trim{it <= ' '})

         var currentMessage = ""
         val aiMessageEntry = ChatMessage(
             message = currentMessage,
             timestamp = timestamp,
             participant = ChatParticipant.AI
         )

         // If the query is from PlantDetailActivity, return the response immediately
         if (isPlantDetailQuery) {
             _uiState.value = _uiState.value + aiMessageEntry
             return
         }


         // Add a placeholder AI message entry (empty) to the UI state
         _uiState.value = _uiState.value + aiMessageEntry

         // Gradually update the AI message word by word
         for (word in words) {
             currentMessage += "$word "
             val updatedAiMessageEntry = ChatMessage(
                 message = currentMessage.trim(),
                 timestamp = timestamp,
                 participant = ChatParticipant.AI
             )

             //Log.d("API Response", word)

             // Update the last message in the UI state (which is the AI's message)
             _uiState.value = _uiState.value.dropLast(1) + updatedAiMessageEntry

             delay(100) // Adjust delay to control typing speed
         }


        //_uiState.value = _uiState.value + aiMessage
    }
}
