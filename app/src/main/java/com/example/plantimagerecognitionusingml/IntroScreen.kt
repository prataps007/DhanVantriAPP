package com.example.plantimagerecognitionusingml

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class IntroScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_screen)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({

            val currentUserID = getCurrentUserId()

            if(currentUserID.isNotEmpty()){
                startActivity(Intent(this,HomePage::class.java))
            }else{

                startActivity(Intent(this,IntroScreen2::class.java))

            }

            // Start the main activity after the specified duration
            finish()
        }, 2500.toLong())

    }

    fun getCurrentUserId(): String{

        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if(currentUser!=null){
            currentUserID = currentUser.uid
        }

        return currentUserID

        //  return FirebaseAuth.getInstance().currentUser!!.uid
    }
}