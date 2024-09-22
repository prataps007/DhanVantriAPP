package com.example.plantimagerecognitionusingml

import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

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

        geminiApiService = ChatViewModel()

        plantName?.let {
            fetchPlantDetails(it)
        }
    }

    private fun fetchPlantDetails(plantName: String) {
        val plantDetailsQuery = "Tell me about $plantName. The response should result in common name, scientific name, medicinal properties, application, and commonly found locations." + "" +
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


//            val plantDetailsList = parsePlantDetails(plantDetailsString.toString())
//
//            // Set up the adapter with the parsed plant details
//            plantDetailsAdapter = PlantDetailsAdapter(plantDetailsList)
//            plantDetailsRecyclerView.adapter = plantDetailsAdapter


        }
    }

    private fun parsePlantDetails(details: String): List<Plant> {
        val plantDetailsList = mutableListOf<Plant>()

        // Example parsing logic
//        plantDetailsList.add(
//            Plant(
//                commonName = "Tulsi",
//                scientificName = "Ocimum tenuiflorum",
//                imageResId = R.drawable.tulsipic,
//                medicinalProperties = "Antioxidant, Anti-inflammatory",
//                application = "Used in teas, remedies, and for worship",
//                commonlyFound = "India, Southeast Asia"
//            )
//        )

        try {
            // Remove any extraneous characters or fix formatting if necessary
            val jsonResponse = details.trim { it <= ' ' } // Trim whitespace
            val jsonObject = JSONObject(jsonResponse)

            // Example JSON structure
            val commonName = jsonObject.optString("common_name", "Unknown")
            val scientificName = jsonObject.optString("scientific_name", "Unknown")
            val medicinalProperties = jsonObject.optString("medicinal_properties", "N/A")
            val application = jsonObject.optString("application", "N/A")
            val commonlyFound = jsonObject.optString("common_locations", "N/A")

            // Create a Plant object from the parsed data
            val plant = Plant(
                commonName = commonName,
                scientificName = scientificName,
                0,
                medicinalProperties = medicinalProperties,
                application = application,
                commonlyFound = commonlyFound
            )

            // Add the plant to the list
            plantDetailsList.add(plant)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return plantDetailsList
    }
}