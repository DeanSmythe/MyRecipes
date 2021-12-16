package com.example.myrecipes.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipes.CellClickListener
import com.example.myrecipes.IngredientItemAdapter
import com.example.myrecipes.R
import com.example.myrecipes.Recipe
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_item.view.*

class RecipeAdapterSingle(private val recipes: MutableList<Recipe>, private val cellClickListener: CellClickListener) : RecyclerView.Adapter<RecipeAdapterSingle.RecipeViewHolder>() {
    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapterSingle.RecipeViewHolder {
        return RecipeAdapterSingle.RecipeViewHolder (
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return recipes.size

    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindValue(image: Int, txt: String, desc:String) {
//            itemView.findViewById<ImageView>(R.id.imageView).setImageResource(image)
//            itemView.findViewById<TextView>(R.id.txt1).text = txt
//            itemView.findViewById<TextView>(R.id.txt2).text = desc
        }
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        val storage = Firebase.storage
        val storageRef = storage.reference
        holder.itemView.apply {
           val imageHolder = findViewById<ImageView>(R.id.imageView)
            storageRef.child(currentRecipe.picture!!).downloadUrl.addOnSuccessListener {
                Picasso.with(context).load(it).into(imageHolder)
            }.addOnFailureListener {
                Log.i("ImageDownloader:", "unable to get Image")
            }
//            Picasso.with(context).load(currentRecipe.picture).into(imageHolder)
           val txt1 = findViewById<TextView>(R.id.txt1)
            txt1.setText(currentRecipe.name)
           val txt2 = findViewById<TextView>(R.id.txt2)
            txt2.setText(currentRecipe.description)
            holder.itemView.setOnClickListener{
                cellClickListener.onCellClickListener()
            }
        }

    }

    fun addNewRecipe(recipe: Recipe){
        recipes.add(recipe)
        notifyItemInserted(recipes.size - 1)

    }



}