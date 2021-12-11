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
            Image("la", "https://live.staticflickr.com/3241/3083459599_55d24a48f8.jpg"),
            Image("test", "https://thumbs.dreamstime.com/b/flour-glass-bowl-wheat-isolated-white-36638830.jpg" )
        )
        imagesRecyclerView?.apply {
            adapter = ImagesAdapter(images)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }
}