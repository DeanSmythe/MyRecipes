package com.example.myrecipes.utils

import android.content.ContentValues
import android.util.Log
import com.example.myrecipes.data.Image
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Recipe(
    val ingredients: String,
    val method: String,
    val notes: String,
    val recipeName: String,
)

class RecipeDatebaseHandler {
    private val db = Firebase.firestore
    fun getData(recipeName: String) {
        val recipeList = mutableListOf<Recipe>()

        db.collection("recipeList").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val recipe = packetise(document)
                    recipeList.add(recipe)
                }
                val a=1
            }
            .addOnFailureListener { error ->
                Log.w(ContentValues.TAG, "Error reading document", error)
            }

    }

    private fun packetise(document: QueryDocumentSnapshot): Recipe {

        return   Recipe(
                document.data["ingredients"].toString(),
                document.data["method"].toString(),
                document.data["notes"].toString(),
                document.data["recipeName"].toString(),
                )
        }

}
