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

class ImagesAdapter constructor(
    private val localRepository: LocalRepository,
    private val imagePicker: ImagePicker
) : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageImageView: ImageView = itemView.findViewById(R.id.imageImageView)
        val context: Context = itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            imageImageView.setOnClickListener {
                localRepository.apply {
                    saveCurrentImage(images[position])
                }
                imagePicker.finishActivity()
            }
            if (localRepository.images[position].imageUrl != null && localRepository.images[position].imageUrl != "") {
                val storage = Firebase.storage
                val storageRef = storage.reference

                localRepository.images[position].imageUrl?.let { url ->
                    storageRef.child(url).downloadUrl.addOnSuccessListener {
                        Picasso.with(context).load(it).into(imageImageView)
                    }.addOnFailureListener {
                        Log.i("ImageDownloader:", "unable to get Image")
                    }
                }
            }
       }
    }

    override fun getItemCount() = localRepository.images.size

}
