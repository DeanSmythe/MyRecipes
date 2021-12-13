package com.example.myrecipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myrecipes.utils.FirebaseUtils.firebaseAuth
import com.google.firebase.auth.FirebaseAuth

class RecipeHomePage : AppCompatActivity() {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_home_page)
        setUsername()

        val myCupboard = findViewById<Button>(R.id.btnMyCupboard)

        myCupboard.setOnClickListener {
            val intent = Intent(this, ImageUploader::class.java)
            startActivity(intent)

        }


    }

    private fun setUsername(){
        val user = firebaseAuth.currentUser?.email
        if (user != null) {
            Log.d("tag", user)
            val currentUser = findViewById<TextView>(R.id.tvCurrentUser)
            currentUser.text = user
        }
    }
}