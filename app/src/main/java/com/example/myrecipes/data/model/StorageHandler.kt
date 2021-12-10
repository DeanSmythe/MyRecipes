package com.example.myrecipes.data.model

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class StorageHandler {

    var storage = Firebase.storage
    val fireStorageRef = storage.reference
    var imagesRef = fireStorageRef.child("images")
    val filename = "space.jpg"
    val spaceRef = imagesRef.child(filename)
    val path = spaceRef.path
    val name = spaceRef.name

    fun setupReferences() {
        storage = Firebase.storage("gs://mymenu-aeb25.appspot.com")
        imagesRef = spaceRef.parent ?: imagesRef
    }

    fun uploadFile(){

    }

    fun downloadFile(){

    }
//    val metadata= StorageMetadata.Builder()
//        .setCustomMetadata("caption",mImageText.getText().toString())

//    fun ii(){
//        // Get the data from an ImageView as bytes
//        val imageView =null
//        imageView.isDrawingCacheEnabled = true
//        imageView.buildDrawingCache()
//        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
//        val baos = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//        val data = baos.toByteArray()
//
//        var uploadTask = imagesRef.putBytes(data)
//        uploadTask.addOnFailureListener {
//            // Handle unsuccessful uploads
//        }.addOnSuccessListener { taskSnapshot ->
//            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
//            // ...
//        }
//    }
}