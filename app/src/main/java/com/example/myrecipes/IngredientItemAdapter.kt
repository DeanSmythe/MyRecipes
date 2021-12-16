package com.example.myrecipes

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso

class IngredientItemAdapter(
    private val ingredients: MutableList<Ingredient>
) : RecyclerView.Adapter<IngredientItemAdapter.IngredientViewHolder>() {
    private lateinit var picture: ImageView
    private lateinit var uom: TextView
    private lateinit var quantity: TextView
    private val storage = Firebase.storage
    private val storageRef = storage.reference

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentIngrendient = ingredients[position]
        holder.itemView.apply {
            val ingredient = findViewById<TextView>(R.id.tvIngredientName)
            ingredient.setText(currentIngrendient.name)
            picture = findViewById(R.id.ivIngredientItem)
            uom = findViewById(R.id.tvIngredientUom)
            quantity = findViewById(R.id.tvIngredientQuanitity)
            uom.text = currentIngrendient.uom
            quantity.text = currentIngrendient.description
            currentIngrendient.name?.let { setImage(context, it) }
        }
    }

    private fun removeDuplicates(comparitor: String) {
        val size = ingredients.size - 1
        val i = 0
        Log.d("remove duplicate =", ingredients.get(i).name.toString())
        for (i in 0..size) {
            if (ingredients.get(i).name.toString() == comparitor) {
                ingredients.removeAt(i)
                notifyItemRemoved(i)
                break
            }
        }
    }

    fun addNewIngredient(ingredient: Ingredient) {
        updateDb(ingredient)
        Log.d("remove duplicates =", ingredient.name.toString())
        if (ingredients.size > 0) {
            removeDuplicates(comparitor = ingredient.name.toString())
        }
        ingredients.add(ingredient)
        notifyItemInserted(ingredients.size - 1)
    }

    fun addNewIngredientRecipe(ingredient: Ingredient) {
        updateDbRecipe(ingredient)
        ingredients.add(ingredient)
        notifyItemInserted(ingredients.size - 1)
    }

    fun populateRecyclerView(ingredient: Ingredient) {
        ingredients.add(ingredient)
        notifyItemInserted(ingredients.size - 1)
    }

    private fun updateDb(ingredient: Ingredient) {
        val emailDataBaseUpdater = UsersEmailDbHandler()
        emailDataBaseUpdater.newCupboardItem(
            ingredient = ingredient.name!!,
            Amount = ingredient.description!!.toInt(),
            Uom = ingredient.uom!!
        )
    }

    private fun updateDbRecipe(ingredient: Ingredient) {
        val recipeAdapter = RecipeDbHandler()
        recipeAdapter.setIngredientsRecipe(
            ingredient = ingredient.name!!,
            Amount = ingredient.description!!.toInt(),
            Uom = ingredient.uom!!,
            RecipeName = ingredient.recipeName!!
        )
    }

    private fun setImage(context: Context, imageName: String) {
        imageName.let { url ->
            storageRef.child(url).downloadUrl.addOnSuccessListener {
                Picasso.with(context).load(it).into(picture)
            }.addOnFailureListener {
                Log.i("ImageDownloader:", "unable to get Image")
            }
        }
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }
}