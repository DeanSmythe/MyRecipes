package com.example.myrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyCupboard : AppCompatActivity() {
    private lateinit var ingredientAdapter: IngredientItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cupboard)
        ingredientAdapter = IngredientItemAdapter(mutableListOf())

        val listOfIngredients = findViewById<RecyclerView>(R.id.rvListOfIngredients)
        listOfIngredients.adapter = ingredientAdapter
        listOfIngredients.layoutManager = LinearLayoutManager(this)

        val addIngredients = findViewById<Button>(R.id.btnAddIngredient)
        addIngredients.setOnClickListener {
            val quantity = findViewById<EditText>(R.id.etIngredientQuantity).text.toString()
            if (quantity.isNotEmpty()) {
                val ingredient = Ingredient(quantity)
                ingredientAdapter.addIngredient(ingredient)
                findViewById<EditText>(R.id.etIngredientQuantity).setText("")
            }
        }
        val Spinner = findViewById<Spinner>(R.id.spinnerIngredients)

        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.ingredients))
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)



    }
}