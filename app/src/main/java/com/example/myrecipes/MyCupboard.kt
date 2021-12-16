package com.example.myrecipes

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipes.utils.FirebaseUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class MyCupboard : AppCompatActivity() {
    private lateinit var ingredientAdapter: IngredientItemAdapter
    private var recipeList = mutableListOf<String>()
    private var uomList = mutableListOf<String>()
    private lateinit var spinner: Spinner
    private lateinit var spinnerUom: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cupboard)
        setSupportActionBar(findViewById(R.id.toolbar_login))
        ingredientAdapter = IngredientItemAdapter(mutableListOf())
        spinnerUom = findViewById(R.id.spinneruom)
        spinner = findViewById<Spinner>(R.id.spinnerIngredients)
        setUpRecyclerAndCards()
        populateIngredients()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.account_menu, menu)
        var currentUser = menu?.findItem(R.id.itmLoggedInAs)
        currentUser?.setTitle(setUsername())
        var recipePage = menu?.findItem(R.id.itmMyCupboard)
        recipePage?.setTitle("Recipe page")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itmLogout -> signOut()
            R.id.itmMyCupboard -> recipePage()
            R.id.itmCreateRecipe -> redirectAddRecipePage()
            R.id.itmAddIngredient -> redirectIngAddPage()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setSpinnerAdapter() {
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, recipeList)
        spinner.adapter = adapter
        populateUom()
    }

    private fun setUomAdapter() {
        val uomAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, uomList)
        spinnerUom.adapter = uomAdapter
    }

    private fun populateIngredients() {
        var db = Firebase.firestore
        db.collection("ingredients").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    recipeList.add(document.data["name"].toString())
                }
                setSpinnerAdapter()
            }
            .addOnFailureListener { error ->
                Log.w(ContentValues.TAG, "Error reading document", error)
            }
    }

    private fun populateUom() {
        var db = Firebase.firestore
        db.collection("uom").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    uomList.add(document.data["uom"].toString())
                }
                setUomAdapter()
            }
            .addOnFailureListener { error ->
                Log.w(ContentValues.TAG, "Error reading document", error)
            }
    }

    private fun findImage(ingredient: String): String? {
        val map = mapOf(
            "http://clipart-library.com/img/1640487.png" to "Eggs",
            "http://clipart-library.com/img/1942265.png" to "Flour",
            "http://clipart-library.com/images_k/cup-transparent-background/cup-transparent-background-10.jpg" to "Milk"
        )
        return map.entries.find { it.value == ingredient }?.key
    }

    private fun setUpRecyclerAndCards() {
        val listOfIngredients = findViewById<RecyclerView>(R.id.rvListOfIngredientsRecipe)
        listOfIngredients.adapter = ingredientAdapter
        listOfIngredients.layoutManager = LinearLayoutManager(this)

        setUpDbAndPopulateCupboard(listOfIngredients)

        val addIngredients = findViewById<Button>(R.id.btnAddIngredient)
        addIngredients.setOnClickListener {
            val quantity = findViewById<EditText>(R.id.etIngredientQuantity).text.toString()
            val ingredient = findViewById<Spinner>(R.id.spinnerIngredients).selectedItem.toString()
            val ingredientUom = findViewById<Spinner>(R.id.spinneruom)
            if (quantity.isNotEmpty()) {
                val ingredient = Ingredient(
                    picture = findImage(ingredient = ingredient),
                    description = quantity,
                    uom = ingredientUom.selectedItem.toString(),
                    name = ingredient
                )
                ingredientAdapter.addNewIngredient(ingredient)
                findViewById<EditText>(R.id.etIngredientQuantity).setText("")
            }
        }
    }

    private fun setUpDbAndPopulateCupboard(listOfIngredients: RecyclerView) {
        val email = FirebaseUtils.firebaseAuth.currentUser?.email
        var db = Firebase.firestore
        val dbGetUser = db.collection("email").document(email!!).collection("MyCupboard").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, document.data.get("Uom").toString())
                    var ingredient = Ingredient(
                        name = document.id,
                        description = document.data.get("Amount")
                            .toString(),
                        uom = document.data.get("Uom").toString(),
                        picture = findImage(ingredient = document.id)
                    )
                    ingredientAdapter.populateRecyclerView(ingredient)
                }
            }
    }

    private fun setUsername(): String {
        val user = FirebaseUtils.firebaseAuth.currentUser?.email
        if (user != null) {
            Log.d("tag", user)
            val username = user
            return "Signed in as: " + user
        }
        return "Error finding user"
    }

    private fun signOut() {
        try {
            FirebaseUtils.firebaseAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this@MyCupboard, "Sign out Error", Toast.LENGTH_SHORT).show()
        }

    }

    private fun recipePage() {
        val intent = Intent(this, RecipeHomePage::class.java)
        startActivity(intent)
    }

    private fun redirectAddRecipePage() {
        val intent = Intent(this, AddRecipePage::class.java)
        startActivity(intent)
    }

    private fun redirectIngAddPage() {
        val intent = Intent(this, IngredientAdder::class.java)
        startActivity(intent)
    }
}