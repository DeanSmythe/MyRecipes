package com.example.myrecipes

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myrecipes.data.ImageDatabaseHandler
import com.example.myrecipes.data.LocalRepository

class ImageUploader : AppCompatActivity() {
    private val localRepository: LocalRepository = LocalRepository()
    private var chooseImageButton: Button? = null
    private var uploadImageButton: Button? = null
    private var imageNameEditText: EditText? = null
    var uploadImageView: ImageView? = null
    private var uploadProgressBar: ProgressBar? = null
    private var uploadsTextView: TextView? = null
    private val imagePickerNavigationHandler = ImagePickerNavigationHandler(this, localRepository)

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
            val imageDatabaseHandler = ImageDatabaseHandler(localRepository,imagePickerNavigationHandler)
            imageDatabaseHandler.downloadImages()
            Log.d("returned:", localRepository.images.toString())
        }
    }

}