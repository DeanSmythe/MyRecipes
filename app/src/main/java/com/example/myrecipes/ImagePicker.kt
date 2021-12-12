package com.example.myrecipes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipes.data.LocalRepository

class ImagePicker : AppCompatActivity() {
    private var imagesRecyclerView: RecyclerView? = null
    private lateinit var localRepository: LocalRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_picker)
        imagesRecyclerView = findViewById(R.id.imagesRecyclerView)
        localRepository = intent.getParcelableExtra("localRepository") ?: LocalRepository()
        if (localRepository.images.isNotEmpty()) {
            imagesRecyclerView?.apply {
                adapter = ImagesAdapter(localRepository.images)
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            }
        }
    }
}