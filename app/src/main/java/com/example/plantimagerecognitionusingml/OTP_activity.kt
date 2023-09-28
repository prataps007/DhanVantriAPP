package com.example.plantimagerecognitionusingml

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.provider.ContactsContract.ProfileSyncState
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.plantimagerecognitionusingml.databinding.ActivityOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OTP_activity : AppCompatActivity() {

    private lateinit var binding:ActivityOtpBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var verificationId:String
    private lateinit var dialog:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        binding= ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()

        val builder = AlertDialog.Builder(this)

        builder.setMessage("Please wait...")
        builder.setTitle("Loading")
        builder.setCancelable(false)

        dialog=builder.create()
        dialog.show()

        val phoneNumber="+91"+intent.getStringExtra("number")

        val options=PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object:PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    TODO("Not yet implemented")
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    dialog.dismiss()
                    Toast.makeText(this@OTP_activity,"Please try again!!",Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)

                    dialog.dismiss()
                    verificationId=p0
                }

            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        binding.btn.setOnClickListener{
            if(binding.otp.text!!.isEmpty()){
                   Toast.makeText(this,"Please enter OTP",Toast.LENGTH_SHORT).show()
            }
            else{
                dialog.show()

                val credential = PhoneAuthProvider.getCredential(verificationId,binding.otp.text!!.toString())

                auth.signInWithCredential(credential)
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            startActivity(Intent(this,HomePage::class.java)) // changed from Main activity to homePage activity
                           // startActivity(Intent(this,ProfileActivity::class.java))
                             finish()
                        }
                        else{
                            dialog.dismiss()
                            Toast.makeText(this,"Error ${it.exception}",Toast.LENGTH_SHORT).show()
                        }
                    }
               }
        }
    }
}