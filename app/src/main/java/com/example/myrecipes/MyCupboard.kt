package com.example.myrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class MyCupboard : AppCompatActivity() {
    private lateinit var ingredientAdapter: IngredientItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cupboard)
        ingredientAdapter = IngredientItemAdapter(mutableListOf())

        setUpRecyclerAndCards()

        val spinner = findViewById<Spinner>(R.id.spinnerIngredients)
        val spinnerUom = findViewById<Spinner>(R.id.spinneruom)

        setSpinnerAdapter(spinner,spinnerUom)

    }

    private fun setSpinnerAdapter(spinner: Spinner, spinnerUom: Spinner){
        ArrayAdapter.createFromResource(
            this,
            R.array.ingredients,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter -> arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter}

        ArrayAdapter.createFromResource(
            this,
            R.array.uom,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter -> arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerUom.adapter = arrayAdapter}
    }

    private fun findImage(ingredient: Spinner) : String?{
        val map = mapOf("http://clipart-library.com/img/1640487.png" to "Eggs",
            "https://mpng.subpng.com/20180212/wyq/kisspng-wheat-flour-bread-flour-powder-bowl-of-white-flour-5a81d582143ae9.8859969215184582420829.jpg" to "Flour",
            "https://www.citypng.com/public/uploads/preview/hd-real-milk-glass-bottle-png-31625339912cg70iul2hg.png" to "Milk")
        return map.entries.find { it.value == ingredient.selectedItem.toString()}?.key
    }

    private fun setUpRecyclerAndCards(){
        val listOfIngredients = findViewById<RecyclerView>(R.id.rvListOfIngredients)
        listOfIngredients.adapter = ingredientAdapter
        listOfIngredients.layoutManager = LinearLayoutManager(this)

        val addIngredients = findViewById<Button>(R.id.btnAddIngredient)
        addIngredients.setOnClickListener {
            val quantity = findViewById<EditText>(R.id.etIngredientQuantity).text.toString()
            val ingredient = findViewById<Spinner>(R.id.spinnerIngredients)
            val ingredientUom = findViewById<Spinner>(R.id.spinneruom)
            if (quantity.isNotEmpty()) {
                val ingredient = Ingredient(picture = findImage(ingredient= ingredient) ,
                    description = quantity,uom = ingredientUom.selectedItem.toString(), name = ingredient.selectedItem.toString())
                ingredientAdapter.addIngredient(ingredient)
                findViewById<EditText>(R.id.etIngredientQuantity).setText("")
            }
        }
    }
}