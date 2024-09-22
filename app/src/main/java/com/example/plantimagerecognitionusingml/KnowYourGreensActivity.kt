package com.example.plantimagerecognitionusingml

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class KnowYourGreensActivity : AppCompatActivity() {

    private lateinit var uploadButton: Button
    private lateinit var bitmap: Bitmap
    private val PICK_IMAGE_REQUEST = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_know_your_greens)

        uploadButton = findViewById(R.id.uploadButton)

        // Handle upload button click
        uploadButton.setOnClickListener {
            openGallery()
        }
    }

    // Open the gallery to pick an image
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Handle the result from the gallery or camera
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            val uri: Uri? = data?.data
            if (uri != null) {
                try {
                    val image = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                    bitmap = Bitmap.createScaledBitmap(image, 224, 224, false)

                    // After image is selected, send it to MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("captured_image", bitmap)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
