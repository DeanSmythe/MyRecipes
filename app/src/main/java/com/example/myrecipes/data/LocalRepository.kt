package com.example.myrecipes.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocalRepository(
    var images: MutableList<Image> = mutableListOf(),
    val user: String = ""
) : Parcelable {
    fun saveImages(newImages: MutableList<Image>) {
        images = newImages
    }

    fun addImage(image: Image) {
        images.add(image)
    }
}
