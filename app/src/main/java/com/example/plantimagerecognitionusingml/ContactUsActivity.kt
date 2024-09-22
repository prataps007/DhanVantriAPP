package com.example.plantimagerecognitionusingml

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class ContactUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

       setUpActionBar()
    }

    private fun setUpActionBar(){
        val toolbar_contact_us : Toolbar = findViewById(R.id.toolbar_contact_us_activity)
        setSupportActionBar(toolbar_contact_us)

        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_arrow)

            actionBar.title = resources.getString(R.string.contact_us_title)

            // val toolbar_about_us : Toolbar = findViewById(R.id.toolbar_about_us_activity)
            toolbar_contact_us.setNavigationOnClickListener{onBackPressed()}
        }
    }


    fun openInstagram(view: View?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/yourprofile"))
        startActivity(intent)
    }

    fun openFacebook(view: View?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/yourprofile"))
        startActivity(intent)
    }

    fun openTwitter(view: View?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com/yourprofile"))
        startActivity(intent)
    }

    fun openEmail(view: View?) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:youremail@example.com"))
        startActivity(intent)
    }

    fun openWebsite(view: View?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.yourwebsite.com"))
        startActivity(intent)
    }
}