package br.com.quatrodcum.myhealth.model.dao.mongodb

import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.MealEntity
import com.google.gson.Gson
import com.google.gson.JsonObject

class MealDao(private val database: Database) {
    private val gson = Gson()

    private fun matchesCondition(meal: MealEntity, whereJson: JsonObject): Boolean {
        return whereJson.entrySet().all { (key, value) ->
            val mealValue = when (key) {
                "id" -> meal.id
                "name" -> meal.name
                "calories" -> meal.calories
                "description" -> meal.description
                "objective" -> meal.objective
                "preparationMode" -> meal.preparationMode
                else -> null
            }
            mealValue?.toString() == value.asString
        }
    }

    fun find(where: String? = null): List<MealEntity> {
        if (where.isNullOrEmpty()) {
            return database.meals
        }
        val whereJson = gson.fromJson(where, JsonObject::class.java)
        return database.meals.filter { matchesCondition(it, whereJson) }
    }

    fun insertOne(raw: String): MealEntity {
        val meal = gson.fromJson(raw, MealEntity::class.java)
        val id = (database.meals.maxOfOrNull { it.id ?: 0 } ?: 0) + 1
        meal.id = id
        database.meals.add(meal)
        return meal
    }

    fun updateMany(where: String, update: String) {
        val whereJson = gson.fromJson(where, JsonObject::class.java)
        val updateJson = gson.fromJson(update, JsonObject::class.java)
        val setFields = updateJson.getAsJsonObject("set")
        database.meals.filter { matchesCondition(it, whereJson) }.forEach { meal ->
            setFields.entrySet().forEach { (key, value) ->
                when (key) {
                    "id" -> meal.id = value.asInt
                    "name" -> meal.name = value.asString
                    "calories" -> meal.calories = value.asDouble
                    "description" -> meal.description = value.asString
                    "objective" -> meal.objective = value.asInt
                    "preparationMode" -> meal.preparationMode = value.asString
                }
            }
        }
    }

    fun delete(where: String) {
        val whereJson = gson.fromJson(where, JsonObject::class.java)
        database.meals.removeIf { matchesCondition(it, whereJson) }
    }
}