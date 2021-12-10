package com.example.myrecipes

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class IngredientUnitTest {

    private val testName: String = "flour"
    private val testDescription: String = "plainflour"
    private val testUom: String = "grams"
    private val testPicture: String = ""

    @Test
    fun createsIngredientTest() {
        val ingredient = Ingredient(testName, testDescription, testUom, testPicture)
        assertEquals(testName, ingredient.name)
        assertEquals(testDescription, ingredient.description)
        assertEquals(testUom, ingredient.uom)
        assertEquals(testPicture, ingredient.picture)
    }

    @Test
    fun readsIngredientTest() = runBlocking {
        val returnIngredient: Ingredient? = suspendGetIngredient(testName)
        assertNotEquals(null, returnIngredient)
        if (returnIngredient != null) {
            assertEquals(testName, returnIngredient.name)
        }
        if (returnIngredient != null) {
            assertEquals(testDescription, returnIngredient.description)
        }
        if (returnIngredient != null) {
            assertEquals(testUom, returnIngredient.uom)
        }
        if (returnIngredient != null) {
            assertEquals(testPicture, returnIngredient.picture)
        }
    }

    private suspend fun suspendGetIngredient(testName: String): Ingredient? = coroutineScope {
        async { Ingredient().getIngredientByName(testName) }.await()
    }
}