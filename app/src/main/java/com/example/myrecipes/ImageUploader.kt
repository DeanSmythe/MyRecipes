package com.example.myrecipes

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myrecipes.data.Image
import com.example.myrecipes.data.ImageDatabaseHandler
import com.example.myrecipes.data.LocalRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image_uploader.*

class ImageUploader : AppCompatActivity(), ActivityNavigationHandler {
    private val localRepository: LocalRepository = LocalRepository()
    private var chooseImageButton: Button? = null
    private var chooseLocalImageButton: Button? = null
    private var uploadImageButton: Button? = null
    private var downloadImageButton: Button? = null
    private var imageNameEditText: EditText? = null
    var uploadImageView: ImageView? = null
    private var uploadProgressBar: ProgressBar? = null
    private var uploadsTextView: TextView? = null
    private val imageUploaderNavigationHandler = ImageUploaderNavigationHandler(this, localRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_uploader)

        chooseButtonHandle()
        chooseLocalButtonHandle()
        downloadButtonHandle()
        uploadButtonHandle()
        imageNameHandle()

        uploadImageView = findViewById(R.id.uploadImageView)
        uploadProgressBar = findViewById(R.id.uploadProgressBar)
        uploadsTextView = findViewById(R.id.uploadsTextView)
    }


    private fun imageNameHandle() {
        imageNameEditText = findViewById(R.id.imageNameEditText)
    }

    private fun chooseButtonHandle() {
        chooseImageButton = findViewById(R.id.chooseImageButton)
        chooseImageButton?.setOnClickListener {
            Log.i("test:", "choose image button clicked")
            val imageDatabaseHandler = ImageDatabaseHandler(localRepository, imageUploaderNavigationHandler, this)
            imageDatabaseHandler.downloadImages()
            Log.d("returned:", localRepository.images.toString())
        }
    }

    private fun chooseLocalButtonHandle() {
        Log.i("test:", "choose local image button clicked")
        chooseLocalImageButton = findViewById(R.id.chooseLocalImageButton)
        chooseLocalImageButton?.setOnClickListener {

        }
    }

    private fun uploadButtonHandle() {
        uploadImageButton = findViewById(R.id.uploadImageButton)
        uploadImageButton?.setOnClickListener {
            Log.i("test:", "upload image button clicked")
            val image=Image("NewImageTest","Screenshot 2021-12-10 at 08.27.00.png")
            val imageDatabaseHandler=ImageDatabaseHandler(localRepository,imageUploaderNavigationHandler,this)
            imageDatabaseHandler.uploadImage(image)
        }
    }

    private fun downloadButtonHandle() {
        downloadImageButton = findViewById(R.id.downloadImageButton)
        downloadImageButton?.setOnClickListener {
            Log.i("test:", "download image button clicked")

            val imageStorageHandler = ImageStorageHandler(this)
            imageStorageHandler.getImage("Screenshot 2021-12-10 at 08.27.00.png")
        }
    }

    override fun openActivity() {
        imageUploaderNavigationHandler.openActivity()
    }

    override fun finishActivity() {
        imageUploaderNavigationHandler.finishActivity()
    }

    fun displayImage(imageUrl: Uri) {
        Picasso.with(this).load(imageUrl).into(uploadImageView)
    }
}