package com.example.plantimagerecognitionusingml.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantimagerecognitionusingml.R
import com.example.plantimagerecognitionusingml.adapters.PlantDetailsAdapter
import com.example.plantimagerecognitionusingml.viewModel.ChatViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlantDetailActivity : AppCompatActivity() {


    private var isBookmarked = false // Variable to track bookmark state

    private lateinit var geminiApiService: ChatViewModel
    private lateinit var plantDetailsRecyclerView: RecyclerView
    private lateinit var plantDetailsAdapter: PlantDetailsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_plant_detail)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backBtn: ImageView = findViewById(R.id.back_btn)
        backBtn.setOnClickListener {
            finish()
        }

        val favBtn : ImageView = findViewById(R.id.bookmark_btn)
        favBtn.setOnClickListener {
            isBookmarked = !isBookmarked
            if (isBookmarked) {
                favBtn.setImageResource(R.drawable.added_to_fav_btn) // Set the red bookmark icon
                Toast.makeText(this, "Plant added to favourites", Toast.LENGTH_SHORT).show()
            } else {
                favBtn.setImageResource(R.drawable.favourite_btn) // Set the default bookmark icon
                Toast.makeText(this, "Plant removed from favourites", Toast.LENGTH_SHORT).show()
            }
        }

        val plantName = intent.getStringExtra("plantName")
        val plantImageResId = intent.getIntExtra("plantImageResId", 0)
        val plantInfo = intent.getStringExtra("plantInfo")

        val plantImage: ImageView = findViewById(R.id.plant_detail_image)
        val plantNameText: TextView = findViewById(R.id.plant_detail_name)
        val plantInfoText: TextView = findViewById(R.id.plant_detail_info)

        plantImage.setImageResource(plantImageResId)
        plantNameText.text = plantName
        plantInfoText.text = plantInfo


        // Set up RecyclerView
        plantDetailsRecyclerView = findViewById(R.id.plantDetailsRecyclerView)
        plantDetailsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize Adapter
        plantDetailsAdapter = PlantDetailsAdapter(mutableListOf())
        plantDetailsRecyclerView.adapter = plantDetailsAdapter

        geminiApiService = ChatViewModel(application)

        plantName?.let {
            fetchPlantDetails(it)
        }
    }

    private fun fetchPlantDetails(plantName: String) {
        val plantDetailsQuery = "Tell me about $plantName. The response should result in common name,local name in India, scientific name, medicinal properties, application,diseases in which it can be used, common home remedies, and commonly found locations." + "" +
                "Also response should be in detailed points and relevant"

        lifecycleScope.launch {
            val plantDetailsString = geminiApiService.sendMessage(plantDetailsQuery, true)


            // Split response into words and update UI word by word
            val words = plantDetailsString.toString().split(" ")
            var currentMessage = ""

            for (word in words) {
                currentMessage += "$word "

                // Update RecyclerView with the current message
                plantDetailsAdapter.updatePlantDetails(currentMessage.trim())
                delay(100) // Adjust delay as per your preference
            }


        }
    }

}