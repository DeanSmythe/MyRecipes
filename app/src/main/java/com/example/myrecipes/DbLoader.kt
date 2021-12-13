package com.example.myrecipes

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class DbLoader : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.db_loader)

        }

    fun loadDB(view : View){
        DatabaseIngLoader().loadDefaultIngToDb()
    }
}