package com.example.myrecipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class IngredientsMethodTabs(fm: FragmentManager, lifecycle: Lifecycle, private var numberOfTabs: Int) :
    FragmentStateAdapter(fm, lifecycle){
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                // Ingredients Fragment
                val bundle = Bundle()
                bundle.putString("fragmentName", "Ingredients Fragment")
                val ingredientsFragment = IngredientsFragmentRecipeCard()
                ingredientsFragment.arguments = bundle
                return ingredientsFragment
            }
            1 -> {
                // # Recipe Fragment
                val bundle = Bundle()
                bundle.putString("fragmentName", "Recipes Fragment")
                val recipesFragment = IngredientsFragmentRecipeCard()
                recipesFragment.arguments = bundle
                return recipesFragment
            }
            2 -> {
                // # Notes
                val bundle = Bundle()
                bundle.putString("fragmentName", "Notes Fragment")
                val notesFragment = IngredientMethodFragment()
                notesFragment.arguments = bundle
                return notesFragment
            }
            else -> return IngredientMethodFragment()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }

}