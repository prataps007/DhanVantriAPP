package com.example.plantimagerecognitionusingml

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class ExploreActivity : AppCompatActivity(), PlantAdapter.OnItemClickListener {

    private lateinit var plantList: List<Plant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_explore)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backBtn: ImageView = findViewById(R.id.back_btn)
        backBtn.setOnClickListener {
            finish()
        }

        // Sample plant data
        plantList = listOf(
            Plant("Aloe Vera", null,R.drawable.aloeverapic, "Aloe Vera info..."),
            Plant("Basil", null,R.drawable.tulsipic, "Basil info..."),
            // Add more plants
        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = PlantAdapter(plantList, this)
    }

    override fun onItemClick(plant: Plant) {
        val intent = Intent(this, PlantDetailActivity::class.java)
        intent.putExtra("plantName", plant.commonName)
        intent.putExtra("plantImageResId", plant.imageResId)
        intent.putExtra("plantInfo", plant.info)
        startActivity(intent)
    }

    override fun Plant(name: String, info: String, imageUrl: String): Plant {
        TODO("Not yet implemented")
    }
}
