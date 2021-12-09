package com.example.myrecipes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.myrecipes.utils.FirebaseUtils.firebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
private const val RC_SIGN_IN = 9001

class MainActivity : AppCompatActivity() {
    private var googleSignInClient: GoogleSignInClient? = null
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


    private fun signIn(){
        val password : String = findViewById<TextView>(R.id.password).text.toString().trim()
        val username : String = findViewById<TextView>(R.id.email).text.toString().trim()
        Log.d("tag", password)
        Log.d("tag", username)
        firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val intent = Intent(this, RecipeHomePage::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this@MainActivity,"Sign-In Incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    private fun signIn() {
//        val signInIntent = googleSignInClient?.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        auth?.signInWithCredential(credential)
//            ?.addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    Log.w("test", "LoginSuccessful", task.exception)
////                    registerUser()
////                    roomsViewModelMapper.initialiseRooms(this, roomsRecyclerView)//,user)
////                    sign_in_button.visibility = View.GONE
//                } else {
//                    Log.w("test", "signInWithCredential:failure", task.exception)
//                }
//            }
//    }
//
//    private fun catchSignInReport(requestCode: Int, data: Intent?) {
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)!!
//                Log.d("test", "firebaseAuthWithGoogle:" + account.id)
//                firebaseAuthWithGoogle(account.idToken!!)
//            } catch (e: ApiException) {
//                Log.w("test", "Google sign in failed", e)
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        catchSignInReport(requestCode, data)
//    }


}