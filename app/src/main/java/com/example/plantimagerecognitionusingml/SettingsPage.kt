package com.example.plantimagerecognitionusingml

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

class SettingsPage : AppCompatActivity() {

    private val mFireStore = FirebaseFirestore.getInstance()
    private var mSelectedImageFileUri: Uri? = null
    private lateinit var mUserDetails: User
    private var mProfileImageURL: String = ""

    private lateinit var darkModeSwitch: Switch
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_page)

        //layoutInflater.inflate(R.layout.activity_settings_page, findViewById(R.id.content_frame))

        // to show user data
        loadUserData()

        val log_out: LinearLayout = findViewById(R.id.logout)
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        log_out.setOnClickListener {
            if (currentUser == null) {
                // The user is not logged in, you can stay on the current activity
            } else {
                // The user is logged in, perform logout and navigate to the login screen
                auth.signOut()
                navigateToLoginScreen()
            }
        }

        val back_btn: ImageView = findViewById(R.id.back_btn)
        back_btn.setOnClickListener {
            finish()
        }

        // update profile
        val btn_edit_profile : Button = findViewById(R.id.edit_profile)
        btn_edit_profile.setOnClickListener {
            startActivity(Intent(this@SettingsPage,UpdateProfileActivity::class.java))
        }

        // Dark mode switch
        val switchDarkMode: Switch = findViewById(R.id.switch_dark_mode)
        val preferences: SharedPreferences = getSharedPreferences("appPreferences", MODE_PRIVATE)
        val isDarkMode = preferences.getBoolean("isDarkMode", false)

        switchDarkMode.isChecked = isDarkMode

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            preferences.edit().putBoolean("isDarkMode", isChecked).apply()
        }

        val contact_us : LinearLayout = findViewById(R.id.contact_us)
        contact_us.setOnClickListener{
            startActivity(Intent(this,ContactUsActivity::class.java))
        }

        val rate_us : LinearLayout = findViewById(R.id.rate_us)
        rate_us.setOnClickListener{
            startActivity(Intent(this,RateUsActivity::class.java))
        }

        val privacy_policy : LinearLayout = findViewById(R.id.privacy_policy)
        privacy_policy.setOnClickListener{
            startActivity(Intent(this,PrivacyPolicyActivity::class.java))
        }

        val terms_of_use : LinearLayout = findViewById(R.id.terms_of_use)
        terms_of_use.setOnClickListener{
            startActivity(Intent(this,TermsOfUseActivity::class.java))
        }

        val about_us : LinearLayout = findViewById(R.id.about_us)
        about_us.setOnClickListener{
            startActivity(Intent(this,AboutUsActivity::class.java))
        }

        val allow_access : LinearLayout = findViewById(R.id.allow_access_option)
        allow_access.setOnClickListener{
            startActivity(Intent(this,AllowAccessActivity::class.java))
        }

        val share_app : LinearLayout = findViewById(R.id.share_app)
        share_app.setOnClickListener{
            startActivity(Intent(this,ShareAppActivity::class.java))
        }
    }

    private fun navigateToLoginScreen() {
        // Navigate to your login activity or fragment
        val intent = Intent(this, UserAuthorisation::class.java)
        startActivity(intent)
        finish() // Optional: finish the current activity to prevent going back to it
    }

    fun loadUserData(){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId()).get()
            .addOnSuccessListener {document ->
                val loggedInUser = document.toObject(User::class.java)

                if (loggedInUser != null) {
                    setUserDataInUI(loggedInUser)
                }

            }.addOnFailureListener{
                    e->
                Log.e("SignInUser","Error writing document",e)
            }
    }

    fun getCurrentUserId(): String{

        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if(currentUser!=null){
            currentUserID = currentUser.uid
        }

        return currentUserID
    }

    fun setUserDataInUI(user: User){

        mUserDetails=user

        val iv_user_image : CircleImageView = findViewById(R.id.profile_image)
        Glide
            .with(this@SettingsPage)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(iv_user_image)

        val tv_user_name : TextView = findViewById(R.id.user_name)
        val tv_user_email : TextView = findViewById(R.id.user_email)

        tv_user_name.setText(user.name)
        tv_user_email.setText(user.email)

    }
}
