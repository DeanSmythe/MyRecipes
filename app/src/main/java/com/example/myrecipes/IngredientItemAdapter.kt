package com.example.myrecipes

import android.content.ContentValues.TAG
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipes.utils.FirebaseUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class IngredientItemAdapter (
    private val ingredients: MutableList<Ingredient>) : RecyclerView.Adapter<IngredientItemAdapter.IngredientViewHolder>(){

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false))
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentIngrendient = ingredients[position]
        holder.itemView.apply {
            val ingredient = findViewById<TextView>(R.id.tvIngredientName)
            ingredient.setText(currentIngrendient.name)
            val picture = findViewById<ImageView>(R.id.ivIngredientItem)
            Picasso.with(context).load(currentIngrendient.picture).into(picture)
            val uom = findViewById<TextView>(R.id.tvIngredientUom)
            uom.setText(currentIngrendient.uom)
            val quantity = findViewById<TextView>(R.id.tvIngredientQuanitity)
            quantity.setText(currentIngrendient.description)

        }
    }

    fun removeDuplicates(comparitor: String){
        var size = ingredients.size - 1
        var i = 0
        Log.d("remove duplicate =",ingredients.get(i).name.toString())
        for (i in 0..size){
            if (ingredients.get(i).name.toString() == comparitor){
                ingredients.removeAt(i)
                notifyItemRemoved(i)
                break
            }
        }
    }

    fun addNewIngredient(ingredient: Ingredient){
        updateDb(ingredient)
        Log.d("remove duplicates =",ingredient.name.toString())
        removeDuplicates(comparitor = ingredient.name.toString())
        ingredients.add(ingredient)
        notifyItemInserted(ingredients.size - 1)
    }

    fun addNewIngredientRecipe(ingredient: Ingredient){
        updateDbRecipe(ingredient)
        ingredients.add(ingredient)
        notifyItemInserted(ingredients.size - 1)
    }

    fun populateRecyclerView(ingredient: Ingredient){
        ingredients.add(ingredient)
        notifyItemInserted(ingredients.size - 1)
    }

    fun updateDb(ingredient: Ingredient){
        var emailDataBaseUpdater = UsersEmailDbHandler()
        emailDataBaseUpdater.newCupboardItem(ingredient = ingredient.name!!, Amount = ingredient.description!!.toInt(), Uom = ingredient.uom!!)
    }

    fun updateDbRecipe(ingredient: Ingredient){
        var recipeAdapter = RecipeDbHandler()
        recipeAdapter.setIngredientsRecipe(ingredient = ingredient.name!!, Amount = ingredient.description!!.toInt(), Uom = ingredient.uom!!, RecipeName = ingredient.recipeName!!)
//        updaterRecipesDb(ingredient)
    }

//    fun updaterRecipesDb(ingredient: Ingredient){
//        var recipeAdapter = RecipeDbHandler()
//        recipeAdapter.setRecipe(ingredient = ingredient.name!!, Amount = ingredient.description!!.toInt(), Uom = ingredient.uom!!, RecipeName = ingredient.recipeName!!)
//
//    }
    override fun getItemCount(): Int {
        return ingredients.size
    }
}