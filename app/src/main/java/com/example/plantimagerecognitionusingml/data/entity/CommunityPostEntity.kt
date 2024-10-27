package com.example.plantimagerecognitionusingml.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "community_posts")
data class CommunityPostEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userName: String,
    val timestamp: String,
    val content: String
)

