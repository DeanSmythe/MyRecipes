package com.example.myrecipes

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RecipeHomePage : AppCompatActivity() {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val user : FirebaseUser? = auth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_home_page)
        var userTextView = findViewById<TextView>(R.id.tvUsername)
        getUsername(userTextView)


    }

    fun getUsername(userTextView:TextView): String {
        val userName = user!!.displayName.toString()
        Log.d("test", "User Name: $userName")
        userTextView.text.toString().equals("$userName")
        return userName
    }
}