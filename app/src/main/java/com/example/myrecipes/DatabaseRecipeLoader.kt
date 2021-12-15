package com.example.myrecipes

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DatabaseRecipeLoader {
    private val db = Firebase.firestore
    var allRecipes: ArrayList<Recipe> = ArrayList()

    fun loadDefaultRecipeToDb() {
        val recipe1 = Recipe("Beans on Toast", "A Family Favourite!", 10, "#", 5, "VEGAN")
        allRecipes.add(recipe1)
        val recipe2 =
            Recipe("Pancakes", "Sweet or Savoury, a quick fix.", 20, "#", 4, "VEGETARIAN")
        allRecipes.add(recipe2)
        val recipe3 = Recipe("Pasta al Pomodoro", "Fast and Filling", 25, "#", 4, "VEGETARIAN")
        allRecipes.add(recipe3)

        for (recipe in allRecipes) {
            val recipeData = hashMapOf(
                "name" to recipe.name,
                "description" to recipe.description,
                "uom" to recipe.timetomake,
                "picture" to recipe.picture,
                "rating" to recipe.rating,
                "diet" to recipe.diet.toString()
            )

            db.collection("recipes").whereEqualTo("name", "${recipe.name}").get()
                .addOnSuccessListener { docSnapshot ->
                    if (docSnapshot.isEmpty) {
                        db.collection("recipes").add(recipeData)
                            .addOnSuccessListener { documentReference ->
                                Log.d(
                                    ContentValues.TAG,
                                    "DocumentSnapshot, Recipe, added with ID: ${documentReference.id}"
                                )
                            }
                            .addOnFailureListener { error ->
                                Log.w(ContentValues.TAG, "Error adding recipe document", error)
                            }
                    } else {
                        Log.d("", "Recipe already exists")
                    }
                }
                .addOnFailureListener() { TODO() }
        }
    }

    fun emptyRecipes() {
        Log.println(Log.WARN, "", "In emptyRecipes function")
        Log.println(Log.WARN, "", "Database $db")
        db.collection("recipes").get()
            .addOnCompleteListener {
                Log.println(Log.WARN, "", "Querying to empty")
                for (document in it.result.documents) {
                    Log.println(
                        Log.WARN,
                        "",
                        "DocumentSnapshot with ID: ${document.id} deleted"
                    )
                    document.reference.delete()
                }
                Log.println(Log.WARN, "", "Leaving delete recipe function")
            }
    }
}



