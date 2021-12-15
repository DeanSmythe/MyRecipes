package com.example.myrecipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myrecipes.utils.FirebaseUtils
import java.lang.Exception

class IngredientAdder : AppCompatActivity() {
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ing_adder)
        toolbar = findViewById(R.id.toolbar_login)
        setSupportActionBar(toolbar)

        val spinner = findViewById<Spinner>(R.id.spUomSpinner)
        setSpinnerAdapter(spinner)

        var submitIng = findViewById<Button>(R.id.btnAddIngredient)
        submitIng.setOnClickListener {
            val ingredientName = findViewById<EditText>(R.id.etIngredientName).text.toString()
            val ingredientDescription =
                findViewById<EditText>(R.id.etIngredientDescription).text.toString()
            val ingredientUom = findViewById<Spinner>(R.id.spUomSpinner).selectedItem.toString()
            if (ingredientName.isNotEmpty() && ingredientDescription.isNotEmpty()) {
                Log.d("Check fields not empty", "Fields not empty")
                val ingredient = Ingredient()
                ingredient.writeNewIngredient( name = ingredientName, description = ingredientDescription, picture = "#", uom = ingredientUom )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.account_menu,menu)
        var currentUser = menu?.findItem(R.id.itmLoggedInAs)
        currentUser?.setTitle(setUsername())
        var itmAddIngChangeTitle = menu?.findItem(R.id.itmAddIngredient)
        itmAddIngChangeTitle?.setTitle("Recipe Page")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.itmLogout -> signOut()
            R.id.itmMyCupboard -> myCupboard()
            R.id.itmCreateRecipe -> redirectRecipePage()
            R.id.itmAddIngredient -> redirectAddRecipePage()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setSpinnerAdapter(spinner: Spinner){
        ArrayAdapter.createFromResource(
            this,
            R.array.uom,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter -> arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter}
    }

    private fun signOut(){
        try {
            FirebaseUtils.firebaseAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } catch(e: Exception){
            Toast.makeText(this@IngredientAdder,"Sign out Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun myCupboard(){
        val intent = Intent(this, MyCupboard::class.java)
        startActivity(intent)
    }

    private fun redirectRecipePage(){
        val intent = Intent(this, RecipeHomePage::class.java)
        startActivity(intent)
    }

    private fun redirectAddRecipePage(){
        val intent = Intent(this, AddRecipePage::class.java)
        startActivity(intent)
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

}