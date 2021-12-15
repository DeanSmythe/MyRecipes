package com.example.myrecipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.fragment_recipe_details.*


class RecipeDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_details, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                val recipe = RecipeDbHandler()
                recipe.newRecipeItem(
                    RecipeName = recipeTitle,
                    RecipeInstructions = recipeDescription,
                    TimeToMake = timeToMake.toInt(),
                    Picture = "#",
                    DietReq = dietRequirement
                )

            }
        }
    }
    private fun setSpinnerAdapter(spinner: Spinner){
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.diet,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter -> arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter}
    }


}