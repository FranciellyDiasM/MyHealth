package br.com.quatrodcum.myhealth.model.dao.mongodb

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.INGREDIENT
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.INGREDIENT_MEAL
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.MEAL.COLUMN_CALORIES
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.MEAL.COLUMN_DESCRIPTION
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.MEAL.COLUMN_ID
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.MEAL.COLUMN_NAME
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.MEAL.COLUMN_OBJECTIVE_ID
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.MEAL.COLUMN_PREPARATION_MODE
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.MEAL.TABLE_NAME
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.OBJECTIVE
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.UNIT_OF_MEASUREMENT
import br.com.quatrodcum.myhealth.model.domain.Ingredient
import br.com.quatrodcum.myhealth.model.domain.IngredientMeal
import br.com.quatrodcum.myhealth.model.domain.Meal
import br.com.quatrodcum.myhealth.model.domain.Objective
import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement
import br.com.quatrodcum.myhealth.util.getDouble
import br.com.quatrodcum.myhealth.util.getInt
import br.com.quatrodcum.myhealth.util.getString

class MealDao(context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

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