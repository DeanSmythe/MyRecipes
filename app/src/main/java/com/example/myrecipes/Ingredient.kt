package com.example.myrecipes

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects

@IgnoreExtraProperties
data class Ingredient(
    var name: String? = null,
    var description: String? = null,
    var uom: String? = null,
    var picture: String? = null
) {


    private val db = Firebase.firestore

    fun writeNewIngredient(name: String, description: String, uom: String, picture: String) {
        val ingredient = Ingredient(name, description, uom, picture)
        db.collection("ingredients").add(ingredient)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }


    fun getIngredient(id: String): Ingredient? {
        val ingredientRef = db.collection("ingredients").document(id)
        return ingredientRef.get().result.toObject<Ingredient>()
    }


    fun getIngredientByName(name: String): Ingredient? {
        val ingredientRef = db.collection("ingredients").document(name)
        return ingredientRef.get().result.toObject<Ingredient>()
    }

    fun getAllIngredients(): List<Ingredient> {
        return db.collection("ingredients").get().result.toObjects<Ingredient>()
    }

}