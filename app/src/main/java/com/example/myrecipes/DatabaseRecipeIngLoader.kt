package com.example.myrecipes

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DatabaseRecipeIngLoader {
    private val db = Firebase.firestore

    fun loadDefaultRecipeIng() {

        val recipeData1 = hashMapOf(
            "recipe_name" to "Beans on toast",
            "ingredient_name" to "Bread",
            "quantity" to "2",
        )
        val recipeData2 = hashMapOf(
            "recipe_name" to "Beans on toast",
            "ingredient_name" to "Baked Beans",
            "quantity" to "400",
        )
        val recipeData3 = hashMapOf(
            "recipe_name" to "Pancakes",
            "ingredient_name" to "Flour",
            "quantity" to "400",
        )

        val recipeData4 = hashMapOf(
            "recipe_name" to "Pancakes",
            "ingredient_name" to "Milk",
            "quantity" to "400",
        )
        val recipeData5 = hashMapOf(
            "recipe_name" to "Pancakes",
            "ingredient_name" to "Egg",
            "quantity" to "2",
        )

        val recipeDataArray: ArrayList< HashMap< String, String>> = ArrayList()
        recipeDataArray.add(recipeData1)
        recipeDataArray.add(recipeData2)
        recipeDataArray.add(recipeData3)
        recipeDataArray.add(recipeData4)
        recipeDataArray.add(recipeData5)

        for (recipeData in recipeDataArray) {
            Log.d(ContentValues.TAG, "Trying  $recipeData")

            db.collection("recipe_ingredients")
                .whereEqualTo("recipe_name", "${recipeData["recipe_name"]}")
                .whereEqualTo("ingredient_name", "${recipeData["ingredient_name"]}").get()
                .addOnSuccessListener { docSnapshot ->
                    if (docSnapshot.isEmpty) {
                        db.collection("recipe_ingredients").add(recipeData)
                            .addOnSuccessListener { documentReference ->
                                Log.d(
                                    ContentValues.TAG,
                                    "DocumentSnapshot added with ID: ${documentReference.id}"
                                )
                            }
                            .addOnFailureListener { error ->
                                Log.w(ContentValues.TAG, "Error adding RecipeIng document", error)
                            }
                    } else {
                        Log.d("", "RecipeIng already exists")
                    }
                }
                .addOnFailureListener() { TODO() }
        }
    }

    fun emptyRecipeIng() {
        Log.println(Log.WARN, "", "In function")
        Log.println(Log.WARN, "", "Database $db")
        db.collection("recipe_ingredients").get()
            .addOnCompleteListener {
                Log.println(Log.WARN, "", "Querying to empty Recipe Ing")
                for (document in it.result.documents) {
                    Log.println(
                        Log.WARN,
                        "",
                        "DocumentSnapshot with ID: ${document.id} deleted"
                    )
                    document.reference.delete()
                }
                Log.println(Log.WARN, "", "Leaving emptyRecipeIng function")
            }
    }
}
