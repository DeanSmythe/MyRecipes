package com.example.myrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipes.data.Image

class ImagePicker : AppCompatActivity() {
    private var imagesRecyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_picker)
        imagesRecyclerView = findViewById(R.id.imagesRecyclerView)
        val images = mutableListOf<Image>(
            Image("la", "tu"),
            Image("test", "poo")
        )
        imagesRecyclerView?.apply {
            adapter = ImagesAdapter(images)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }
}