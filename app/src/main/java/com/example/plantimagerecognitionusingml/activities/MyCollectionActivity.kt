package com.example.plantimagerecognitionusingml.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.plantimagerecognitionusingml.R
import com.example.plantimagerecognitionusingml.model.Plant

class MyCollectionActivity : AppCompatActivity() {

    private lateinit var plantList: List<Plant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_collection)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backBtn: ImageView = findViewById(R.id.back_btn)
        backBtn.setOnClickListener {
            finish()
        }



    }

}