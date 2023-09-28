package com.example.plantimagerecognitionusingml

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.google.firebase.auth.FirebaseAuth

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)


        val scanner=findViewById<ImageView>(R.id.scannerView)

        scanner.setOnClickListener{
            val iNext = Intent(this@HomePage,MainActivity::class.java)
            startActivity(iNext)

        }

        val logOut:ImageView = findViewById(R.id.logout)
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        logOut.setOnClickListener{

            if (currentUser == null) {
                // The user is not logged in, you can stay on the current activity
            } else {
                // The user is logged in, perform logout and navigate to the login screen
                auth.signOut()
                navigateToLoginScreen()
            }
        }

    }
    private fun navigateToLoginScreen() {
        // Navigate to your login activity or fragment
        val intent = Intent(this, UserAuthorisation::class.java)
        startActivity(intent)
        finish() // Optional: finish the current activity to prevent going back to it
    }
}