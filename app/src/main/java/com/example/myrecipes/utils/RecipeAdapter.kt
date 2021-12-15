package com.example.myrecipes.utils

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.myrecipes.CellClickListener
import com.example.myrecipes.R

class RecipeAdapter(val img:Array<Int>,val text:Array<String>,
                    val desc:Array<String>, private val cellClickListener: CellClickListener)
                    :RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(v)
    }

    override fun getItemCount(): Int {
        return text.size

    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindValue(image: Int, txt: String, desc:String) {
            itemView.findViewById<ImageView>(R.id.imageView).setImageResource(image)
            itemView.findViewById<TextView>(R.id.txt1).text = txt
            itemView.findViewById<TextView>(R.id.txt2).text = desc
        }
    }

        override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
            with(holder) { bindValue(img[position],text[position],desc[position])
            holder.itemView.setOnClickListener{
                val recipeDatebaseHandler=RecipeDatebaseHandler()
                recipeDatebaseHandler.getData(text[position])
                cellClickListener.onCellClickListener()
            }
            }
        }



    }


