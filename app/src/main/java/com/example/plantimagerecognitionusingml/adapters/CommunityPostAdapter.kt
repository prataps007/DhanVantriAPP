package com.example.plantimagerecognitionusingml.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantimagerecognitionusingml.model.CommunityPost
import com.example.plantimagerecognitionusingml.R

class CommunityPostAdapter(private val communityPosts: List<CommunityPost>) :
    RecyclerView.Adapter<CommunityPostAdapter.CommunityPostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityPostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_community_post, parent, false)
        return CommunityPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommunityPostViewHolder, position: Int) {
        val post = communityPosts[position]
        holder.userName.text = post.userName
        holder.timestamp.text = post.timestamp
        holder.postContent.text = post.content
    }

    override fun getItemCount(): Int {
        return communityPosts.size
    }

    class CommunityPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.tv_user_name)
        val timestamp: TextView = itemView.findViewById(R.id.tv_timestamp)
        val postContent: TextView = itemView.findViewById(R.id.tv_post_content)
    }
}
