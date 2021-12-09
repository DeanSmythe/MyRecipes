package com.example.myrecipes

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Recipe(
    val name: String? = null,
    val description: String? = null,
    val timetomake: Int? = null,
    val picture: String? = null,
    val rating: Int? = null
) {

    private val db = Firebase.firestore

    fun setNewRecipe(
        name: String,
        description: String,
        timetomake: Int,
        picture: String,
        rating: Int
    ) {
        val recipe = Recipe(name, description, timetomake, picture, rating)
        db.collection("recipes").add(recipe)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }
}