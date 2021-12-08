package com.example.myrecipes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class RegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        val btnShowHide = findViewById<Button>(R.id.btnShowHide)
        val btnRegisterUser = findViewById<Button>(R.id.btnRegisterUser)
        var etRegisterPassword = findViewById<EditText>(R.id.etRegisterPassword)
        var etRegisterUsername = findViewById<EditText>(R.id.etRegisterUsername)

        btnShowHide.setOnClickListener {
            if(btnShowHide.text.toString().equals("Show")) {
                etRegisterPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                btnShowHide.text = "Hide"
            }else{btnShowHide.text.toString().equals("Hide")
                etRegisterPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                btnShowHide.text = "Show"
            }
        }
        btnRegisterUser.setOnClickListener{
            Log.d("check", "working")
            Log.d("checkInput", etRegisterUsername.text.toString())
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

                            Toast.makeText(
                                this@RegisterPage,
                                "Registration successful.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@RegisterPage, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user_id", firebaseUser)
                            intent.putExtra("email_id", email)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@RegisterPage,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }


}