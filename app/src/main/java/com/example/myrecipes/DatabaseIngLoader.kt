package com.example.myrecipes

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

val db = Firebase.firestore

class DatabaseIngLoader {

    fun loadDb() {
        var newRef = Ingredient().writeNewIngredient("Flour", "Plain flour", "grams", "")
        newRef = Ingredient().writeNewIngredient("Milk", "Plain flour", "grams", "")
        newRef = Ingredient().writeNewIngredient("Egg", "Medium egg", "each", "")
        newRef = Ingredient().writeNewIngredient("Butter", "Unsalted", "grams", "")
        newRef = Ingredient().writeNewIngredient("SR Flour", "Self Raising flour", "grams", "")
        newRef = Ingredient().writeNewIngredient("Rice", "Brown rice", "grams", "")

    }

    fun emptyIngredients() {
        Log.println( Log.WARN, "","In function")
        Log.println( Log.WARN, "","Database ${db}")
        db.collection("ingredients").get()
            .addOnCompleteListener{
                Log.println(Log.WARN, "", "Querying to empty")
                for (document in it.result.documents) {
                    Log.println(Log.WARN, "", "DocumentSnapshot with ID: ${document.id}")
                    document.reference.delete()
            }
        }
        Log.println( Log.WARN, "","Leaving function")
    }
}
