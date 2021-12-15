package com.example.myrecipes

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import java.io.IOException

// PICK_PHOTO_CODE is a constant integer
private const val PICK_PHOTO_CODE = 1046

class ImageLocalHandler(val imageUploader: ImageUploader) {

        // Trigger gallery selection for a photo
        fun onPickPhoto() {
            // Create intent for picking a photo from the gallery
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.
            if (intent.resolveActivity(imageUploader.getPackageManager()) != null) {
                // Bring up gallery to select a photo
                ActivityCompat.startActivityForResult(imageUploader,intent, PICK_PHOTO_CODE,null)
            }
        }

        fun loadFromUri(photoUri: Uri): Bitmap? {
            var image: Bitmap? = null
            try {
                // check version of Android on device
                image = if (Build.VERSION.SDK_INT > 27) {
                    // on newer versions of Android, use the new decodeBitmap method
                    val source: ImageDecoder.Source = ImageDecoder.createSource(imageUploader.getContentResolver(), photoUri)
                    ImageDecoder.decodeBitmap(source)
                } else {
                    // support older versions of Android by using getBitmap
                    MediaStore.Images.Media.getBitmap(imageUploader.getContentResolver(), photoUri)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return image
        }

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            if (data != null && requestCode == PICK_PHOTO_CODE) {
                val photoUri: Uri? = data.data

                // Load the image located at photoUri into selectedImage
                val selectedImage = photoUri?.let { loadFromUri(it) }

                imageUploader.loadImage(selectedImage)
            }
        }


}