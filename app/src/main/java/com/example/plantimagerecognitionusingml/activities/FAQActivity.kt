package com.example.plantimagerecognitionusingml.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantimagerecognitionusingml.R
import com.example.plantimagerecognitionusingml.adapters.FAQAdapter
import com.example.plantimagerecognitionusingml.model.FAQItem

class FAQActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_faqactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backBtn: ImageView = findViewById(R.id.back_btn)
        backBtn.setOnClickListener {
            finish()
        }


        val faqList = listOf(
            FAQItem("How does Dhanvantri recognize plants?", "Dhanvantri uses a machine learning model to identify plants through images."),
            FAQItem("What is the accuracy of plant identification?", "The accuracy depends on image quality but generally ranges around 90%."),
            FAQItem("How can I get more information about a plant?", "After identifying the plant, click on 'More Info' to learn about its medicinal properties."),
            FAQItem("How can I contribute to the community?", "You can share your own experiences and insights on plants by visiting the community page.")
        )

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView_faq)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FAQAdapter(faqList)
    }
}