package com.example.plantimagerecognitionusingml.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plantimagerecognitionusingml.adapters.PlantDetailsAdapter
import com.example.plantimagerecognitionusingml.R
import com.example.plantimagerecognitionusingml.viewModel.ChatViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class KnowYourGreensActivity : AppCompatActivity() {

    private lateinit var uploadButton: Button
    private lateinit var bitmap: Bitmap
    private val PICK_IMAGE_REQUEST = 100
    private val VOICE_SEARCH_REQUEST = 200

    private lateinit var backButton: ImageButton
    private lateinit var searchIcon: ImageButton
    private lateinit var searchBar: LinearLayout
    private lateinit var searchView: SearchView
    private lateinit var voiceSearchButton: ImageButton
    private lateinit var recentSearchesRecyclerView: RecyclerView
    private lateinit var searchResultsRecyclerView: RecyclerView

    private lateinit var geminiApiService: ChatViewModel
    private lateinit var plantDetailsAdapter: PlantDetailsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_know_your_greens)

        // Initialize UI elements
        uploadButton = findViewById(R.id.uploadButton)
        backButton = findViewById(R.id.backButton)
        searchIcon = findViewById(R.id.searchIcon)
        searchBar = findViewById(R.id.searchBar)
        searchView = findViewById(R.id.searchView)
        voiceSearchButton = findViewById(R.id.voiceSearchButton)
        recentSearchesRecyclerView = findViewById(R.id.recentSearchesRecyclerView)
        searchResultsRecyclerView = findViewById(R.id.searchResultsRecyclerView)


        // Set up the RecyclerView
        plantDetailsAdapter = PlantDetailsAdapter(mutableListOf())
        searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
        searchResultsRecyclerView.adapter = plantDetailsAdapter


        // Handle upload button click
        uploadButton.setOnClickListener {
            openGallery()
        }


        // Handle search icon click to expand the search bar
        searchIcon.setOnClickListener {
            searchBar.visibility = LinearLayout.VISIBLE
//            recentSearchesRecyclerView.visibility = RecyclerView.VISIBLE
//            searchResultsRecyclerView.visibility = RecyclerView.VISIBLE
            uploadButton.visibility = Button.GONE
            searchIcon.visibility = ImageButton.GONE
        }

        // Handle back button click to close the search bar
        backButton.setOnClickListener {
            searchBar.visibility = LinearLayout.GONE
            recentSearchesRecyclerView.visibility = RecyclerView.GONE
            searchResultsRecyclerView.visibility = RecyclerView.GONE
            uploadButton.visibility = Button.VISIBLE
            searchIcon.visibility = ImageButton.VISIBLE
        }



        // Handle search queries
        geminiApiService = ChatViewModel(application)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {

                    searchForPlant(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Optionally handle text change for live search
                return false
            }
        })

        // Voice search button click
        voiceSearchButton.setOnClickListener {
            startVoiceSearch()
        }

        // Setup RecyclerView for recent searches
        //recentSearchesRecyclerView.layoutManager = LinearLayoutManager(this)
       // val recentSearchesAdapter = RecentSearchesAdapter(viewModel.getRecentSearches())
        //recentSearchesRecyclerView.adapter = recentSearchesAdapter

    }

    // Start voice search
    private fun startVoiceSearch() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak plant name...")
        startActivityForResult(intent, VOICE_SEARCH_REQUEST)
    }

    // Open the gallery to pick an image
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Handle the result from the gallery or camera
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            val uri: Uri? = data?.data
            if (uri != null) {
                try {
                    val image = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                    bitmap = Bitmap.createScaledBitmap(image, 224, 224, false)

                    // After image is selected, send it to MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("captured_image", bitmap)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (requestCode == VOICE_SEARCH_REQUEST && resultCode == Activity.RESULT_OK) {
            val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            matches?.let {
                if (it.isNotEmpty()) {
                    searchView.setQuery(it[0], true)  // Set the spoken text in search bar
                }
            }
        }
    }

    // Search for plant using the Gemini API
    private fun searchForPlant(query: String) {
        lifecycleScope.launch {
            val plantDetailsString = geminiApiService.sendMessage(query, true)

            // Show the RecyclerView if it's hidden
            searchResultsRecyclerView.visibility = RecyclerView.VISIBLE

            // Split response into words and update UI word by word
            val words = plantDetailsString.toString().split(" ")
            var currentMessage = ""

            for (word in words) {
                currentMessage += "$word "

                // Update RecyclerView with the current message
                plantDetailsAdapter.updatePlantDetails(currentMessage.trim())
                delay(100) // Adjust delay
            }
        }
    }
}
