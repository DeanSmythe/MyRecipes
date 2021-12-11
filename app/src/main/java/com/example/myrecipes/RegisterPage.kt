package com.example.myrecipes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        val btnShowHide = findViewById<Button>(R.id.btnShowHide)
        val btnRegisterUser = findViewById<Button>(R.id.btnRegisterUser)
        val etRegisterPassword = findViewById<EditText>(R.id.etRegisterPassword)
        val etRegisterUsername = findViewById<EditText>(R.id.etRegisterUsername)

        btnShowHide.setOnClickListener {
            showOrHideButton(etRegisterPassword, btnShowHide)
        }

        btnRegisterUser.setOnClickListener{
            registerUser(etRegisterUsername,etRegisterPassword)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showOrHideButton(etRegisterPassword: EditText, btnShowHide: Button ){
        if(btnShowHide.text.toString().equals("Show")) {
            etRegisterPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            btnShowHide.text = "Hide"
        }else{btnShowHide.text.toString().equals("Hide")
            etRegisterPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            btnShowHide.text = "Show"
        }
    }

    private fun registerUser(etRegisterUsername: EditText, etRegisterPassword: EditText){
        when {
            etRegisterUsername.text.toString().isEmpty() -> {
                Toast.makeText(this@RegisterPage, "Please enter your email", Toast.LENGTH_SHORT).show()
            }
            etRegisterPassword.text.toString().isEmpty() -> {
                Toast.makeText(this@RegisterPage, "Please enter your password", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val email: String = etRegisterUsername.text.toString()
                val password: String = etRegisterPassword.text.toString()
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        registrationSuccessToast()
                        redirectRecipePage(email, firebaseUser)
                    } else {
                        registrationFailToast(task)
                    }
                }
            }
        }
    }

    private fun redirectRecipePage(email: String, firebaseUser: FirebaseUser){
        val intent = Intent(this@RegisterPage, RecipeHomePage::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("user_id", firebaseUser)
        intent.putExtra("email_id", email)
        startActivity(intent)
    }

    private fun registrationSuccessToast() {
        Toast.makeText(
            this@RegisterPage,
            "Registration successful.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun registrationFailToast(task: Task<AuthResult>){
         Toast.makeText(
             this@RegisterPage,
             task.exception!!.message.toString(),
             Toast.LENGTH_SHORT
         ).show()
    }
}