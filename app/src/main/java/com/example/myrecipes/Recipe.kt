package com.example.myrecipes

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import java.util.*

@IgnoreExtraProperties
data class Recipe(
    val name: String? = null,
    val description: String? = null,
    val timetomake: Int? = null,
    val picture: String? = null,
    val rating: Int? = null
) {

    private val db = Firebase.firestore
    var id: String = ""
        get() {
           return field
        }

    fun writeNewRecipe(
        name: String,
        description: String,
        timetomake: Int,
        picture: String,
        rating: Int
    ): String {

        val recipe = Recipe(name, description, timetomake, picture, rating)
        id = db.collection("recipes").add(recipe)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
            .result.toString()
        return id.toString()
    }

    fun updateRecipe(recipe: Recipe) {
        db.collection("recipes").document(recipe.id)
            .update(
                mapOf(
                    "name" to recipe.name,
                    "description" to recipe.description,
                    "timetomake" to recipe.timetomake,
                    "picture" to recipe.picture,
                    "rating" to recipe.rating
                )
            )
    }

    fun deleteRecipe(recipe: Recipe) {
        db.collection("recipes").document(recipe.id).delete()
    }

    fun getRecipe(id: String): Recipe? {
        val recipeRef = db.collection("recipes").document(id)
        return recipeRef.get().result.toObject<Recipe>()
    }

    fun getRecipeByName(name: String): Recipe? {
        val recipeRef = db.collection("recipes").document(name)
        return recipeRef.get().result.toObject<Recipe>()
    }

    fun getAllRecipes(): List<Recipe> {
        return db.collection("recipes").get().result.toObjects<Recipe>()
    }

    fun getRecipesByRating(rating: Int): List<Recipe> {
        val recipesOverRating = db.collection("recipes").whereGreaterThanOrEqualTo("Rating", rating)
        return recipesOverRating.get().result.toObjects<Recipe>()
    }
}