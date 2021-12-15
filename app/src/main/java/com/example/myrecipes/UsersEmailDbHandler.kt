package com.example.myrecipes

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import com.example.myrecipes.utils.FirebaseUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UsersEmailDbHandler {
        private val db = Firebase.firestore

        fun newUser(email: String) {
            val emailMap = hashMapOf("email" to email)
            db.collection("email").document(email).set(emailMap)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference}")
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }
        }

    fun newCupboardItem(ingredient: String, Amount: Int, Uom: String){
        val ingredientDetails = hashMapOf(
            "Amount" to Amount,
            "Uom" to Uom
        )
        val email = FirebaseUtils.firebaseAuth.currentUser?.email
        Log.d(TAG, email!!)
        val dbGetUser = db.collection("email").document(email!!).collection("MyCupboard").document(ingredient)
        dbGetUser.set(ingredientDetails)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID (adding ingredient): ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

}