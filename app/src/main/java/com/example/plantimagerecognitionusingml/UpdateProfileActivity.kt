package com.example.plantimagerecognitionusingml

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.plantimagerecognitionusingml.Constants.PICK_IMAGE_REQUEST_CODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView
import java.io.IOException

class UpdateProfileActivity : BaseActivity() {

    private var mSelectedImageFileUri: Uri? =null
    private lateinit var mUserDetails : User
    private var mProfileImageURL : String=""

    private val mFireStore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        setUpActionBar()

        loadUserData()

        val iv_profile_user_image : CircleImageView = findViewById(R.id.iv_profile_user_image)

        iv_profile_user_image.setOnClickListener{
            // TODO Show Image Chooser
            Constants.showImageChooser(this)
        }

        val btn_update : Button = findViewById(R.id.btn_update)
        btn_update.setOnClickListener{
            if(mSelectedImageFileUri!=null){
                uploadUserImage()
            }
            else{
                showProgressDialog(resources.getString(R.string.please_wait))
                updateUserProfileData()
            }
        }

    }

//    private fun showImageChooser(){
//        var galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//
//        startActivityForResult(galleryIntent, Constants.PICK_IMAGE_REQUEST_CODE)
//    }

    private fun getFileExtension(uri: Uri):String?{
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(contentResolver.getType(uri!!))
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST_CODE && data?.data!=null){
            mSelectedImageFileUri = data.data

            try {
                val iv_user_image: CircleImageView = findViewById(R.id.iv_profile_user_image)
                Glide
                    .with(this@UpdateProfileActivity)
                    .load(mSelectedImageFileUri)
                    .centerCrop()
                    .placeholder(R.drawable.ic_user_place_holder)
                    .into(iv_user_image)
            }
            catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    fun setUserDataInUI(user: User){

        mUserDetails=user

        val iv_user_image : CircleImageView = findViewById(R.id.iv_profile_user_image)
        Glide
            .with(this@UpdateProfileActivity)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(iv_user_image)

        val et_name : AppCompatEditText = findViewById(R.id.et_name_my_profile)
        val et_email : AppCompatEditText = findViewById(R.id.et_email_my_profile)

        et_name.setText(user.name)
        et_email.setText(user.email)
        if(user.mobile!=0L){
            val et_mobile : AppCompatEditText = findViewById(R.id.et_mobile_my_profile)
            et_mobile.setText(user.mobile.toString())
        }
    }

    private fun updateUserProfileData(){
        val userHashMap = HashMap<String,Any>()

        var anyChangesMade = false

        if(mProfileImageURL.isNotEmpty() && mProfileImageURL!=mUserDetails.image){
            // userHashMap["image"]
            userHashMap[Constants.IMAGE] = mProfileImageURL
            anyChangesMade=true;
        }

        val et_name : EditText = findViewById(R.id.et_name_my_profile)
        if(et_name.text.toString()!=mUserDetails.name){
            userHashMap[Constants.NAME] = et_name.text.toString()
            anyChangesMade=true
        }

        val et_mobile : EditText = findViewById(R.id.et_mobile_my_profile)
        if(et_mobile.text.toString()!=mUserDetails.mobile.toString()){
            userHashMap[Constants.MOBILE] = et_mobile.text.toString().toLong()
            anyChangesMade=true
        }

        if (anyChangesMade) {
            updateUserProfileData(userHashMap)
        } else {
            hideProgressDialog()
        }
    }

    private fun updateUserProfileData(userHashMap: HashMap<String,Any>){

        mFireStore.collection((Constants.USERS))
            .document(getCurrentUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                Log.i(javaClass.simpleName,"Profile Data updated successfully")
                Toast.makeText(this,"Profile updated successfully!", Toast.LENGTH_LONG).show()

                        profileUpdateSuccess()
            }.addOnFailureListener { e ->

                        hideProgressDialog()
                Toast.makeText(this,"Error when updating the profile", Toast.LENGTH_LONG).show()
            }
    }


    private fun uploadUserImage(){
        showProgressDialog(resources.getString(R.string.please_wait))

        if(mSelectedImageFileUri!=null){
            val sRef : StorageReference =
                FirebaseStorage.getInstance().reference.child(
                    "USER_IMAGE"+System.currentTimeMillis()
                            +"."+ getFileExtension(mSelectedImageFileUri!!))

            sRef.putFile(mSelectedImageFileUri!!).addOnSuccessListener {
                    taskSnapshot->
                Log.i(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )

                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        uri->
                    Log.i("Downloadable Image URL",uri.toString())
                    mProfileImageURL = uri.toString()

                    hideProgressDialog()
                    // TODO UpdateUserProfileData
                    updateUserProfileData()

                }
            }.addOnFailureListener{
                    exception->

                hideProgressDialog()
                Toast.makeText(
                    this@UpdateProfileActivity,
                    exception.message,
                    Toast.LENGTH_LONG
                ).show()


            }
        }
    }



    fun profileUpdateSuccess(){
        hideProgressDialog()
        setResult(Activity.RESULT_OK)
        finish()
    }

    @SuppressLint("CutPasteId")
    private fun setUpActionBar(){
        val toolbar_main_activity : Toolbar = findViewById(R.id.toolbar_my_profile_activity)
        setSupportActionBar(toolbar_main_activity)

        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_arrow)

            actionBar.title = resources.getString(R.string.my_profile_title)

            val toolbar_my_profile_activity : Toolbar = findViewById(R.id.toolbar_my_profile_activity)
            toolbar_my_profile_activity.setNavigationOnClickListener{onBackPressed()}
        }
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


}