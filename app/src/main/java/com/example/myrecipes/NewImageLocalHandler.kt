package com.example.myrecipes

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.myrecipes.data.LocalRepository

private const val CAMERA_PERMISSION_REQUEST_CODE = 999

class NewImageLocalHandler(val imageUploader: ImageUploader) {
    fun openActivity() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
//        if (ActivityCompat.checkSelfPermission(imageUploader.applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(imageUploader, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), CAMERA_PERMISSION_REQUEST_CODE)
//        } else {
//            ActivityCompat.OnRequestPermissionsResultCallback(CAMERA_PERMISSION_REQUEST_CODE, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),"")
        launchSomeActivity.launch(intent)
//        }
    }

    fun finishActivity() {
    }

    private var launchSomeActivity = imageUploader.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result != null && result.resultCode == Activity.RESULT_OK) {
            Log.i("Success", "Success")
        }
    }

//    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//
//    }


}