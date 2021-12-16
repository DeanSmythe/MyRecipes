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
    private var uploadViewButton: Button? = null
    private var downloadImageButton: Button? = null
    private var imageNameEditText: EditText? = null
    private lateinit var imageUrlEditText: EditText
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
        uploadViewButtonHandle()
        uploadImageView = findViewById(R.id.uploadImageView)
        uploadProgressBar = findViewById(R.id.transferProgressBar)
        uploadsTextView = findViewById(R.id.uploadsTextView)
        imageNameEditText = findViewById(R.id.imageNameEditText)
        imageUrlEditText = findViewById(R.id.imageUrlEditText)
        imageUrlEditText.isEnabled = false
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
            openLocalMedia()
        }
    }

    private fun uploadButtonHandle() {
        uploadImageButton = findViewById(R.id.uploadImageButton)
        uploadImageButton?.setOnClickListener {
            Log.i("test:", "upload image button clicked")
            if (imageNameEditText?.text.toString() != "" && imageUrlEditText.text.toString() != "") {
                val image = Image(imageNameEditText?.text.toString(), imageUrlEditText.text.toString())
                val imageDatabaseHandler = ImageDatabaseHandler(localRepository, imageUploaderNavigationHandler, this)
                imageDatabaseHandler.uploadImage(image)
            }
        }
    }

    private fun uploadViewButtonHandle() {
        uploadViewButton = findViewById(R.id.uploadViewButton)
        uploadViewButton?.setOnClickListener {
            Log.i("test:", "upload view button clicked")
            if (imageNameEditText?.text.toString() != "" && imageUrlEditText.text.toString() != "") {
                val image = Image(imageNameEditText?.text.toString(), imageNameEditText?.text.toString())
                val imageDatabaseHandler = ImageDatabaseHandler(localRepository, imageUploaderNavigationHandler, this)
                imageDatabaseHandler.uploadImage(image)
            }
        }
    }

    private fun downloadButtonHandle() {
        downloadImageButton = findViewById(R.id.downloadImageButton)
        downloadImageButton?.setOnClickListener {
            Log.i("test:", "download image button clicked")
            fetchImage()
        }
    }

    private fun fetchImage() {
        val imageDatabaseHandler = ImageDatabaseHandler(localRepository, imageUploaderNavigationHandler, this)
        val imageName = imageNameEditText?.text
        if (imageName != null) {
            if (imageName.isNotBlank()) {
                imageDatabaseHandler.findUrl(imageName.toString())
            }
        }
    }

    fun displayImage(imageUrl: Uri) {
        Picasso.with(this).load(imageUrl).into(uploadImageView)
    }

    fun displayChosenImage(currentImage: Image) {
//        Picasso.with(this).load(currentImage.imageUrl).into(uploadImageView)
        imageNameEditText?.setText(currentImage.imageName, TextView.BufferType.EDITABLE)
        imageUrlEditText.setText(currentImage.imageUrl, TextView.BufferType.NORMAL)
        fetchImage()
    }

    fun resultedUrl(image: Image) {
        if (image.imageUrl != null && image.imageUrl != "") {
            val imageStorageHandler = ImageStorageHandler(this)
            image.imageUrl.let { imageStorageHandler.getImage(it) }
            imageUrlEditText.setText(image.imageUrl, TextView.BufferType.EDITABLE)
            localRepository.currentImage = image
            Picasso.with(this).load(image.imageUrl).into(uploadImageView)
        }
    }

    fun loadImage(selectedImage: Bitmap?) {
        val ivPreview: ImageView = findViewById(R.id.uploadImageView) as ImageView
        ivPreview.setImageBitmap(selectedImage)
    }


    fun openLocalMedia() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        launchSomeActivity.launch(intent)
    }

    private var launchSomeActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result != null && result.resultCode == Activity.RESULT_OK) {
            val data = result.data?.data
            data?.path

            Picasso.with(this).load(data).into(uploadImageView)
            imageNameEditText?.setText("", TextView.BufferType.NORMAL)
            imageUrlEditText.setText(data.toString(), TextView.BufferType.NORMAL)
        }
    }
}
