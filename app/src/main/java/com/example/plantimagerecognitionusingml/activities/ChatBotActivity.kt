package com.example.plantimagerecognitionusingml.activities

import android.content.ClipData
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

import android.content.ClipboardManager
import android.content.Context
import android.widget.PopupMenu
import com.example.plantimagerecognitionusingml.model.ChatMessage
import com.example.plantimagerecognitionusingml.R
import com.example.plantimagerecognitionusingml.adapters.ChatAdapter
import com.example.plantimagerecognitionusingml.viewModel.ChatViewModel


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

        //chatAdapter = ChatAdapter(chatViewModel.uiState.value)

        // for selecting the message
        chatAdapter = ChatAdapter(chatViewModel.uiState.value) { selectedMessage ->
            showMessageOptions(selectedMessage) // Show options on message selection
        }
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

    private fun showMessageOptions(chatMessage: ChatMessage) {
        val popupMenu = PopupMenu(this, recyclerView)
        popupMenu.menuInflater.inflate(R.menu.chat_message_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_copy -> {
                    // Copy message to clipboard
                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Copied Message", chatMessage.message)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this, "Message copied", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_delete -> {
                    // Delete the message
                    chatViewModel.deleteMessage(chatMessage) // Add delete function in ViewModel
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}