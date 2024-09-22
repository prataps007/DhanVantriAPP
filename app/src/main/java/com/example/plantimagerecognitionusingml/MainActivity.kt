package com.example.plantimagerecognitionusingml

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.plantimagerecognitionusingml.databinding.ActivityMainBinding
import com.example.plantimagerecognitionusingml.ml.Model
import com.google.api.Distribution.BucketOptions.Linear
import com.google.firebase.auth.FirebaseAuth
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder


class MainActivity : AppCompatActivity() {

    private var binding:ActivityMainBinding?=null
    private lateinit var auth:FirebaseAuth

    lateinit var bitmap : Bitmap

    var result: TextView? = null
    var confidence:TextView? = null
    var imageView: ImageView? = null
    var btn_identify: Button? = null
    var imageSize = 224
    var info:ImageView?=null
    var imageFound : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        auth=FirebaseAuth.getInstance()

//        if(auth.currentUser==null){
//            startActivity(Intent(this,UserAuthorisation::class.java))
//            finish()
//        }


        result = findViewById(R.id.result)
        confidence = findViewById(R.id.confidence)
        imageView = findViewById(R.id.imageView)
        btn_identify = findViewById(R.id.identify)
        info = findViewById(R.id.info)
        val classify_ll :LinearLayout = findViewById(R.id.classify_ll)
        val confidences_ll : LinearLayout = findViewById(R.id.confidences_ll)



        // upload image
//        val btn_upload: Button = findViewById(R.id.upload)
//        btn_upload.setOnClickListener(View.OnClickListener{
//            val intent:Intent = Intent(Intent.ACTION_GET_CONTENT)
//            intent.type = "image/*"
//
//            startActivityForResult(intent,100)
//        })


        // Check if an image was passed from the HomePage activity
        val capturedImage = intent.getParcelableExtra<Bitmap>("captured_image")
        if (capturedImage != null) {
            bitmap = Bitmap.createScaledBitmap(capturedImage, imageSize, imageSize, false)
            imageView?.setImageBitmap(bitmap)
        }
        else {
            Toast.makeText(this, "No image received!", Toast.LENGTH_SHORT).show()
        }


        btn_identify?.setOnClickListener {
            if (this::bitmap.isInitialized) {
                classifyImage(bitmap)
                classify_ll?.visibility = View.VISIBLE
                confidences_ll?.visibility = View.VISIBLE
                btn_identify?.visibility = View.GONE // Hide "Identify" button after identification
            } else {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            }
        }

        info?.setOnClickListener{
            val iNext = Intent(this@MainActivity,PlantDetailActivity::class.java)
            iNext.putExtra("plantName", result?.text)
            iNext.putExtra("plantImageResId", R.drawable.tulsipic) // replace with actual image
              iNext.putExtra("plantInfo", "Plant info")
            startActivity(iNext)
        }



        result?.setText("")
        confidence?.setText("")



        val back_btn: ImageView = findViewById(R.id.back_btn)
        back_btn.setOnClickListener {
            finish()
        }


    }

    fun classifyImage(image: Bitmap) {
        val model = Model.newInstance(applicationContext)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)


        val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(imageSize * imageSize)
        image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
        var pixel = 0
        for (i in 0 until imageSize) {
            for (j in 0 until imageSize) {
                val `val` = intValues[pixel++]
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
            }
        }

        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray

        // Get the top 5 results
        val sortedIndices = confidences.indices.sortedByDescending { confidences[it] }.take(5)

        val classes = arrayOf("Neem", "Aloevera", "Tulsi", "Peppermint", "Dalchinni", "Amla", "Basil", "Ashwagandha")

        result!!.text = classes[sortedIndices[0]]

        var s = ""
        for (i in sortedIndices) {
            s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100)
        }

        confidence!!.text = s

        // Releases model resources if no longer used.
        model.close()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ((requestCode == 1 || requestCode==100) && resultCode == RESULT_OK) {
            var image:Bitmap?
            if(requestCode==1) {  // scan
                 image = data!!.extras!!["data"] as Bitmap?
            }
            else{  // upload
                val uri: Uri? = data?.data
                 image = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            }


            val dimension = Math.min(image!!.width, image.height)
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
            imageView!!.setImageBitmap(image)
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)

            bitmap = image
            imageFound=true
        }


        super.onActivityResult(requestCode, resultCode, data)

    }

}