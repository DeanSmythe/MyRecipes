package com.example.myrecipes

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myrecipes.data.Image
import com.example.myrecipes.data.ImageDatabaseHandler
import com.example.myrecipes.data.LocalRepository
import com.squareup.picasso.Picasso
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class ImageUploader : AppCompatActivity(), ActivityNavigationHandler {
    private val localRepository: LocalRepository = LocalRepository()
    private var chooseImageButton: Button? = null
    private var chooseLocalImageButton: Button? = null
    private var uploadImageButton: Button? = null
    private var downloadImageButton: Button? = null
    private var imageNameEditText: EditText? = null
    private var imageUrlEditText: EditText? = null
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
        uploadImageView = findViewById(R.id.uploadImageView)
        uploadProgressBar = findViewById(R.id.transferProgressBar)
        uploadsTextView = findViewById(R.id.uploadsTextView)
        imageNameEditText = findViewById(R.id.imageNameEditText)
        imageUrlEditText = findViewById(R.id.imageUrlEditText)
    }

    private fun chooseButtonHandle() {
        chooseImageButton = findViewById(R.id.chooseImageButton)
        chooseImageButton?.setOnClickListener {
            Log.i("test:", "choose image button clicked")
            val imageDatabaseHandler = ImageDatabaseHandler(localRepository, imageUploaderNavigationHandler, this)
            imageDatabaseHandler.downloadImages()
        }
    }

    private fun chooseLocalButtonHandle() {
        Log.i("test:", "choose local image button clicked")
        chooseLocalImageButton = findViewById(R.id.chooseLocalImageButton)
        chooseLocalImageButton?.setOnClickListener {
            val imageLocalHandler = ImageLocalHandler(this)
            imageLocalHandler.onPickPhoto()
        }
    }

    private fun uploadButtonHandle() {
        uploadImageButton = findViewById(R.id.uploadImageButton)
        uploadImageButton?.setOnClickListener {
            Log.i("test:", "upload image button clicked")
            val image = Image(imageNameEditText.toString(), imageUrlEditText.toString())
            val imageDatabaseHandler = ImageDatabaseHandler(localRepository, imageUploaderNavigationHandler, this)
            imageDatabaseHandler.uploadImage(image)
        }
    }

    private fun downloadButtonHandle() {
        downloadImageButton = findViewById(R.id.downloadImageButton)
        downloadImageButton?.setOnClickListener {
            Log.i("test:", "download image button clicked")

            val imageDatabaseHandler = ImageDatabaseHandler(localRepository, imageUploaderNavigationHandler, this)
            val imageName = imageNameEditText?.text
            if (imageName != null) {
                if (imageName.isNotBlank()) {
                    imageDatabaseHandler.findUrl(imageName.toString())
                }
            }
        }
    }

    fun displayImage(imageUrl: Uri) {
        Picasso.with(this).load(imageUrl).into(uploadImageView)
    }

    fun displayChosenImage(currentImage: Image) {
        Picasso.with(this).load(currentImage.imageUrl).into(uploadImageView)
        imageNameEditText?.setText(currentImage.imageName, TextView.BufferType.EDITABLE)
        imageUrlEditText?.setText(currentImage.imageUrl, TextView.BufferType.EDITABLE)
    }

    fun resultedUrl(image: Image) {
        val imageStorageHandler = ImageStorageHandler(this)
        image.imageUrl?.let { imageStorageHandler.getImage(it) }
        imageUrlEditText?.setText(image.imageUrl, TextView.BufferType.EDITABLE)
        localRepository.currentImage = image
    }

    override fun openActivity() {
        imageUploaderNavigationHandler.openActivity()
    }

    override fun finishActivity() {
        imageUploaderNavigationHandler.finishActivity()
    }

     fun loadImage(selectedImage: Bitmap?) {
        val ivPreview: ImageView = findViewById(R.id.uploadImageView) as ImageView
        ivPreview.setImageBitmap(selectedImage)
    }

}