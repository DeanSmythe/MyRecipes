package com.example.myrecipes

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipes.data.LocalRepository
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso

class PhotosAdapter constructor(
    private val localRepository: LocalRepository,
) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
    val storage = Firebase.storage
    val storageRef = storage.reference

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoImageView: ImageView = itemView.findViewById(R.id.photoImageView)
        val context: Context = itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            photoImageView.setOnClickListener {
                localRepository.apply {
                    saveCurrentRecipe(recipes[position])
                }
            }
            if (localRepository.recipes[position].picture != null && localRepository.recipes[position].name != "") {

                localRepository.recipes[position].picture?.let { url ->
                    storageRef.child(url).downloadUrl.addOnSuccessListener {
                        Picasso.with(context).load(it).into(photoImageView)
                    }.addOnFailureListener {
                        Log.i("ImageDownloader:", "unable to get Image")
                    }
                }
            }
        }
    }

    override fun getItemCount() = localRepository.recipes.size

}
