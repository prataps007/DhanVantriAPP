package com.example.plantimagerecognitionusingml

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class AllowAccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allow_access)

        val allowAccessButton: Button = findViewById(R.id.btn_allow_access)
        allowAccessButton.setOnClickListener {
            // Handle the permission request process here
            requestPermissions()
        }
        setUpActionBar()
    }

    private fun setUpActionBar(){
        val toolbar_allow_access : Toolbar = findViewById(R.id.toolbar_allow_access)
        setSupportActionBar(toolbar_allow_access)

        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_arrow)

            actionBar.title = resources.getString(R.string.allow_access)

            // val toolbar_about_us : Toolbar = findViewById(R.id.toolbar_about_us_activity)
            toolbar_allow_access.setNavigationOnClickListener{onBackPressed()}
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all { it.value == true }
            if (granted) {
                Toast.makeText(this, "All permissions granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Some permissions denied", Toast.LENGTH_SHORT).show()
            }
        }

    private fun requestPermissions() {
        when {
            ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this,READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                     -> {
                // Permissions are already granted
                Toast.makeText(this, "Permissions already granted", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Request the necessary permissions
                requestPermissionLauncher.launch(
                    arrayOf(
                        ACCESS_FINE_LOCATION,
                        READ_EXTERNAL_STORAGE
                    )
                )
            }
        }
    }
}