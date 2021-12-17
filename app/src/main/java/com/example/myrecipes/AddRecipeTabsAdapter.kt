package com.example.myrecipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class AddRecipeTabsAdapter(fm: FragmentManager, lifecycle: Lifecycle, private var numberOfTabs: Int) :
    FragmentStateAdapter(fm, lifecycle){
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                // Ingredients Fragment
                val bundle = Bundle()
                bundle.putString("RecipeName", "Details")
                val AddRecipeFragment = RecipeDetailsFragment()
                AddRecipeFragment.arguments = bundle
                return AddRecipeFragment
            }
            1 -> {
                // # Recipe Fragment
                val bundle = Bundle()
                bundle.putString("fragmentName", "Ingredients")
                val ingredientsFragment = RecipeAddIngredientsFragment()
                ingredientsFragment.arguments = bundle
                return ingredientsFragment
            }

            else -> return AddRecipeTabsFragment()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }


}