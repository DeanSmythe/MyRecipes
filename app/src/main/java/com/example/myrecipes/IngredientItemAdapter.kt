package com.example.myrecipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class IngredientItemAdapter (
    private val ingredients: MutableList<Ingredient>) : RecyclerView.Adapter<IngredientItemAdapter.IngredientViewHolder>(){

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false))
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentIngredient = ingredients[position]
        holder.itemView.apply {
            val ingredient = findViewById<TextView>(R.id.tvIngredientName)
            ingredient.setText(currentIngredient.name)
            val picture = findViewById<ImageView>(R.id.ivIngredientItem)
            Picasso.with(context).load(currentIngredient.picture).into(picture)
            val uom = findViewById<TextView>(R.id.tvIngredientUom)
            uom.text = currentIngredient.uom
            val quantity = findViewById<TextView>(R.id.tvIngredientQuanitity)
            quantity.setText(currentIngredient.description)

        }
    }

    fun addIngredient(ingredient: Ingredient){
        ingredients.add(ingredient)
        notifyItemInserted(ingredients.size - 1)
    }


    override fun getItemCount(): Int {
        return ingredients.size
    }
}