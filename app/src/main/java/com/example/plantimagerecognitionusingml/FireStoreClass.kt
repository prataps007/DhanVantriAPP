package com.example.plantimagerecognitionusingml

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import de.hdodenhof.circleimageview.CircleImageView

class FireStoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()

    fun loadUserData(activity: Activity){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId()).get()
            .addOnSuccessListener {document ->
                val loggedInUser = document.toObject(User::class.java)

                when(activity){
                    is HomePage ->{
                        if (loggedInUser != null) {
                            activity.setUserDataInUI(loggedInUser)
                        }
                    }
                    is CommunityPageActivity ->{
                        if(loggedInUser!=null){
                            activity.getUserDetails(loggedInUser)
                        }
                    }
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

//    fun registerUser(activity: SignUpActivity, userINfo: User){
//        mFireStore.collection(Constants.USERS)
//            .document(getCurrentUserId()).set(userINfo, SetOptions.merge())
//            .addOnSuccessListener {
//                activity.userRegisteredSuccess()
//            }.addOnFailureListener{
//                    e->
//                Log.e(activity.javaClass.simpleName,"Error writing document",e)
//            }
//
//    }

//    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String,Any>){
//        mFireStore.collection((Constants.USERS))
//            .document(getCurrentUserId())
//            .update(userHashMap)
//            .addOnSuccessListener {
//                Log.i(activity.javaClass.simpleName,"Profile Data updated successfully")
//                Toast.makeText(activity,"Profile updated successfully!",Toast.LENGTH_LONG).show()
//
//                when(activity){
//                    is UpdateProfileActivity ->{
//                        activity.profileUpdateSuccess()
//                    }
//                }
//
//            }.addOnFailureListener { e ->
//
//                when(activity){
//                    is UpdateProfileActivity ->{
//                        activity.hideProgressDialog()
//                    }
//                }
//
//                Log.e(
//                    activity.javaClass.simpleName,
//                    "Error while creating a board.",
//                    e
//                )
//                Toast.makeText(activity,"Error when updating the profile",Toast.LENGTH_LONG).show()
//            }
//    }


//    fun loadUserData(activity: Activity, readBoardList:Boolean = false){
//        mFireStore.collection(Constants.USERS)
//            .document(getCurrentUserId()).get()
//            .addOnSuccessListener {document ->
//                val loggedInUser = document.toObject(User::class.java)
//
//                when(activity){
//                    is SignInActivity ->{
//                        if(loggedInUser != null)
//                            activity.signInSuccess(loggedInUser)
//                    }
//                    is MainActivity ->{
//                        if(loggedInUser != null)
//                            activity.updateNavigationUserDetails(loggedInUser,readBoardList)
//                    }
//                    is MyProfileActivity ->{
//                        if (loggedInUser != null) {
//                            setUserDataInUI(loggedInUser)
//                        }
//                    }
//                }
//
//                if(loggedInUser != null)
//                    activity.signInSuccess(loggedInUser)
//            }.addOnFailureListener{
//                    e->
//                when(activity){
////                    is SignInActivity ->{
////                        activity.hideProgressDialog()
////                    }
////                    is MainActivity ->{
////                        activity.hideProgressDialog()
////                    }
//                }
//                Log.e("SignInUser","Error writing document",e)
//            }
//    }

//    fun getCurrentUserId(): String{
//
//        val currentUser = FirebaseAuth.getInstance().currentUser
//        var currentUserID = ""
//        if(currentUser!=null){
//            currentUserID = currentUser.uid
//        }
//
//        return currentUserID
//
//    }

//    fun setUserDataInUI(user: User){
//
//        mUserDetails=user
//
//        val iv_user_image : CircleImageView = findViewById(R.id.iv_profile_user_image)
//        Glide
//            .with(this@MyProfileActivity)
//            .load(user.image)
//            .centerCrop()
//            .placeholder(R.drawable.ic_user_place_holder)
//            .into(iv_user_image)
//
//        val et_name : AppCompatEditText = findViewById(R.id.et_name)
//        val et_email : AppCompatEditText = findViewById(R.id.et_email)
//
//        et_name.setText(user.name)
//        et_email.setText(user.email)
//        if(user.mobile!=0L){
//            val et_mobile : AppCompatEditText = findViewById(R.id.et_mobile)
//            et_mobile.setText(user.mobile.toString())
//        }
//    }



}

