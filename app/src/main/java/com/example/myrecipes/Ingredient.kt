package com.example.myrecipes

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.IgnoreExtraProperties
import java.lang.Boolean.TRUE

@IgnoreExtraProperties
data class Ingredient(
    val name: String? = null,
    val description: String? = null,
    val uom: String? = null,
    val picture: String? = null
) {

    private val db: FirebaseFirestore

    init {
        db = setDb(TRUE)
    }

    fun writeNewIngredient(name: String, description: String, uom: String, picture: String) {
        val ingredient = Ingredient(name, description, uom, picture)
        db.collection("ingredients").add(ingredient)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }


    private fun setDb(emulator: Boolean): FirebaseFirestore {
        val db = Firebase.firestore
        if (emulator) {
            val settings = FirebaseFirestoreSettings.Builder()
                .setHost("10.0.2.2:8080")
                .setPersistenceEnabled(false)
                .setSslEnabled(false)
                .build()
            db.firestoreSettings = settings
        }
        return db
    }
}