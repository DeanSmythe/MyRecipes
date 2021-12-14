package com.example.myrecipes.data

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.example.myrecipes.ImageUploaderNavigationHandler
import com.example.myrecipes.ImageUploader
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ImageDatabaseHandler(val localRepository: LocalRepository, private val imagePickerNavigationHandler: ImageUploaderNavigationHandler, private val imageUploader: ImageUploader) {
    private val db = Firebase.firestore

    fun uploadImage(image: Image) {
        executeUploadRequest(image)
    }

    private fun executeUploadRequest(image: Image) {
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

                    val image = packetiseImage(document)
                    localRepository.addImage(image)
                }
                imagePickerNavigationHandler.openActivity()
            }
            .addOnFailureListener { error ->
                Log.w(ContentValues.TAG, "Error reading document", error)
            }
    }

    private fun packetiseImage(document: QueryDocumentSnapshot): Image {
        val imageName = document.data["imageName"].toString()
        val imageUrl = document.data["imageUrl"].toString()
        val image = Image(imageName, imageUrl)
        return image
    }

    fun findUrl(imageName: String) {
        val images = mutableListOf<Image>()
        db.collection("images")
            .whereEqualTo("imageName", imageName)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")

                    val image = packetiseImage(document)
                    images.add(image)
                }
                imageUploader.resultedUrl(images.first())
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

    }
}
