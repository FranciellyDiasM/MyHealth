package br.com.quatrodcum.myhealth.model.dao

import android.content.Context
import br.com.quatrodcum.myhealth.model.domain.Database

class DatabaseDao(context: Context) {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun getDatabase() : Database {
        return Database(
            users = emptyList(),
            ingredients = emptyList(),
            objectives = emptyList(),
            meals = emptyList(),
            unitOfMeasurements = emptyList(),
            ingredientMeals = emptyList()
        )
    }
}