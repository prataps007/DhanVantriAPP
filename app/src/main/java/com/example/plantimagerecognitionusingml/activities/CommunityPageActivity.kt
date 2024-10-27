package com.example.plantimagerecognitionusingml.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.plantimagerecognitionusingml.model.CommunityPost
import com.example.plantimagerecognitionusingml.adapters.CommunityPostAdapter
import com.example.plantimagerecognitionusingml.R
import com.example.plantimagerecognitionusingml.data.dao.CommunityPostDao
import com.example.plantimagerecognitionusingml.data.database.AppDatabase
import com.example.plantimagerecognitionusingml.data.database.FireStoreClass
import com.example.plantimagerecognitionusingml.data.entity.CommunityPostEntity
import com.example.plantimagerecognitionusingml.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CommunityPageActivity : AppCompatActivity() {

    private lateinit var communityPosts: MutableList<CommunityPost>
    private lateinit var adapter: CommunityPostAdapter
    private lateinit var userName : String
    private lateinit var communityPostDao: CommunityPostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_community_page)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val back_btn : ImageView = findViewById(R.id.back_btn)
        back_btn.setOnClickListener{
            finish()
        }

        communityPostDao = AppDatabase.getDatabase(this).communityPostDao()
        loadCommunityPosts()

        // Sample data
        communityPosts = mutableListOf(
            CommunityPost("User1", "2 hours ago", "This is the first community post."),
            CommunityPost("User2", "3 hours ago", "This is another community post.")
            // Add more posts
        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView_community_page)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = CommunityPostAdapter(communityPosts)
        recyclerView.adapter = adapter

        val fabAddPost: FloatingActionButton = findViewById(R.id.fab_add_post)
        fabAddPost.setOnClickListener {
            FireStoreClass().loadUserData(this)
            showAddPostDialog()
        }

    }

    private fun loadCommunityPosts() {
        // Use Coroutine to fetch data from the database
        CoroutineScope(Dispatchers.IO).launch {
            val posts = communityPostDao.getAllPosts()
            communityPosts.clear()
            communityPosts.addAll(posts.map { CommunityPost(it.userName, it.timestamp, it.content) })
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
            }
        }
    }



    private fun showAddPostDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_add_post, null)
        val postContentEditText: EditText = dialogView.findViewById(R.id.et_post_content)
        val submitButton: Button = dialogView.findViewById(R.id.btn_submit_post)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        submitButton.setOnClickListener {
            //val userName = "User1"

            val postContent = postContentEditText.text.toString()
            if (postContent.isNotEmpty()) {

                addNewPost(userName, postContent)
                dialog.dismiss()
            } else {
                if (postContent.isEmpty()) postContentEditText.error = "Please enter post content"
            }
        }

        dialog.show()
    }

    private fun addNewPost(userName: String, content: String) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val currentTime = dateFormat.format(Date())

        val newPost = CommunityPostEntity(userName = userName, timestamp = currentTime, content = content)

        CoroutineScope(Dispatchers.IO).launch {
            communityPostDao.insert(newPost)
            // Update the UI
            withContext(Dispatchers.Main) {
                communityPosts.add(0, CommunityPost(userName, currentTime, content)) // Add new post at the beginning
                adapter.notifyItemInserted(0)
            }
        }

//        val newPost = CommunityPost(userName, currentTime, content)
//        communityPosts.add(0, newPost) // Add new post at the beginning
//        adapter.notifyItemInserted(0)
    }

    fun getUserDetails(user : User){
        userName = user.name
    }
}