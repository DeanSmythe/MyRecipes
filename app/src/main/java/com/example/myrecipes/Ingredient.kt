package com.example.myrecipes

import android.content.ContentValues
import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Ingredient(
    val name: String? = null,
    val description: String? = null,
    val uom: String? = null,
    val picture: String? = null,
    val recipeName: String? = null
):Parcelable {
    val db = Firebase.firestore
    fun writeNewIngredient(
        name: String,
        description: String,
        uom: String,
        picture: String,
        recipeName: String? = null
    ) {
        val ingredient = Ingredient(name, description, uom, picture)
        writeNewIngredient(ingredient)
    }

    fun writeNewIngredient(ingredient: Ingredient) {
        db.collection("ingredients").add(ingredient)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

}