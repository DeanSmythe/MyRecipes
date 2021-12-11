package com.example.myrecipes.data

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Image(
    val imageName: String? = null,
    val imageUrl: String? = null
) {
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

    fun downloadImage(): MutableList<Image> {
        val images = mutableListOf<Image>()
        db.collection("images").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(ContentValues.TAG, "Read Image ID: ${document.id}::${document.data}")
                    images.add(Image(document.data.get("imageName").toString(), document.data.get("imageUrl").toString()))
                }
            }
            .addOnFailureListener { error ->
                Log.w(ContentValues.TAG, "Error reading document", error)
            }
        return images
    }
}
