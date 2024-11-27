package br.com.quatrodcum.myhealth.model.dao.mongodb

import android.content.Context
import android.content.SharedPreferences
import br.com.quatrodcum.myhealth.model.dao.mongodb.core.IngredientCore
import br.com.quatrodcum.myhealth.model.dao.mongodb.core.MealCore
import br.com.quatrodcum.myhealth.model.dao.mongodb.core.ObjectiveCore
import br.com.quatrodcum.myhealth.model.dao.mongodb.core.UnitOfMeasurementCore
import br.com.quatrodcum.myhealth.model.dao.mongodb.core.UserCore
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.UserEntity
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.IngredientEntity
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.ObjectiveEntity
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.MealEntity
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.UnitOfMeasurementEntity
import com.google.gson.Gson

class Database {
    private val gson = Gson()
    private var sharedPreferences: SharedPreferences? = null
    val tables = Tables()

    val users = UserCore(this)
    val ingredients = IngredientCore(this)
    val objectives = ObjectiveCore(this)
    val meals = MealCore(this)
    val unitOfMeasurements = UnitOfMeasurementCore(this)

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences("DatabasePrefs", Context.MODE_PRIVATE)
        loadFromPreferences()
    }

    fun commit() {
        sharedPreferences?.edit()?.apply {
            val jsonString = gson.toJson(tables)

            putString("database", jsonString)
            apply()
        }
    }

    fun getRawDatabase() : String{
        return gson.toJson(tables)
    }

    private fun loadFromPreferences() {
        val json = sharedPreferences?.getString("database", null)
        if (!json.isNullOrEmpty()) {
            val loadedDatabase: Tables = gson.fromJson(json, Tables::class.java)

            tables.users.addAll(loadedDatabase.users)
            tables.ingredients.addAll(loadedDatabase.ingredients)
            tables.objectives.addAll(loadedDatabase.objectives)
            tables.meals.addAll(loadedDatabase.meals)
            tables.unitOfMeasurements.addAll(loadedDatabase.unitOfMeasurements)
        }
    }
}

data class Tables(
    val users: MutableList<UserEntity> = mutableListOf(),
    val ingredients: MutableList<IngredientEntity> = mutableListOf(),
    val objectives: MutableList<ObjectiveEntity> = mutableListOf(),
    val meals: MutableList<MealEntity> = mutableListOf(),
    val unitOfMeasurements: MutableList<UnitOfMeasurementEntity> = mutableListOf()
)