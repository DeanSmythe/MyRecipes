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


    var id: String = ""
        get() {
            return field
        }

    fun writeNewIngredient(
        name: String,
        description: String,
        uom: String,
        picture: String
    ): String {
        val ingredient = Ingredient(name, description, uom, picture)
        val ingredientData = hashMapOf(ingredient.name to "name", ingredient.description to "description", ingredient.uom to "uom", ingredient.picture to "picture")
        id = db.collection("ingredients").add(ingredientData)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
            .result.toString()
        return id.toString()
    }

    fun writeIngredient(ingredient: Ingredient) : String {
        val ingredientData = hashMapOf(ingredient.name to "name", ingredient.description to "description", ingredient.uom to "uom", ingredient.picture to "picture")
        id = db.collection("ingredients").add(ingredientData)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
            .result.toString()
        return id.toString()
    }

    fun updateIngredient(ingredient: Ingredient) {
        db.collection("ingredients").document(ingredient.id)
            .update(
                mapOf(
                    "name" to ingredient.name,
                    "description" to ingredient.description,
                    "uom" to ingredient.uom,
                    "picture" to ingredient.picture
                )
            )
    }

    fun deleteIngredient(ingredient: Ingredient) {
        db.collection("ingredients").document(ingredient.id).delete()
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