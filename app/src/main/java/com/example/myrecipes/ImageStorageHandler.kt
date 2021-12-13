package com.example.myrecipes

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ImageStorageHandler(val imageUploader: ImageUploader) {
    val storage = Firebase.storage
    val storageRef = storage.reference

    fun getImage(imageUrl: String){
        storageRef.child(imageUrl).downloadUrl.addOnSuccessListener {
            imageUploader.displayImage(it)
        }.addOnFailureListener {
            Log.i("ImageDownloader:","unable to get Image")
        }
    }

}