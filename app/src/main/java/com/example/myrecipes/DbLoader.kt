package com.example.myrecipes

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class DbLoader : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.db_loader)
        }

    fun loadIngDb(view : View){
        DatabaseIngLoader().loadDefaultIngToDb()
    }

    fun loadRecipeDB(view : View){
        DatabaseRecipeLoader().loadDefaultRecipeToDb()
    }

    fun loadRecipeIngDB(view : View){
        DatabaseRecipeIngLoader().loadDefaultRecipeIng()
    }
}