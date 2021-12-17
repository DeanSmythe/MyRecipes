package com.example.myrecipes

import android.content.ContentValues
import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
class Recipe(
    val name: String? = null,
    val description: String? = null,
    val timetomake: Int? = null,
    val picture: String? = null,
    val rating: Int? = null,
    val diet: String? = null
):Parcelable {
    private val db = Firebase.firestore
    val recipes = mutableListOf<Recipe>()

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
    }

    fun loadRecipes() {
        db.collection("recipes").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    recipes.add(packetiseRecipe(document))
                }
            }
            .addOnFailureListener { error ->
                Log.w(ContentValues.TAG, "Error reading document", error)
            }
    }

    fun fetchRecipes(): MutableList<Recipe> {
        return recipes
    }

    private fun packetiseRecipe(document: QueryDocumentSnapshot): Recipe {
        return Recipe(
            document.data["name"].toString(),
            document.data["description"].toString(),
            document.data["timetomake"] as Int?,
            document.data["picture"].toString(),
            document.data["rating"] as Int?,
            document.data["diet"].toString(),
        )
    }
}