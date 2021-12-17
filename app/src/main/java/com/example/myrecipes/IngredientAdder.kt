package com.example.myrecipes

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myrecipes.data.Image
import com.example.myrecipes.utils.FirebaseUtils
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.lang.Exception

class IngredientAdder : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var ingredientDescriptionEditText: EditText
    private lateinit var ingredientDescription:String
    private lateinit var ingredientNameEditText: EditText
    private lateinit var ingredientName: String
    private lateinit var ingredientUom: String
    private lateinit var submitIng: Button
    private lateinit var spinner: Spinner
    private lateinit var imageView: ImageView
    val storage = Firebase.storage
    val storageRef = storage.reference
    private var refChild: StorageReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ing_adder)
        setUpAssets()
        val btnUploadImage = findViewById<Button>(R.id.btnUploadImage)
        btnUploadImage.setOnClickListener { openLocalMedia() }

        submitIng.setOnClickListener {
            readUiAssets()

            if (ingredientName.isNotEmpty() && ingredientDescription.isNotEmpty()) {
                Log.d("Check fields not empty", "Fields not empty")
                val ingredient = Ingredient()
                ingredient.writeNewIngredient(
                    name = ingredientName,
                    description = ingredientDescription,
                    picture = ingredientName,
                    uom = ingredientUom
                )
                uploadImage(Image(ingredientName, ingredientName))
            }

        }
    }

    private fun readUiAssets() {
        ingredientUom = spinner.selectedItem.toString()
        ingredientName = ingredientNameEditText.text.toString()
        ingredientDescription = ingredientDescriptionEditText.text.toString()
    }

    private fun setUpAssets() {
        toolbar = findViewById(R.id.toolbar_adder)
        setSupportActionBar(toolbar)
        ingredientNameEditText = findViewById<EditText>(R.id.etIngredientName)//.text.toString()
        ingredientDescriptionEditText = findViewById<EditText>(R.id.etIngredientDescription)//.text.toString()
        spinner = findViewById(R.id.spUomSpinner)
        submitIng = findViewById(R.id.btnSubmitIngredient)
        setSpinnerAdapter(spinner)
        imageView = findViewById(R.id.imageView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.account_menu, menu)
        val currentUser = menu?.findItem(R.id.itmLoggedInAs)
        currentUser?.setTitle(setUsername())
        val itmAddIngChangeTitle = menu?.findItem(R.id.itmAddIngredient)
        itmAddIngChangeTitle?.setTitle("Recipe Page")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itmLogout -> signOut()
            R.id.itmMyCupboard -> myCupboard()
            R.id.itmCreateRecipe -> redirectAddRecipePage()
            R.id.itmAddIngredient -> redirectRecipePage()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setSpinnerAdapter(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            this,
            R.array.uom,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter
        }
    }

    private fun signOut() {
        try {
            FirebaseUtils.firebaseAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this@IngredientAdder, "Sign out Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun myCupboard() {
        val intent = Intent(this, MyCupboard::class.java)
        startActivity(intent)
    }

    private fun redirectRecipePage() {
        val intent = Intent(this, RecipeHomePage::class.java)
        startActivity(intent)
    }

    private fun redirectAddRecipePage() {
        val intent = Intent(this, AddRecipePage::class.java)
        startActivity(intent)
    }

    private fun setUsername(): String {
        val user = FirebaseUtils.firebaseAuth.currentUser?.email
        if (user != null) {
            Log.d("tag", user)
            user
            return "Signed in as: " + user
        }
        return "Error finding user"
    }

    fun openLocalMedia() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        launchSomeActivity.launch(intent)
    }

    private var launchSomeActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null && result.resultCode == Activity.RESULT_OK) {
                val data = result.data?.data

                Picasso.with(this).load(data).into(imageView)
            }
        }

    fun uploadImage(image: Image) {
        refChild = image.imageName?.let { storageRef.child(it) }
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = refChild?.putBytes(data)
        if (uploadTask != null) {
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // ...
            }.addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding Image", e)
            }
        }
    }

}