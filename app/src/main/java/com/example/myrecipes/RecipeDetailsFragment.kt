package com.example.myrecipes

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myrecipes.data.Image
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_recipe_details.*
import java.io.ByteArrayOutputStream


class RecipeDetailsFragment : Fragment() {
    private lateinit var ivRecipeImageHolder: ImageView
    val storage = Firebase.storage
    val storageRef = storage.reference
    private var refChild: StorageReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipeDetailsFragment = RecipeDetailsFragment()
        ivRecipeImageHolder = ivRecipeImage
        val spinner = spDietSpinner
        setSpinnerAdapter(spinner)

        var submitRecipe = btnRecipeSubmit
        submitRecipe.setOnClickListener {
            val recipeTitle = etRecipeName.text.toString()
            val recipeDescription = etRecipeDesc.text.toString()
            val timeToMake = etTimeToMake.text.toString()
            val dietRequirement = spDietSpinner.selectedItem.toString()
            if (recipeTitle.isNotEmpty() && recipeDescription.isNotEmpty() && timeToMake.isNotEmpty()) {
                val recipe = RecipeDbHandler()
                recipe.newRecipeItem(
                    RecipeName = recipeTitle,
                    RecipeInstructions = recipeDescription,
                    TimeToMake = timeToMake.toInt(),
                    Picture = recipeTitle,
                    DietReq = dietRequirement
                )

            }
            uploadImage(Image(recipeTitle, recipeTitle))
        }
        val uploadImage = btnUploadImage
        uploadImage.setOnClickListener {
            openLocalMedia()
        }
    }

    private fun setSpinnerAdapter(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.diet,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter
        }
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

                Picasso.with(requireContext()).load(data).into(ivRecipeImageHolder)
            }
        }

    fun uploadImage(image: Image) {
        refChild = image.imageName?.let { storageRef.child(it) }
        ivRecipeImageHolder.isDrawingCacheEnabled = true
        ivRecipeImageHolder.buildDrawingCache()
        val bitmap = (ivRecipeImageHolder.drawable as BitmapDrawable).bitmap
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
