package com.example.plantimagerecognitionusingml

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private var chatMessages: List<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatMessage = chatMessages[position]
        holder.messageTextView.text = chatMessage.message
        holder.timestampTextView.text = chatMessage.timestamp

        // Adjust layout parameters based on message type
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            topMargin = 10 // Margin between messages
            bottomMargin = 10 // Margin between messages
        }

        // Set background color based on message type
        if (chatMessage.participant == ChatParticipant.USER) {
            holder.container.layoutParams = layoutParams.apply {
                marginStart = 80 // Space from the left side
                marginEnd = 40 // Add space on the right side
            }

            holder.container.gravity = Gravity.END // Align to the right

            holder.messageTextView.setTextColor(Color.BLACK)
            holder.container.setBackgroundResource(R.drawable.background_user_message)
//            holder.timestampTextView.setBackgroundColor(Color.parseColor("white"))
        } else {
            holder.container.layoutParams = layoutParams.apply {
                marginStart = 40 // Add space on the left side
                marginEnd = 70 // Space from the right side
            }

            holder.container.gravity = Gravity.START // Align to the left

            holder.messageTextView.setTextColor(Color.BLACK) // Black Color
            holder.container.setBackgroundResource(R.drawable.background_chatbot_message)
//            holder.container.setBackgroundColor(Color.parseColor("#C8E6C9")) // Light green color
        }

//        holder.messageTextView.setTextColor(Color.WHITE) // White color


    }

    override fun getItemCount(): Int = chatMessages.size

    fun updateMessages(newMessages: List<ChatMessage>) {
        this.chatMessages = newMessages
        notifyDataSetChanged()
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextView: TextView = itemView.findViewById(R.id.tv_message)
        val timestampTextView: TextView = itemView.findViewById(R.id.chat_timestamp)
        val container: LinearLayout = itemView.findViewById(R.id.container) // Make sure to add a container
    }


}
