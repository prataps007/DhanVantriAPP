package com.example.plantimagerecognitionusingml

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ShareAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_app)

        val shareButton: Button = findViewById(R.id.btn_share_app)
        shareButton.setOnClickListener {
            shareApp()
        }
    }

    private fun shareApp() {
        val appPackageName = packageName
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        try {
            val appInfo = packageManager.getApplicationInfo(appPackageName, PackageManager.GET_META_DATA)
            val appName = packageManager.getApplicationLabel(appInfo).toString()
            val message = "Check out this awesome app: $appName\n\nhttps://play.google.com/store/apps/details?id=$appPackageName"
            intent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(Intent.createChooser(intent, "Share $appName via"))
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }
}