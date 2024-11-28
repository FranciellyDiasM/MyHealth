package br.com.quatrodcum.myhealth.model.dao.mongodb

import br.com.quatrodcum.myhealth.db
import br.com.quatrodcum.myhealth.model.domain.Database

class DatabaseDao() {
    private val userDao = UserDao()
    private val ingredientDao = IngredientDao()
    private val mealDao = MealDao()
    private val objectiveDao = ObjectiveDao()
    private val unitOfMeasurementDao = UnitOfMeasurementDao()

    fun getDatabase(): Database {
        return try {
            loadDatabase()
        } catch (e: Exception) {
            clearDatabase()

            loadDatabase()
        }
    }

    private fun clearDatabase() {
        db.users.deleteMany()
        db.objectives.deleteMany()
        db.unitOfMeasurements.deleteMany()
        db.ingredients.deleteMany()
        db.meals.deleteMany()
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

    fun loadFrom(sqliteDatabase: Database) {
        sqliteDatabase.objectives.forEach { entity ->
            objectiveDao.insert(entity)
        }

        sqliteDatabase.ingredients.forEach { entity ->
            ingredientDao.insert(entity)
        }

        sqliteDatabase.unitOfMeasurements.forEach { entity ->
            unitOfMeasurementDao.insert(entity)
        }

        sqliteDatabase.users.forEach { entity ->
            userDao.insert(entity)
        }

        sqliteDatabase.meals.forEach { entity ->
            mealDao.insert(entity)
        }
    }
}