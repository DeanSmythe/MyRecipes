package com.example.myrecipes

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class DatabaseRecipeIngLoader {
    private val db = Firebase.firestore

    fun loadDefaultRecipeIng() {

        val recipeData = hashMapOf(
            "recipe_ID" to 1,
            "ingredient_ID" to 4,
            "quantity" to 2,
        )
        val recipeData2 = hashMapOf(
            "recipe_ID" to 1,
            "ingredient_ID" to 5,
            "quantity" to 500,
        )

        db.collection("recipe_ingredients").add(recipeData)
            .addOnSuccessListener{ documentReference ->
            Log.d(
                ContentValues.TAG,
                "DocumentSnapshot added with ID: ${documentReference.id}"
            )
        }
            .addOnFailureListener{ error ->
            Log.w(ContentValues.TAG, "Error adding document", error)
        }
            .result.toString()

        db.collection("recipe_ingredients").add(recipeData2)
            .addOnSuccessListener{ documentReference ->
            Log.d(
                ContentValues.TAG,
                "DocumentSnapshot added with ID: ${documentReference.id}"
            )
        }
            .addOnFailureListener{ error ->
            Log.w(ContentValues.TAG, "Error adding document", error)
        }
//            .result.toString()
    }


    suspend fun emptyRecipeIng() = coroutineScope {
        launch {
            Log.println(Log.WARN, "", "In function")
            Log.println(Log.WARN, "", "Database $db")
            db.collection("recipe_ingredients").get()
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
                    Log.println(Log.WARN, "", "Leaving function")
                }
        }
    }
}