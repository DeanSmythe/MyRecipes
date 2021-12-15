package com.example.myrecipes

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myrecipes.data.LocalRepository

class ImageUploaderNavigationHandler(private val imageUploader: ImageUploader, private var localRepository: LocalRepository) {
    fun openActivity() {
        val intent = Intent(imageUploader, ImagePicker::class.java)
        intent.putExtra("localRepository", localRepository)
        launchSomeActivity.launch(intent)
    }

    fun finishActivity() {
    }

    private var launchSomeActivity = imageUploader.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result != null && result.resultCode == Activity.RESULT_OK) {
            val data = result.data?.getParcelableExtra<LocalRepository>("returnLocalRepository")
            if (data != null) {
                localRepository = data
//                Picasso.with(imageUploader).load(localRepository.currentImage?.imageUrl).into(imageUploader.uploadImageView)
                localRepository.currentImage?.let { imageUploader.displayChosenImage(it) }
            }
        }
    }
}