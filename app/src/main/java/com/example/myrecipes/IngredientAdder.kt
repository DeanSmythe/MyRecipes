package com.example.myrecipes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class IngredientAdder : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ing_adder)

        toolbar = findViewById(R.id.toolbar_login)
        setSupportActionBar(toolbar)
    }
}