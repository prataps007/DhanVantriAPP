package com.example.plantimagerecognitionusingml

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.plantimagerecognitionusingml.databinding.ActivityUserAuthorisationBinding
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class UserAuthorisation : AppCompatActivity() {

    private lateinit var binding:ActivityUserAuthorisationBinding
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_authorisation)

        binding= ActivityUserAuthorisationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()

        // if already logged in then no need to to open sign in page --> directly go to Home Page

        if(auth.currentUser!=null){   //===
            startActivity(Intent(this,HomePage::class.java))  // changed from mainActivity to homePage activity
            finish()
        }

        binding.btnSignIn.setOnClickListener{
            if(binding.mobileNumber.text!!.isEmpty()){
                Toast.makeText(this,"Please enter your Number",Toast.LENGTH_SHORT).show()
            }
            else{
                var intent= Intent(this,OTP_activity::class.java)
                intent.putExtra("number",binding.mobileNumber.text!!.toString())
                startActivity(intent)
            }
        }


    }
}