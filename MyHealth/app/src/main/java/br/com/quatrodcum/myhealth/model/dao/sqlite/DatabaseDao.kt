package br.com.quatrodcum.myhealth.model.dao.sqlite

import android.content.Context
import br.com.quatrodcum.myhealth.model.domain.Database

class DatabaseDao(private val context: Context) {
    private val userDao = UserDao(context)
    private val ingredientDao = IngredientDao(context)
    private val mealDao = MealDao(context)
    private val objectiveDao = ObjectiveDao(context)
    private val unitOfMeasurementDao = UnitOfMeasurementDao(context)

    fun getDatabase(): Database {
        return try {
            loadDatabase()
        } catch (e: Exception) {
            DatabaseHelper(context).deleteDatabase()

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