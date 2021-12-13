package com.example.myrecipes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipes.utils.FirebaseUtils
import java.lang.Exception


class MyCupboard : AppCompatActivity() {
    private lateinit var ingredientAdapter: IngredientItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cupboard)
        setSupportActionBar(findViewById(R.id.toolbar_login))
        ingredientAdapter = IngredientItemAdapter(mutableListOf())

        setUpRecyclerAndCards()

        val spinner = findViewById<Spinner>(R.id.spinnerIngredients)
        val spinnerUom = findViewById<Spinner>(R.id.spinneruom)

        setSpinnerAdapter(spinner,spinnerUom)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.account_menu,menu)
        var currentUser = menu?.findItem(R.id.itmLoggedInAs)
        currentUser?.setTitle(setUsername())
        var recipePage = menu?.findItem(R.id.itmMyCupboard)
        recipePage?.setTitle("Recipe page")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.itmLogout -> signOut()
            R.id.itmMyCupboard -> recipePage()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setSpinnerAdapter(spinner: Spinner, spinnerUom: Spinner){
        ArrayAdapter.createFromResource(
            this,
            R.array.ingredients,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter -> arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter}

        ArrayAdapter.createFromResource(
            this,
            R.array.uom,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter -> arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerUom.adapter = arrayAdapter}
    }

    private fun findImage(ingredient: Spinner) : String?{
        val map = mapOf("http://clipart-library.com/img/1640487.png" to "Eggs",
            "http://clipart-library.com/img/1942265.png" to "Flour",
            "http://clipart-library.com/images_k/cup-transparent-background/cup-transparent-background-10.jpg" to "Milk"
        )
        return map.entries.find { it.value == ingredient.selectedItem.toString()}?.key
    }

    private fun setUpRecyclerAndCards(){
        val listOfIngredients = findViewById<RecyclerView>(R.id.rvListOfIngredients)
        listOfIngredients.adapter = ingredientAdapter
        listOfIngredients.layoutManager = LinearLayoutManager(this)

        val addIngredients = findViewById<Button>(R.id.btnAddIngredient)
        addIngredients.setOnClickListener {
            val quantity = findViewById<EditText>(R.id.etIngredientQuantity).text.toString()
            val ingredient = findViewById<Spinner>(R.id.spinnerIngredients)
            val ingredientUom = findViewById<Spinner>(R.id.spinneruom)
            if (quantity.isNotEmpty()) {
                val ingredient = Ingredient(picture = findImage(ingredient= ingredient) ,
                    description = quantity,uom = ingredientUom.selectedItem.toString(), name = ingredient.selectedItem.toString())
                ingredientAdapter.addIngredient(ingredient)
                findViewById<EditText>(R.id.etIngredientQuantity).setText("")
            }
        }
    }

    private fun setUsername(): String {
        val user = FirebaseUtils.firebaseAuth.currentUser?.email
        if (user != null) {
            Log.d("tag", user)
            val username = user
            return "Signed in as: "+user
        }
        return "Error finding user"
    }

    private fun signOut(){
        try {
            FirebaseUtils.firebaseAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } catch(e: Exception){
            Toast.makeText(this@MyCupboard,"Sign out Error", Toast.LENGTH_SHORT).show()
        }

    }

    private fun recipePage(){
        val intent = Intent(this, RecipeHomePage::class.java)
        startActivity(intent)
    }
}