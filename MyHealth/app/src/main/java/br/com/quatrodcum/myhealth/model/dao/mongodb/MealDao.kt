package br.com.quatrodcum.myhealth.model.dao.mongodb

import br.com.quatrodcum.myhealth.model.domain.Meal

class MealDao() {

    private val dbHelper: DatabaseHelper = DatabaseHelper()

    fun get(mealId: Int): Meal? {
        TODO()
    }

    fun getAll(): List<Meal> {
        TODO()
    }

    fun getByObjective(objectiveId: Int): List<Meal> {
        TODO()
    }

    fun insert(meal: Meal) {
        TODO()
    }

    fun update(meal: Meal) {
        TODO()
    }

    fun countUsesByObjectiveId(id: Int): Int {
        TODO()
    }

    fun countUsesByIngredientId(id: Int): Int {
        TODO()
    }

    fun countUsesByUnitOfMeasurement(id: Int): Int {
        TODO()
    }

    fun delete(id: Int) {
        TODO()
    }
}