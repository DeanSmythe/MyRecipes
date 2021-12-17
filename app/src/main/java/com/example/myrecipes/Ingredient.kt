package com.example.myrecipes

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@IgnoreExtraProperties
data class Ingredient(
    val name: String? = null,
    val description: String? = null,
    val uom: String? = null,
    val picture: String? = null,
    val recipeName: String? = null
) {
    val db = Firebase.firestore
    fun writeNewIngredient(
        name: String,
        description: String,
        uom: String,
        picture: String,
        recipeName: String? = null
    ) {
        val ingredient = hashMapOf(
            "name" to name,
            "description" to description,
            "uom" to uom,
            "picture" to picture,
            "recipeName" to recipeName
        )
        db.collection("ingredients").add(ingredient)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

}
