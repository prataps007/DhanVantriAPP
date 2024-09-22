package com.example.plantimagerecognitionusingml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        setUpActionBar()
    }

    private fun setUpActionBar(){
        val toolbar_about_us : Toolbar = findViewById(R.id.toolbar_about_us_activity)
        setSupportActionBar(toolbar_about_us)

        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_arrow)

            actionBar.title = resources.getString(R.string.about_us_title)

           // val toolbar_about_us : Toolbar = findViewById(R.id.toolbar_about_us_activity)
            toolbar_about_us.setNavigationOnClickListener{onBackPressed()}
        }
    }
}