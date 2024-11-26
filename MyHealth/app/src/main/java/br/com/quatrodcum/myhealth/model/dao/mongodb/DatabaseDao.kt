package br.com.quatrodcum.myhealth.model.dao.mongodb

import android.content.Context
import br.com.quatrodcum.myhealth.model.domain.Database

class DatabaseDao(private val context: Context) {
    private val userDao = UserDao()
    private val ingredientDao = IngredientDao()
    private val mealDao = MealDao()
    private val objectiveDao = ObjectiveDao()
    private val unitOfMeasurementDao = UnitOfMeasurementDao()

    fun getDatabase(): Database {
        return try {
            loadDatabase()
        } catch (e: Exception) {
            loadDatabase()
        }
    }

    private fun loadDatabase(): Database {
        val users = userDao.getAll()
        val ingredients = ingredientDao.getAll()
        val objectives = objectiveDao.getAllObjectives()
        val unitOfMeasurements = unitOfMeasurementDao.getAll()
        val meals = mealDao.getAll()
        val ingredientMeals = meals.flatMap { it.ingredients }

        return Database(
            users = users,
            ingredients = ingredients,
            objectives = objectives,
            meals = meals,
            unitOfMeasurements = unitOfMeasurements,
            ingredientMeals = ingredientMeals
        )
    }
}