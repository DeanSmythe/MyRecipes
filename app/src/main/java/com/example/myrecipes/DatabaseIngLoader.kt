package com.example.myrecipes

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class DatabaseIngLoader {
    private val db = Firebase.firestore
    var allIngredients: ArrayList<Ingredient> = ArrayList()

    fun loadDefaultIngToDb() {
        val ingredient1 = Ingredient("Milk", "Semi-skimmed", UnitOfMeasure.ML , "#")
        allIngredients.add(ingredient1)
        val ingredient2 = Ingredient("Egg", "Medium", UnitOfMeasure.EACH, "#")
        allIngredients.add(ingredient2)
        val ingredient3 = Ingredient("Flour", "Plain flour", UnitOfMeasure.ML, "#")
        allIngredients.add(ingredient3)
        val ingredient4 = Ingredient("Bread", "Sliced", UnitOfMeasure.EACH, "#")
        allIngredients.add(ingredient4)
        val ingredient5 = Ingredient("Baked Beans", "Tinned", UnitOfMeasure.GRAMS, "#")
        allIngredients.add(ingredient5)
        val ingredient6 = Ingredient("Broccoli", "Fresh", UnitOfMeasure.GRAMS, "#")
        allIngredients.add(ingredient6)
        val ingredient7 = Ingredient("Pasta", "Spaghetti", UnitOfMeasure.GRAMS, "#")
        allIngredients.add(ingredient7)
        val ingredient8 = Ingredient("Tomato", "Fresh", UnitOfMeasure.GRAMS, "#")
        allIngredients.add(ingredient8)
        val ingredient9 = Ingredient("Tinned Tomatoes", "Chopped", UnitOfMeasure.GRAMS, "#")
        allIngredients.add(ingredient9)
        val ingredient10 = Ingredient("Olive Oil", "Fresh", UnitOfMeasure.ML, "#")
        allIngredients.add(ingredient10)
        val ingredient11 = Ingredient("Mushrooms", "Chopped", UnitOfMeasure.GRAMS, "#")
        allIngredients.add(ingredient11)
        val ingredient12 = Ingredient("Onion", "Chopped", UnitOfMeasure.GRAMS, "#")
        allIngredients.add(ingredient12)
        val ingredient13 = Ingredient("Rice", "Loose", UnitOfMeasure.GRAMS, "#")
        allIngredients.add(ingredient13)
        val ingredient14 = Ingredient("Potatoes", "New", UnitOfMeasure.GRAMS, "#")
        allIngredients.add(ingredient14)

        for (ingredient in allIngredients) {
            Log.d(ContentValues.TAG, "Trying  $ingredient")
            val ingredientData = hashMapOf(
                "name" to ingredient.name,
                "description" to ingredient.description,
                "uom" to ingredient.uom,
                "picture" to ingredient.picture
            )
            db.collection("ingredients").add(ingredientData)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        ContentValues.TAG,
                        "DocumentSnapshot added with ID: ${documentReference.id}"
                    )
                }
                .addOnFailureListener { error ->
                    Log.w(ContentValues.TAG, "Error adding document", error)
                }
//                .result.toString()
        }
//        val returnID = localWriteIngredient(ingredient)
    }

//    fun localWriteIngredient(ingredient: Ingredient) : String {
//        val ingredientData = hashMapOf("name" to ingredient.name,"description" to ingredient.description, "uom" to ingredient.uom, "picture" to ingredient.picture)
//        return db.collection("ingredients").add(ingredientData)
//            .addOnSuccessListener { documentReference ->
//                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { error ->
//                Log.w(ContentValues.TAG, "Error adding document", error)
//            }
//            .result.toString()
//    }

    suspend fun emptyIngredients() = coroutineScope {
        launch {
            Log.println(Log.WARN, "", "In function")
            Log.println(Log.WARN, "", "Database $db")
            db.collection("ingredients").get()
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

    fun emptyAndFill() = runBlocking {
        emptyIngredients()
        justWait(500)
        loadDefaultIngToDb()
    }

    private suspend fun justWait(timeMs: Long) = coroutineScope {
        Log.println(Log.WARN, "", "Starting time function")
        delay(timeMs)
        Log.println(Log.WARN, "", "Leaving time function")
    }
}