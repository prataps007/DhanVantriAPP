package com.example.plantimagerecognitionusingml

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class IntroScreen2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_screen2)

        val btnNext2: Button = findViewById(R.id.btnNext2)

        btnNext2.setOnClickListener{
            val iNext = Intent(this@IntroScreen2,IntroScreen3::class.java)
            startActivity(iNext)
            // or
            //startActivity(Intent(this@MainActivity,SecondActivity::class.java))
        }

        val typeFace: Typeface = Typeface.createFromAsset(assets,"grus.bold.ttf")
        val tv: TextView=findViewById(R.id.tv)
        tv.typeface=typeFace
    }
}