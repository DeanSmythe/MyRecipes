package com.example.myrecipes

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myrecipes.data.LocalRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image_uploader.*

class ImageUploaderNavigationHandler(private val activity: AppCompatActivity, private var localRepository: LocalRepository) {
    fun openActivity() {
        val intent = Intent(activity, ImagePicker::class.java)
        intent.putExtra("localRepository", localRepository)
        launchSomeActivity.launch(intent)
    }

    fun finishActivity() {
    }

    private var launchSomeActivity = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result != null && result.resultCode == Activity.RESULT_OK) {
            val data = result.data?.getParcelableExtra<LocalRepository>("returnLocalRepository")
            if (data != null) {
                localRepository = data
                Picasso.with(activity).load(localRepository.currentImage?.imageUrl).into(activity.uploadImageView)
            }
        }
    }
}