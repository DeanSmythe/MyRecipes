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
class Recipe(
    val name: String? = null,
    val description: String? = null,
    val timetomake: Int? = null,
    val picture: String? = null,
    val rating: Int? = null,
    val diet: String? = null
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
        rating: Int,
        diet: String
    ) {

        val recipe = Recipe(name, description, timetomake, picture, rating, diet)
        db.collection("recipes").add(recipe)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
//            .result.toString()
//        return id.toString()
    }

//    fun writeRecipe(recipe: Recipe) {
//        db.collection("recipes").add(recipe)
//            .addOnSuccessListener { documentReference ->
//                Log.d(ContentValues.TAG, "DocumentSnapshot Recipe added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(ContentValues.TAG, "Error adding Recipe document", e)
//            }
//    }
//
//    fun updateRecipe(recipe: Recipe) {
//        db.collection("recipes").document(recipe.id)
//            .update(
//                mapOf(
//                    "name" to recipe.name,
//                    "description" to recipe.description,
//                    "timetomake" to recipe.timetomake,
//                    "picture" to recipe.picture,
//                    "rating" to recipe.rating
//                )
//            )
//    }
//
//    fun deleteRecipe(recipe: Recipe) {
//        db.collection("recipes").document(recipe.id).delete()
//    }
//
//    fun getRecipe(id: String): Recipe? {
//        val recipeRef = db.collection("recipes").document(id)
//        return recipeRef.get().result.toObject<Recipe>()
//    }
//
//    fun getRecipeByName(name: String): Recipe? {
//        val recipeRef = db.collection("recipes").document(name)
//        return recipeRef.get().result.toObject<Recipe>()
//    }
//
//    fun getAllRecipes(): List<Recipe> {
//        return db.collection("recipes").get().result.toObjects<Recipe>()
//    }
//
//    fun getRecipesByRating(rating: Int): List<Recipe> {
//        val recipesOverRating = db.collection("recipes").whereGreaterThanOrEqualTo("Rating", rating)
//        return recipesOverRating.get().result.toObjects<Recipe>()
//    }
//
//    fun getRecipesByDiet(diet: String): List<Recipe> {
//        val recipesMatch = db.collection("recipes").whereEqualTo("Diet", diet)
//        return recipesMatch.get().result.toObjects<Recipe>()
//    }
}