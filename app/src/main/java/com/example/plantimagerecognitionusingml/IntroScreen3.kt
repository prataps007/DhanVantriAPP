package com.example.plantimagerecognitionusingml

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class IntroScreen3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_screen3)


        val skip2: Button = findViewById(R.id.skip2)
        skip2.setOnClickListener{
            startActivity(Intent(this,UserAuthorisation::class.java))
        }


        val btnNext:Button = findViewById(R.id.btnNext3)
        btnNext.setOnClickListener{
            val iNext = Intent(this@IntroScreen3,IntroScreen4::class.java)
            startActivity(iNext)
            // or
            //startActivity(Intent(this@MainActivity,SecondActivity::class.java))
        }

//        val typeFace: Typeface = Typeface.createFromAsset(assets,"grus.bold.ttf")
//        val tv: TextView =findViewById(R.id.tv)
//        tv.typeface=typeFace
    }
}