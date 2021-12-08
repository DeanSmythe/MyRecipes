package com.example.myrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myDB : DB_Test = DB_Test()
        myDB.writeToDB()

    }

    fun testRead(view : View) {
        val myDB = DB_Test()
        myDB.readFromDB()

    }




}