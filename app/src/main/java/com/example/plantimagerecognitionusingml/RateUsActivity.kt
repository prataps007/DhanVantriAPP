package com.example.plantimagerecognitionusingml

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class RateUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_us)

        val ratingBar = findViewById<RatingBar>(R.id.idRBRate)
        val submitButton = findViewById<Button>(R.id.idBtnSubmit)

        submitButton.setOnClickListener {
            val rating = ratingBar.rating
            Toast.makeText(this, "You rated: $rating stars", Toast.LENGTH_SHORT).show()
            // You can add additional logic here to handle the rating submission, such as sending it to a server
        }
        setUpActionBar()
    }

    private fun setUpActionBar(){
        val toolbar_rate_us : Toolbar = findViewById(R.id.toolbar_rate_us)
        setSupportActionBar(toolbar_rate_us)

        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_arrow)

            actionBar.title = resources.getString(R.string.rate_us)

            // val toolbar_about_us : Toolbar = findViewById(R.id.toolbar_about_us_activity)
            toolbar_rate_us.setNavigationOnClickListener{onBackPressed()}
        }
    }
}
