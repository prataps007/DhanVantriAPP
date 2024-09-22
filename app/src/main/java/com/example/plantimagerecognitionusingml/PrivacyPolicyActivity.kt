package com.example.plantimagerecognitionusingml

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class PrivacyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        setUpActionBar()
    }

    private fun setUpActionBar(){
        val toolbar_privacy_policy : Toolbar = findViewById(R.id.toolbar_privacy_policy)
        setSupportActionBar(toolbar_privacy_policy)

        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_arrow)

            actionBar.title = resources.getString(R.string.privacy_policy)

            // val toolbar_about_us : Toolbar = findViewById(R.id.toolbar_about_us_activity)
            toolbar_privacy_policy.setNavigationOnClickListener{onBackPressed()}
        }
    }

    fun onAcceptClick(view: View?) {
        // Handle the accept button click
        Toast.makeText(this, "Privacy Policy Accepted", Toast.LENGTH_SHORT).show()
        // Close the activity or perform any other necessary actions
        finish()
    }
}