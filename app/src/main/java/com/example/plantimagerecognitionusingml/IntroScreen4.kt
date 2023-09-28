package com.example.plantimagerecognitionusingml

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class IntroScreen4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_screen4)


        val btnSignIn: Button = findViewById(R.id.btnSignIn)
        btnSignIn.setOnClickListener{
            val iNext = Intent(this@IntroScreen4,UserAuthorisation::class.java)
            startActivity(iNext)
            // or
            //startActivity(Intent(this@MainActivity,SecondActivity::class.java))
        }

        val typeFace: Typeface = Typeface.createFromAsset(assets,"grus.bold.ttf")
        val tv: TextView =findViewById(R.id.tv)
        tv.typeface=typeFace
    }
}