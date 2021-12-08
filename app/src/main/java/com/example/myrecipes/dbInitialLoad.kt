package com.example.myrecipes

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class dbInitialLoad {

    // Access a Cloud Firestore instance from your Activity

    val db = Firebase.firestore

//Write Data

    // Create a new user with a first and last name
    val user = hashMapOf(
        "first" to "Ada",
        "last" to "Lovelace",
        "born" to 1815
    )




fun writeToDB() {
// Add a new document with a generated ID
    db.collection("users")
        .add(user)
        .addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }
}

    fun readFromDB() {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

    }
}


//    // Create a new user with a first, middle, and last name
//    val user = hashMapOf(
//        "first" to "Alan",
//        "middle" to "Mathison",
//        "last" to "Turing",
//        "born" to 1912
//    )
//
//// Add a new document with a generated ID
//    db.collection("users")
//    .add(user)
//    .addOnSuccessListener { documentReference ->
//        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//    }
//    .addOnFailureListener { e ->
//        Log.w(TAG, "Error adding document", e)
//    }

    // Read data
//
//    db.collection("users")
//    .get()
//    .addOnSuccessListener { result ->
//        for (document in result) {
//            Log.d(TAG, "${document.id} => ${document.data}")
//        }
//    }
//    .addOnFailureListener { exception ->
//        Log.w(TAG, "Error getting documents.", exception)
//    }
//}

