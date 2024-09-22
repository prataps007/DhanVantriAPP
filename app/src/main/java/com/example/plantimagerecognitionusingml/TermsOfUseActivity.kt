package com.example.plantimagerecognitionusingml

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class TermsOfUseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_of_use)

        setUpActionBar()
    }

    private fun setUpActionBar(){
        val toolbar_terms_of_use : Toolbar = findViewById(R.id.toolbar_terms_of_use)
        setSupportActionBar(toolbar_terms_of_use)

        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_arrow)

            actionBar.title = resources.getString(R.string.terms_of_use)

            // val toolbar_about_us : Toolbar = findViewById(R.id.toolbar_about_us_activity)
            toolbar_terms_of_use.setNavigationOnClickListener{onBackPressed()}
        }
    }

    fun onAcceptClick(view: View?) {
        // Handle the accept button click
        Toast.makeText(this, "Terms of Use Accepted", Toast.LENGTH_SHORT).show()
        // Close the activity or perform any other necessary actions
        finish()
    }
}