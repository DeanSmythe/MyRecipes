package com.example.myrecipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.fragment_recipe_details.*
import kotlinx.android.synthetic.main.ingredients_method.view.*

class AddRecipeTabsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.ingredients_method, container, false)

        val fragmentName = arguments?.getString("fragmentName")

        rootView.fragment_name.text = fragmentName

        val recipeDetailsFragment = RecipeDetailsFragment()
        val spinner = spDietSpinner
        setSpinnerAdapter(spinner)

        var submitRecipe = btnRecipeSubmit
        submitRecipe.setOnClickListener {
            val recipeTitle = etRecipeName.text.toString()
            val recipeDescription = etRecipeDesc.text.toString()
            val timeToMake = etTimeToMake.text.toString()
            val dietRequirement = spDietSpinner.selectedItem.toString()
            if (recipeTitle.isNotEmpty() && recipeDescription.isNotEmpty() && timeToMake.isNotEmpty()) {
                Log.d("check not empty", "Working")
                val recipe = Recipe()
                recipe.writeNewRecipe(
                    name = recipeTitle,
                    description = recipeDescription,
                    timetomake = timeToMake.toInt(),
                    picture = "#",
                    rating = 0,
                    diet = dietRequirement
                )

            }
        }

        return rootView
    }

    private fun setSpinnerAdapter(spinner: Spinner){
        ArrayAdapter.createFromResource(
            this,
            R.array.diet,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter -> arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter}
    }

}