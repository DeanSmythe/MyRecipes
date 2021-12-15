package com.example.myrecipes

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrecipes.utils.FirebaseUtils
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_my_cupboard.btnAddIngredient
import kotlinx.android.synthetic.main.activity_my_cupboard.etIngredientQuantity
import kotlinx.android.synthetic.main.activity_my_cupboard.rvListOfIngredientsRecipe
import kotlinx.android.synthetic.main.activity_my_cupboard.spinnerIngredients
import kotlinx.android.synthetic.main.activity_my_cupboard.spinneruom
import kotlinx.android.synthetic.main.fragment_recipe_add_ingredients.*
import kotlinx.android.synthetic.main.fragment_recipe_details.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

class RecipeAddIngredientsFragment : Fragment() {

    private lateinit var ingredientAdapter: IngredientItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_add_ingredients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ingredientAdapter = IngredientItemAdapter(mutableListOf())

        setUpRecyclerAndCards()

        val spinner = spinnerIngredients
        val spinnerUom = spinneruom

        setSpinnerAdapter(spinner, spinnerUom)
    }

    private fun setSpinnerAdapter(spinner: Spinner, spinnerUom: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.ingredients,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.uom,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerUom.adapter = arrayAdapter
        }
    }

    private fun findImage(ingredient: String): String? {
        val map = mapOf(
            "http://clipart-library.com/img/1640487.png" to "Eggs",
            "http://clipart-library.com/img/1942265.png" to "Flour",
            "http://clipart-library.com/images_k/cup-transparent-background/cup-transparent-background-10.jpg" to "Milk"
        )
        return map.entries.find { it.value == ingredient }?.key
    }

    private fun setUpRecyclerAndCards() {
        ingredientAdapter = IngredientItemAdapter(mutableListOf())
        val listOfIngredients = rvListOfIngredientsForRecipe
        listOfIngredients.adapter = ingredientAdapter
        listOfIngredients.layoutManager = LinearLayoutManager(requireContext())
        val addIngredients = btnAddIngredient
        addIngredients.setOnClickListener {

                populateEtWithRecipeNames()

        }
    }

    private fun populateEtWithRecipeNames() {
        val email = FirebaseUtils.firebaseAuth.currentUser?.email
        var db = Firebase.firestore
        val dbGetUser = db.collection("recipe").document(email!!).collection("MyRecipes").limit(1).orderBy("Date",Query.Direction.DESCENDING)
           .get().addOnSuccessListener { documents ->
            for (document in documents) {
                var recipeNames = document.data.get("Recipe").toString()
                Log.d(tag,recipeNames)
                etRecipeNameGet.setText(recipeNames)
                addIngredientToRecipe()
            }
        }
    }

    private fun addIngredientToRecipe(){
        val recipeName = etRecipeNameGet.text.toString()
        val quantity = etIngredientQuantity.text.toString()
        val ingredient = spinnerIngredients.selectedItem.toString()
        val ingredientUom = spinneruom
        if (quantity.isNotEmpty() && recipeName.isNotEmpty()) {
            val ingredient = Ingredient(
                picture = findImage(ingredient = ingredient),
                description = quantity,
                uom = ingredientUom.selectedItem.toString(),
                name = ingredient,
                recipeName = recipeName
            )
            ingredientAdapter.addNewIngredientRecipe(ingredient)
            etIngredientQuantity.setText("")
        }
    }
}


