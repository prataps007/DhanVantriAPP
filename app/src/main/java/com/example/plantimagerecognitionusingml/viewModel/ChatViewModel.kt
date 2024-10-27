package com.example.plantimagerecognitionusingml.viewModel

import android.util.Log
import com.example.plantimagerecognitionusingml.utils.Model.model
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantimagerecognitionusingml.model.ChatMessage
import com.example.plantimagerecognitionusingml.utils.ChatParticipant
import com.example.plantimagerecognitionusingml.data.database.ChatDatabase
import com.example.plantimagerecognitionusingml.data.entity.ChatMessageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

//class ChatViewModel : ViewModel() {
class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<List<ChatMessage>>(emptyList())
    val uiState: StateFlow<List<ChatMessage>> = _uiState.asStateFlow()

    // Use the application context to get the database instance
    private val chatDao = ChatDatabase.getDatabase(application).chatMessageDao()

    private val chat = model.startChat(
        history = listOf(
            content("model") { text("Hi! How can I help you today?") }
        )
    )

    init {

        // Load previous chat messages
        viewModelScope.launch(Dispatchers.IO) {
            val chatHistory = chatDao.getAllMessages()
            _uiState.value = chatHistory.map { entity ->
                ChatMessage(
                    id = entity.id,
                    message = entity.message,
                    timestamp = entity.timestamp,
                    participant = entity.participant
                )
            }
        }

        // Initialize chat history
//        _uiState.value = chat.history.map { content ->
//            ChatMessage(
//                message = content.parts.first().asTextOrNull() ?: "",
//                timestamp = "",
//                participant = if (content.role == "user") ChatParticipant.USER else ChatParticipant.AI
//            )
//        }
    }

     suspend fun sendMessage(userMessage: String, isPlantDetailQuery: Boolean = false) : String{
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


         // Return the full response if it's a plant detail query
         if (isPlantDetailQuery) {
             return aiResponse
         }


         // Start building the AI message word by word
         val words = aiResponse.split(" ")

         Log.d("API Response", words.toString().trim{it <= ' '})

         var currentMessage = ""
         val aiMessageEntry = ChatMessage(
             message = currentMessage,
             timestamp = timestamp,
             participant = ChatParticipant.AI
         )



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

         // ***********   store query and response in chat database   *******************

         val userMessageEntryDatabase = ChatMessageEntity(
                 id = UUID.randomUUID().toString(),
                 message = userMessage,
                 timestamp = timestamp,
                 participant = ChatParticipant.USER
         )

         // Save the user's message in the database
         viewModelScope.launch(Dispatchers.IO) {
             chatDao.insertMessage(userMessageEntryDatabase)
         }

         // Save the AI's response in the database after processing
         // Example for AI message:
         val aiMessageEntryDatabase = ChatMessageEntity(
             id = UUID.randomUUID().toString(),
             message = aiResponse,
             timestamp = timestamp,
             participant = ChatParticipant.AI
         )

         viewModelScope.launch(Dispatchers.IO) {
             chatDao.insertMessage(aiMessageEntryDatabase)
         }

         return aiResponse


        //_uiState.value = _uiState.value + aiMessage
    }

    fun deleteMessage(chatMessage: ChatMessage) {
        viewModelScope.launch(Dispatchers.IO) {
            // Delete the message from the database
            chatDao.deleteMessageById(chatMessage.id)

            // Remove the message from the UI state
            _uiState.value = _uiState.value.filter { it.id != chatMessage.id }
        }
    }

}
