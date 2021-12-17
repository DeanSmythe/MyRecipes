package com.example.myrecipes.data

import android.os.Parcelable
import com.example.myrecipes.Ingredient
import com.example.myrecipes.Recipe
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocalRepository constructor(
    var images: MutableList<Image> = mutableListOf(),
    val user: String = "",
    var currentImage: Image? = null,
    val recipes: MutableList<Recipe> = mutableListOf(),
    var currentRecipe: Recipe? = null,
    val ingredients: MutableList<Ingredient> = mutableListOf(),
    var currentIngredient: Ingredient? = null
) : Parcelable {

    fun addImage(image: Image) {
        images.add(image)
    }

    fun saveCurrentImage(image: Image) {
        currentImage = image
    }

    fun addRecipe(recipe: Recipe) {
        recipes.add(recipe)
    }

    fun saveCurrentRecipe(recipe: Recipe) {
        currentRecipe = recipe
    }


    fun addIngredient(ingredient: Ingredient) {
        ingredients.add(ingredient)
    }

    fun saveCurrentIngredient(ingredient: Ingredient) {
        currentIngredient = ingredient
    }
}
