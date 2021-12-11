package com.example.myrecipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipes.data.Image

class ImagesAdapter constructor(val images: MutableList<Image>) : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageNameTextView = itemView.findViewById<TextView>(R.id.imageNameTextView)
        val imageUrlTextView = itemView.findViewById<TextView>(R.id.imageUrlTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            imageNameTextView.text = images[position].imageName
            imageUrlTextView.text = images[position].imageUrl
        }
    }

    override fun getItemCount() = images.size

}
