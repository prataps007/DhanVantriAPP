package com.example.plantimagerecognitionusingml

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class IntroScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_screen)


        val iHome = Intent(this@IntroScreen, IntroScreen2::class.java
        ) // move from Intro screen to IntroScreen2


        Handler().postDelayed({
            startActivity(iHome)
            finish()
        }, 3000)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_intro_screen)
    }
}