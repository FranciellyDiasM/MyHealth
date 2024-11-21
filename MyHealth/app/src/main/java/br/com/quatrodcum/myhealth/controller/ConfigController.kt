package br.com.quatrodcum.myhealth.controller

import android.content.Context
import br.com.quatrodcum.myhealth.model.dao.sqlite.DatabaseHelper
import br.com.quatrodcum.myhealth.model.dao.sqlite.IngredientDao
import br.com.quatrodcum.myhealth.model.dao.sqlite.MealDao
import br.com.quatrodcum.myhealth.model.dao.sqlite.ObjectiveDao
import br.com.quatrodcum.myhealth.model.dao.sqlite.UnitOfMeasurementDao
import br.com.quatrodcum.myhealth.model.dao.sqlite.UserDao
import br.com.quatrodcum.myhealth.model.data.LocalPreferences
import br.com.quatrodcum.myhealth.model.domain.Ingredient
import br.com.quatrodcum.myhealth.model.domain.Objective
import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement

class ConfigController(context: Context) {

    private val unitOfMeasurementDao: UnitOfMeasurementDao = UnitOfMeasurementDao(context)
    private val objectiveDao: ObjectiveDao = ObjectiveDao(context)
    private val ingredientDao: IngredientDao = IngredientDao(context)
    private val databaseHelper = DatabaseHelper(context)
    private val localPreferences = LocalPreferences(context)
    private val userDao: UserDao = UserDao(context)
    private val mealDao: MealDao = MealDao(context)

    fun getUnitOfMeasurements(): List<UnitOfMeasurement> {
        return unitOfMeasurementDao.getAll()
    }

    fun insert(unitOfMeasurement: UnitOfMeasurement) {
        unitOfMeasurementDao.insert(unitOfMeasurement)
    }

    fun update(unitOfMeasurement: UnitOfMeasurement) {
        unitOfMeasurementDao.update(unitOfMeasurement)
    }

    fun delete(unitOfMeasurement: UnitOfMeasurement) {
        unitOfMeasurementDao.delete(unitOfMeasurement)
    }

    fun getObjectives(): List<Objective> {
        return objectiveDao.getAllObjectives()
    }

    fun insert(objective: Objective) {
        objectiveDao.insert(objective)
    }

    fun update(objective: Objective) {
        objectiveDao.update(objective)
    }

    fun delete(objective: Objective) {
        objectiveDao.delete(objective)
    }

    fun getIngredients(): List<Ingredient> {
        return ingredientDao.getAll()
    }

    fun insert(ingredient: Ingredient) {
        ingredientDao.insert(ingredient)
    }

    fun update(ingredient: Ingredient) {
        ingredientDao.update(ingredient)
    }

    fun delete(ingredient: Ingredient) {
        ingredientDao.delete(ingredient)
    }

    fun dropDatabase() {
        databaseHelper.deleteDatabase()
        localPreferences.clearLogin()
    }

    fun checkIfUsed(item: Objective) : Boolean {
        val objectiveId = item.id ?: return false

        var count = userDao.countUsesByObjectiveId(objectiveId)
        count += mealDao.countUsesByObjectiveId(objectiveId)

        return count > 0
    }

    fun checkIfUsed(item: Ingredient) : Boolean {
        val ingredientId = item.id ?: return false

        val count = mealDao.countUsesByIngredientId(ingredientId)

        return count > 0
    }

    fun checkIfUsed(item: UnitOfMeasurement) : Boolean {
        val ingredientId = item.id ?: return false

        val count = mealDao.countUsesByUnitOfMeasurement(ingredientId)

        return count > 0
    }
}