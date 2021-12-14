package com.example.myrecipes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.myrecipes.utils.FirebaseUtils
import java.lang.Exception

class AddRecipePage : AppCompatActivity() {
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe_page)
        toolbar = findViewById(R.id.toolbar_AddRecipe)
        setSupportActionBar(toolbar)
        var submitRecipe = findViewById<Button>(R.id.btnRecipeSubmit)
        submitRecipe.setOnClickListener {
            val recipeTitle = findViewById<EditText>(R.id.etRecipeName).text.toString()
            val recipeDescription = findViewById<EditText>(R.id.etRecipeDesc).text.toString()
            val timeToMake = findViewById<EditText>(R.id.etTimeToMake).text.toString()
            if (recipeTitle.isNotEmpty() && recipeDescription.isNotEmpty() && timeToMake.isNotEmpty()){
                Log.d("check not empty","Working")

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.account_menu,menu)
        var currentUser = menu?.findItem(R.id.itmLoggedInAs)
        currentUser?.setTitle(setUsername())
        var itmCreateRecipeChangeTitle = menu?.findItem(R.id.itmCreateRecipe)
        itmCreateRecipeChangeTitle?.setTitle("Recipe Page")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.itmLogout -> signOut()
            R.id.itmMyCupboard -> myCupboard()
            R.id.itmCreateRecipe -> redirectRecipePage()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun signOut(){
        try {
            FirebaseUtils.firebaseAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } catch(e: Exception){
            Toast.makeText(this@AddRecipePage,"Sign out Error", Toast.LENGTH_SHORT).show()
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