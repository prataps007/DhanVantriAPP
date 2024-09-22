package com.example.plantimagerecognitionusingml

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class HomePage : AppCompatActivity() {

    private lateinit var mUserDetails: User
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
       // layoutInflater.inflate(R.layout.activity_home_page, findViewById(R.id.content_frame))

        FireStoreClass().loadUserData(this)

        val scanner=findViewById<ImageView>(R.id.scanner_item)

        scanner.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 1)
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }


        val btn_settings : ImageView = findViewById(R.id.settings_item)

        btn_settings.setOnClickListener{
            startActivity(Intent(this,SettingsPage::class.java))
        }

        val btn_know_your_plant : ImageView = findViewById(R.id.myImageView4)
        btn_know_your_plant.setOnClickListener{
            startActivity(Intent(this,KnowYourGreensActivity::class.java));
        }

//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
//
//        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.scanner_item -> {
//                    val iNext = Intent(this@HomePage, MainActivity::class.java)
//                    startActivity(iNext)
//                    true
//                }
//                R.id.settings_item -> {
//                    val iSettings = Intent(this@HomePage, SettingsPage::class.java)
//                    startActivity(iSettings)
//                    true
//                }
//                R.id.home_item -> {
//                    val iHome = Intent(this, HomePage::class.java)
//                    startActivity(iHome)
//                    true
//                }
//                else -> false
//            }
//        }


        // Get the TextView for the greeting message
        val tvGreeting: TextView = findViewById(R.id.tv_greeting)

        // Set the greeting message based on the current time
        setGreetingMessage(tvGreeting)

        // chatbot
        val chatbot : FloatingActionButton = findViewById(R.id.fab_chat)
        chatbot.setOnClickListener{
            startActivity(Intent(this,ChatBotActivity::class.java))
        }

        // explore
        val explore : ImageButton = findViewById(R.id.explore_btn)
        explore.setOnClickListener{
            startActivity(Intent(this,ExploreActivity::class.java))
        }

        // community
        val community : ImageButton = findViewById(R.id.community_btn)
        community.setOnClickListener{
            startActivity(Intent(this,CommunityPageActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // Update the greeting message when the activity is resumed
        val tvGreeting: TextView = findViewById(R.id.tv_greeting)
        setGreetingMessage(tvGreeting)
    }

    private fun setGreetingMessage(textView: TextView) {
        val currentTime = Calendar.getInstance().time
        val timeFormat = SimpleDateFormat("HH", Locale.getDefault())
        val hour = timeFormat.format(currentTime).toInt()

        val greetingMessage = when (hour) {
            in 0..11 -> " Good morning "
            in 12..16 -> " Good afternoon "
            else -> " Good evening "
        }

        textView.text = greetingMessage
    }

    fun setUserDataInUI(user: User){

        mUserDetails=user

        val tv_user_name : TextView = findViewById(R.id.tv_user)

        val firstName = user.name.split(" ")[0]

        tv_user_name.text = firstName
//        tv_user_name.setText(user.name)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val image = data?.extras?.get("data") as Bitmap?
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("captured_image", image)
            startActivity(intent)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}