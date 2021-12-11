package com.example.myrecipes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class ImageUploader : AppCompatActivity() {
    private var chooseImageButton: Button? = null
    private var uploadImageButton: Button? = null
    private var imageNameEditText: EditText? = null
    private var uploadImageView: ImageView? = null
    private var uploadProgressBar: ProgressBar? = null
    private var uploadsTextView: TextView? = null
    private var resultUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_uploader)

        chooseButtonHandle()
        uploadButtonHandle()
        imageNameHandle()
        uploadImageView = findViewById(R.id.uploadImageView)
        uploadProgressBar = findViewById(R.id.uploadProgressBar)
        uploadsTextView = findViewById(R.id.uploadsTextView)
    }


    private fun imageNameHandle() {
        imageNameEditText = findViewById(R.id.imageNameEditText)
    }

    private fun uploadButtonHandle() {
        uploadImageButton = findViewById(R.id.uploadImageButton)
        uploadImageButton?.setOnClickListener {
            Log.i("test:", "upload button clicked")
        }
    }

    private fun chooseButtonHandle() {
        chooseImageButton = findViewById(R.id.chooseImageButton)
        chooseImageButton?.setOnClickListener {
            Log.i("test:", "choose button clicked")
            openActivity()
        }
    }

    private var launchSomeActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result != null && result.resultCode == Activity.RESULT_OK) {
            resultUri = result.data?.data
            if (resultUri != null) {
                Picasso.with(this).load(resultUri).into(uploadImageView)
            }
        }
    }

    private fun openActivity() {
        val intent = Intent(this, ImagePicker::class.java)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_RESTRICTION_ENTRIES
        launchSomeActivity.launch(intent)
    }
}