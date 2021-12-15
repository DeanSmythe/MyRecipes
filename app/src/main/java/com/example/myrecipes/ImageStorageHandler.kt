package com.example.myrecipes

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import com.example.myrecipes.data.Image
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class ImageStorageHandler(val imageUploader: ImageUploader) {
    val storage = Firebase.storage
    val storageRef = storage.reference
    private var refChild: StorageReference? = null

    fun getImage(imageUrl: String) {
        storageRef.child(imageUrl).downloadUrl.addOnSuccessListener {
            imageUploader.displayImage(it)
        }.addOnFailureListener {
            Log.i("ImageDownloader:", "unable to get Image")
        }
    }

    fun uploadImage(image: Image) {
        refChild = image.imageName?.let { storageRef.child(it) }
        imageUploader.uploadImageView?.isDrawingCacheEnabled = true
        imageUploader.uploadImageView?.buildDrawingCache()
        val bitmap = (imageUploader.uploadImageView?.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = refChild?.putBytes(data)
        if (uploadTask != null) {
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // ...
            }
        }
    }
}