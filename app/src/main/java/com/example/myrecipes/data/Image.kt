package com.example.myrecipes.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    val imageName: String? = null,
    val imageUrl: String? = null
): Parcelable
