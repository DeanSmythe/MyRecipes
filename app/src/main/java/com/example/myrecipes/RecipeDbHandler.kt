package com.example.myrecipes

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.example.myrecipes.utils.FirebaseUtils
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp

class RecipeDbHandler {
    private val db = Firebase.firestore

    fun newRecipeItem(RecipeName: String, RecipeInstructions: String, TimeToMake: Int, DietReq: String, Picture: String? =null){
        val Date = FieldValue.serverTimestamp()
        val ingredientDetails = hashMapOf(
            "Recipe" to RecipeName,
            "RecipeInstructions" to RecipeInstructions,
            "TimeToMake" to TimeToMake,
            "Dietiary Requirments" to DietReq,
            "Picture" to Picture,
            "Date" to Date
        )
        val email = FirebaseUtils.firebaseAuth.currentUser?.email
        Log.d(ContentValues.TAG, email!!)
        val dbGetUser = db.collection("recipe").document(email!!).collection("MyRecipes").document(RecipeName)
        dbGetUser.set(ingredientDetails)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID (adding ingredient): ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

    fun setIngredientsRecipe(ingredient: String, Amount: Int, Uom: String, RecipeName: String) {
        val ingredientAddDetails = hashMapOf(
            "Amount" to Amount,
            "Uom" to Uom
        )
        val email = FirebaseUtils.firebaseAuth.currentUser?.email
        Log.d(ContentValues.TAG, email!!)
        val dbGetUserIngredient =
            db.collection("recipe").document(email!!).collection("MyRecipes").document(RecipeName).collection("Ingredients").document(ingredient)
        dbGetUserIngredient.set(ingredientAddDetails)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    ContentValues.TAG,
                    "DocumentSnapshot added with ID (adding ingredient): ${documentReference}"
                )
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }
//
//    fun getLastRecipe(){
//        val email = FirebaseUtils.firebaseAuth.currentUser?.email
//        val lastRecipe = db.collection("recipe").document(email!!).collection("MyRecipes").orderBy("Date",Query.Direction.ASCENDING).limit(1).get().addOnSuccessListener { documents ->
//            for (document in documents){
//
//            var ingredient = Ingredient(name = document.id, description = document.data.get("Amount")
//                .toString(), uom = document.data.get("Uom").toString(), picture = findImage(ingredient = document.id))
//            ingredientAdapter.populateRecyclerView(ingredient)
//        }
//    }
//    }

}
