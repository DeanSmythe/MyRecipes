package com.example.myrecipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipes.data.Image

class ImagesAdapter constructor(val images: MutableList<Image>) : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val name=findViewById<TextView>(R.id.imageNameTextView)
            name.text = images[position].imageName
            val url= findViewById<TextView>(R.id.imageUrlTextView)
            url.text = images[position].imageUrl
        }
            }

    override fun getItemCount() = images.size

}
