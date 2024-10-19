package br.com.quatrodcum.myhealth.controller

import android.content.Context
import br.com.quatrodcum.myhealth.model.dao.IngredientDao
import br.com.quatrodcum.myhealth.model.dao.MealDao
import br.com.quatrodcum.myhealth.model.dao.ObjectiveDao
import br.com.quatrodcum.myhealth.model.dao.UnitOfMeasurementDao
import br.com.quatrodcum.myhealth.model.domain.Ingredient
import br.com.quatrodcum.myhealth.model.domain.Meal
import br.com.quatrodcum.myhealth.model.domain.Objective
import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement

class MealController(context: Context) {

    private val mealDao: MealDao = MealDao(context)
    private val objectiveDao: ObjectiveDao = ObjectiveDao(context)
    private val unitOfMeasurementDao: UnitOfMeasurementDao = UnitOfMeasurementDao(context)
    private val ingredientDao: IngredientDao = IngredientDao(context)

    fun getAllObjectives(): List<Objective> {
        return objectiveDao.getAllObjectives()
    }

    fun getAllMeal(): List<Meal> {
        return mealDao.getAll()
    }

    fun getMeal(id: Int): Meal? {
        return mealDao.get(id)
    }

    fun deleteMeal(mealId: Int) {
        mealDao.delete(mealId)
    }

    fun insert(meal: Meal) {
        mealDao.insert(meal)
    }

    fun update(meal: Meal) {
        mealDao.update(meal)
    }

    fun getAllIngredients() : List<Ingredient>{
        return ingredientDao.getAll()
    }

    fun getAllUnitOfMeasurements() : List<UnitOfMeasurement>{
        return unitOfMeasurementDao.getAll()
    }


}