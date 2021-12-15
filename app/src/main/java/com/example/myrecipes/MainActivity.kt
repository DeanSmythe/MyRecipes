package com.example.myrecipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myrecipes.utils.FirebaseUtils.firebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar_login)
        setSupportActionBar(toolbar)

        val registerButton = findViewById<Button>(R.id.btnRegisterView)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val password: String = findViewById<TextView>(R.id.password).text.toString().trim()
        val username: String = findViewById<TextView>(R.id.email).text.toString().trim()
        Log.d("tag", password)
        Log.d("tag", username)
        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, RecipeHomePage::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@MainActivity, "Sign-In Incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }
}