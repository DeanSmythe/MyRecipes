package com.example.myrecipes

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipes.utils.FirebaseUtils
import com.example.myrecipes.utils.FirebaseUtils.firebaseAuth
import com.example.myrecipes.utils.RecipeAdapter
import com.example.myrecipes.utils.RecipeAdapterSingle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recipe_add_ingredients.*
import java.lang.Exception

class RecipeHomePage : AppCompatActivity(), CellClickListener {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var adapter : RecipeAdapterSingle
    private val img = arrayOf(R.drawable.burger,R.drawable.pizza,R.drawable.pancakes,
        R.drawable.frenchtoast,R.drawable.meatballs, R.drawable.tacos,R.drawable.tomato_mozerella)
    private val texts = arrayOf("Charbroiled Steak Burger", "Pizza Milano", "Breakfast Pancakes", "French Toast",
        "Italian Style Meatballs", "Chick Pea Tacos", "Tomato and Mozzarella Salad")
    private val descs = arrayOf("Delicious chuck steak burger", "Adjust toppings to suit your diet", "A Sunday morning treat",
    "Breakfast doesn't get any better", "Serve with a pasta of your choice", "Great vegetarian option", "Healthy option for those on a diet")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_home_page)
        setSupportActionBar(findViewById(R.id.toolbar))
        adapter = RecipeAdapterSingle(mutableListOf(), this)
        setUsername()
        getRecipes()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_recipe)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

    }

//    private fun getRecipes() {
//        var db = Firebase.firestore
//        val dbGetUser = db.collection("recipes").get().addOnSuccessListener { emails ->
//            for (email in emails ){
//                Log.d("All Recipes?",email.id.toString())
//                val users = db.collection("recipes").document(email.id).collection("Ingredients").get().addOnSuccessListener { items ->
//                    for (email in items ){
//                        Log.d("get ingredients?","reaching this point")
//                        Log.d("get ingredients?",email.data.get("Ingredient").toString())
//                    }
//                }
//            }
//        }
//    }

    private fun getRecipes() {
        var db = Firebase.firestore
        val dbGetUser = db.collection("email").get().addOnSuccessListener { emails ->
            for (email in emails ){
                Log.d("All Recipes?",email.id.toString())
                val emailId = email.id
                val users = db.collection("recipe").document(email.id).collection("MyRecipes").get().addOnSuccessListener { items ->
                    for (email in items ){
                        Log.d("get ingredients?","reaching this point")
                        Log.d("get ingredients?",email.data.get("Recipe").toString())
                        val recipe = email.data.get("Recipe").toString()
                        db.collection("recipe").document(emailId).collection("MyRecipes").document(recipe).collection("Ingredients")
                            .get().addOnSuccessListener { recipes ->
                                for (recipe in recipes){
                                    Log.d("get ingredient!?","reaching this point 2")
                                    Log.d("get ingredient!?",recipe.id.toString())
                                    setUpAdapter(email.data.get("Picture").toString(), email.data.get("RecipeInstructions").toString(), email.data.get("Recipe").toString(), email.data.get("TimeToMake").toString())
                                }
                            }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.account_menu,menu)
        var currentUser = menu?.findItem(R.id.itmLoggedInAs)
        currentUser?.setTitle(setUsername())
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.itmLogout -> signOut()
            R.id.itmMyCupboard -> myCupboard()
            R.id.itmCreateRecipe -> redirectRecipePage()
            R.id.itmAddIngredient -> redirectIngAddPage()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpAdapter(img: String, texts: String, desc: String, timeToMake: String){
        val recipe = Recipe(picture = img,name = desc, timetomake = timeToMake.toInt(), description = texts)
        adapter.addNewRecipe(recipe)
            }



    private fun setUsername(): String {
        val user = firebaseAuth.currentUser?.email
        if (user != null) {
            Log.d("tag", user)
            val username = user
            return "Signed in as: "+user
        }
        return "Error finding user"
    }

    private fun signOut(){
        try {
            firebaseAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } catch(e: Exception){
            Toast.makeText(this@RecipeHomePage,"Sign out Error", Toast.LENGTH_SHORT).show()
        }

    }

    private fun myCupboard(){
        val intent = Intent(this, MyCupboard::class.java)
        startActivity(intent)
    }

    private fun redirectRecipePage(){
        val intent = Intent(this, AddRecipePage::class.java)
        startActivity(intent)
    }


    override fun onCellClickListener() {
        val intent = Intent(this, IngredientMethodActivity::class.java)
        startActivity(intent)
    }

    private fun redirectIngAddPage(){
        val intent = Intent(this, IngredientAdder::class.java)
        startActivity(intent)
    }
}