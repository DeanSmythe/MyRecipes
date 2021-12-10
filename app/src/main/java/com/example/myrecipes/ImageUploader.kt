package com.example.myrecipes

import android.app.Activity
import android.content.Intent
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.squareup.picasso.Picasso

class ImageUploader : AppCompatActivity() {
    private var chooseImageButton: Button? = null
    private var uploadImageButton: Button? = null
    private var imageNameEditText: EditText? = null
    private var uploadImageView: ImageView? = null
    private var uploadProgressBar: ProgressBar? = null
    private var uploadTextView: TextView? = null
    private var resultUri: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_uploader)

        chooseButtonHandle()
        uploadButtonHandle()
        imageNameHandle()
        uploadImageView?.findViewById<TextView>(R.id.uploads_text_view)
        uploadProgressBar?.findViewById<ProgressBar>(R.id.upload_progress_bar)
        uploadTextView?.findViewById<TextView>(R.id.uploads_text_view)
    }


    private fun imageNameHandle() {
        imageNameEditText?.findViewById<EditText>(R.id.image_name_edit_text)
        imageNameEditText?.setOnClickListener {
            openActivity()
        }
    }

    private fun uploadButtonHandle() {
        uploadImageButton?.findViewById<Button>(R.id.upload_image_button)
        uploadImageButton?.setOnClickListener {
        }
    }

    private fun chooseButtonHandle() {
        chooseImageButton?.findViewById<Button>(R.id.choose_image_button)
        chooseImageButton?.setOnClickListener {
        }
    }

    private var launchSomeActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result != null && result.resultCode == Activity.RESULT_OK) {
            resultUri = result.data
            if (resultUri !=null){
                Picasso.
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