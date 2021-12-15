package com.example.myrecipes

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myrecipes.data.Image
import com.example.myrecipes.data.ImageDatabaseHandler
import com.example.myrecipes.data.LocalRepository
import com.squareup.picasso.Picasso
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class ImageUploader : AppCompatActivity() {
    private var localRepository: LocalRepository = LocalRepository()
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
        chooseLocalImageButton = findViewById(R.id.chooseLocalImageButton)
        chooseLocalImageButton?.setOnClickListener {
            Log.i("test:", "choose local image button clicked")
            openActivity()
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

    fun loadImage(selectedImage: Bitmap?) {
        val ivPreview: ImageView = findViewById(R.id.uploadImageView) as ImageView
        ivPreview.setImageBitmap(selectedImage)
    }


    fun openActivity() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        launchSomeActivity.launch(intent)


    }

    private var launchSomeActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result != null && result.resultCode == Activity.RESULT_OK) {
            val data = result.data?.data
            Picasso.with(this).load(data).into(uploadImageView)
            localRepository.currentImage?.let { displayChosenImage(it) }
        }
    }
}
