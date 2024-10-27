package com.example.plantimagerecognitionusingml.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.plantimagerecognitionusingml.data.entity.CommunityPostEntity

@Dao
interface CommunityPostDao {
    @Insert
    suspend fun insert(post: CommunityPostEntity)

    @Query("SELECT * FROM community_posts ORDER BY id DESC")
    suspend fun getAllPosts(): List<CommunityPostEntity>
}
