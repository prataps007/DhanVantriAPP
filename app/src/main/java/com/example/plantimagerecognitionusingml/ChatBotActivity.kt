package com.example.plantimagerecognitionusingml

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch



class ChatBotActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: Button
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatViewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot)

        val back_btn: ImageView = findViewById(R.id.back_btn)
        back_btn.setOnClickListener {
            finish()
        }

        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        recyclerView = findViewById(R.id.chat_recycler_view)
        editTextMessage = findViewById(R.id.message_input);
        buttonSend = findViewById(R.id.send_button);

        chatAdapter = ChatAdapter(chatViewModel.uiState.value)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdapter

        // Collecting the StateFlow using lifecycleScope
        lifecycleScope.launch {
            chatViewModel.uiState.collect { chatMessages ->
                chatAdapter.updateMessages(chatMessages)
                recyclerView.scrollToPosition(chatMessages.size - 1)
            }
        }

        buttonSend.setOnClickListener {
            val message = editTextMessage.text.toString()
            if (message.isNotEmpty()) {
                sendMessage(message)
                editTextMessage.text.clear()
            }
        }
    }

    private fun sendMessage(message: String) {
        lifecycleScope.launch {
            chatViewModel.sendMessage(message)
        }

    }
}