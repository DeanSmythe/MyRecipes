package com.example.myrecipes

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

val db = Firebase.firestore

class DatabaseIngLoader {

    fun loadDefaultIngToDb() {
//       val ingredient = Ingredient("Milk", "Semi-skimmed", "ml", "#")
          val test1 = db.collection("ingredients")
          val ingMap = hashMapOf("name" to "Milk", "description" to "Semi-skimmed", "uom" to "ml", "Picture" to "#")
                  test1.add(ingMap)
//        db.collection("ingredients").add(ingredient)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { error ->
                Log.w(ContentValues.TAG, "Error adding document", error)
            }
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
