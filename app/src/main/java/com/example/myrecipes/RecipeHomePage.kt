package com.example.myrecipes

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myrecipes.utils.FirebaseUtils.firebaseAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RecipeHomePage : AppCompatActivity() {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_home_page)
        setUsername()

    }

    private fun setUsername(){
        val user = firebaseAuth.currentUser?.email
        if (user != null) {
            Log.d("tag", user)
            val username = user
            var currentUser = findViewById<TextView>(R.id.tvCurrentUser)
            currentUser.text = user
        }
    }
}