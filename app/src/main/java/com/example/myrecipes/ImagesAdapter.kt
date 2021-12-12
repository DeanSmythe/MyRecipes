package com.example.myrecipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipes.data.Image
import com.squareup.picasso.Picasso

class ImagesAdapter constructor(val images: MutableList<Image>) : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageImageView=itemView.findViewById<ImageView>(R.id.imageImageView)
        val context=itemView.context
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            Picasso.with(context).load(images[position].imageUrl).into(imageImageView)
        }
    }

    override fun getItemCount() = images.size

}
