package com.example.plantimagerecognitionusingml.activities.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.plantimagerecognitionusingml.R
import com.example.plantimagerecognitionusingml.UserAuthorisation

class IntroScreen2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_screen2)

        val skip1: Button = findViewById(R.id.skip1)
        skip1.setOnClickListener{
            startActivity(Intent(this, UserAuthorisation::class.java))
        }


        val btnNext2: Button = findViewById(R.id.btnNext2)

        btnNext2.setOnClickListener{
            val iNext = Intent(this@IntroScreen2, IntroScreen3::class.java)
            startActivity(iNext)
            // or
            //startActivity(Intent(this@MainActivity,SecondActivity::class.java))
        }

//        val typeFace: Typeface = Typeface.createFromAsset(assets,"grus.bold.ttf")
//        val tv: TextView=findViewById(R.id.tv)
//        tv.typeface=typeFace
    }
}