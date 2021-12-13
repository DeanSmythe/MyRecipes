package com.example.myrecipes.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocalRepository constructor(
    var images: MutableList<Image> = mutableListOf(),
    val user: String = "",
    var currentImage: Image? = null
) : Parcelable {
    fun addImage(image: Image) {
        images.add(image)
    }

    fun saveCurrentImage(image: Image) {
        currentImage = image
    }


    //recipe table

    //ingredients

    //cupboard


}
