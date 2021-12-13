package com.example.myrecipes.data

import android.content.ContentValues
import android.util.Log
import com.example.myrecipes.ImageUploaderNavigationHandler
import com.example.myrecipes.ImageUploader
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ImageDatabaseHandler(val localRepository: LocalRepository, private val imagePickerNavigationHandler: ImageUploaderNavigationHandler, private val imageUploader: ImageUploader) {
    private val db = Firebase.firestore

    fun uploadImage(imageName: String, imageUrl: String) {
        val image = Image(imageName, imageUrl)
        db.collection("images").add(image)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "Added Image ID: ${documentReference.id}")
            }
            .addOnFailureListener { error ->
                Log.w(ContentValues.TAG, "Error adding document", error)
            }
    }

    fun downloadImages() {
        db.collection("images").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "Read Image ID: ${document.id}::${document.data}")
                    Log.d(ContentValues.TAG, "NAME: ${document.data["imageName"].toString()}")
                    Log.d(ContentValues.TAG, "URL: ${document.data["imageUrl"].toString()}")

                    val imageName = document.data["imageName"].toString()
                    val imageUrl = document.data["imageUrl"].toString()
                    val image = Image(imageName, imageUrl)
                    localRepository.addImage(image)
                    imagePickerNavigationHandler.openActivity()
                }
            }
            .addOnFailureListener { error ->
                Log.w(ContentValues.TAG, "Error reading document", error)
            }
    }
}
