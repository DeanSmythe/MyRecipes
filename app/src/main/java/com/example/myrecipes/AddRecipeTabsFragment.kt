package com.example.myrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipes.data.LocalRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.ingredients_method.*
import kotlinx.android.synthetic.main.ingredients_method.view.*

class AddRecipeTabsFragment : Fragment() {
    private val db = Firebase.firestore
    private lateinit var photosRecyclerViewHolder: RecyclerView
    private lateinit var localRepository: LocalRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.ingredients_method, container, false)
        localRepository = LocalRepository()
        val recipe = Recipe()
        recipe.loadRecipes()
        localRepository.recipes = recipe.fetchRecipes()
        photosRecyclerViewHolder = photosRecyclerView
        photosRecyclerViewHolder.apply {
            photosRecyclerViewHolder.adapter = PhotosAdapter(localRepository)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        return rootView
    }
}