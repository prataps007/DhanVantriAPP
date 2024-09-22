package com.example.plantimagerecognitionusingml

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.plantimagerecognitionusingml.databinding.ActivityUserAuthorisationBinding
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class UserAuthorisation : BaseActivity() {

    private lateinit var binding:ActivityUserAuthorisationBinding
    private lateinit var auth:FirebaseAuth
    private var isPasswordVisible = false

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


        // sign in
        val btn_sign_in: Button = findViewById(R.id.btn_sign_in)
        btn_sign_in.setOnClickListener{
            signInRegisterUser()
        }

        // sign up
       val tv_sign_up : TextView = findViewById(R.id.tv_sign_up)
        tv_sign_up.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        val passwordEditText: EditText = findViewById(R.id.password)
        val showPasswordImageView: ImageView = findViewById(R.id.iv_show_password)

        showPasswordImageView.setOnClickListener {
            if (isPasswordVisible) {
                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                showPasswordImageView.setImageResource(R.drawable.ic_eye_closed) // Change to closed eye icon
            } else {
                passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                showPasswordImageView.setImageResource(R.drawable.ic_eye) // Change to open eye icon
            }
            isPasswordVisible = !isPasswordVisible
            passwordEditText.setSelection(passwordEditText.text.length) // Move cursor to end
        }

    }

    fun forgotPassword(view: View) {
        val et_email: TextView = findViewById(R.id.email)
        val email: String = et_email.text.toString().trim{it <= ' '}

        if(email.isNotEmpty()) {
            showResetPasswordDialog(email)
        }
        else{
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show()
        }
    }

    private fun showResetPasswordDialog(email: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Reset Password")
        builder.setMessage("Are you sure you want to reset your password?")
        builder.setPositiveButton("Yes") { dialog, which ->
            resetPassword(email)
        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to send password reset email", Toast.LENGTH_SHORT).show()
                }
            }
    }



    fun signInSuccess(){
        hideProgressDialog()
        startActivity(Intent(this,HomePage::class.java))
        finish()
    }

    private fun signInRegisterUser(){
        // val et_name: TextView = findViewById(R.id.et_name)
        val et_email: TextView = findViewById(R.id.email)
        val et_password: TextView = findViewById(R.id.password)

//        val name: String = et_name.text.toString().trim{it <= ' '}
        val email: String = et_email.text.toString().trim{it <= ' '}
        val password: String = et_password.text.toString().trim{it <= ' '}

        if(validateForm(email,password)){
            showProgressDialog(resources.getString(R.string.please_wait))

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Sign in", "signInWithEmail:success")
                        val user = auth.currentUser
//                        startActivity(Intent(this,MainActivity::class.java))

                        signInSuccess()

//                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Sign in", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
//                        updateUI(null)
                    }
                }
        }
    }

    private fun validateForm(email:String,password:String) : Boolean{
        return when{
            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Please enter a name")
                false
            }
            else->{
                return true
            }

        }
    }
}