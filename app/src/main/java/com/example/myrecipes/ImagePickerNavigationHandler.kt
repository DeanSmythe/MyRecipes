package com.example.myrecipes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myrecipes.data.LocalRepository
import com.squareup.picasso.Picasso

class ImagePickerNavigationHandler(val imageUploader: ImageUploader, val localRepository: LocalRepository) {
    private var resultUri: Uri? = null
    private var launchSomeActivity = imageUploader.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result != null && result.resultCode == Activity.RESULT_OK) {
            resultUri = result.data?.data
            if (resultUri != null) {
                Picasso.with(imageUploader).load(resultUri).into(imageUploader.uploadImageView)
            }
        }
    }

    fun openActivity() {
        val intent = Intent(imageUploader, ImagePicker::class.java)
        intent.putExtra("localRepository", localRepository)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_RESTRICTION_ENTRIES
        launchSomeActivity.launch(intent)
    }

}