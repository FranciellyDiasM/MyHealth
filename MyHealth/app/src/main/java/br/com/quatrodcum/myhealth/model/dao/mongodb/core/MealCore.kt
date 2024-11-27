package br.com.quatrodcum.myhealth.model.dao.mongodb.core

import br.com.quatrodcum.myhealth.model.dao.mongodb.Database
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.IngredientEntity.Companion.toDomain
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.IngredientMealEntity
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.MealEntity
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.MealEntity.Companion.toDomain
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.ObjectiveEntity
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.ObjectiveEntity.Companion.toDomain
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.UnitOfMeasurementEntity.Companion.toDomain
import br.com.quatrodcum.myhealth.model.domain.IngredientMeal
import br.com.quatrodcum.myhealth.model.domain.Meal
import br.com.quatrodcum.myhealth.util.transformFlattenedJson
import com.google.gson.Gson
import com.google.gson.JsonObject

class MealCore(private val database: Database) {
    private val gson = Gson()

    private fun matchesCondition(meal: MealEntity, where: MealEntity): Boolean {
        return listOf(
            where.id?.let { it == meal.id },
            where.name?.let { it == meal.name },
            where.calories?.let { it == meal.calories },
            where.description?.let { it == meal.description },
            where.objective?.let { it == meal.objective },
            where.preparationMode?.let { it == meal.preparationMode }
        ).all { it == null || it }
    }

    fun find(where: String = "{}"): List<Meal> {
        val whereJson = gson.fromJson(where.transformFlattenedJson(), MealEntity::class.java)

        return database.tables.meals.filter { matchesCondition(it, whereJson) }.map { entity ->
            entity.convert()
        }
    }

    fun insertOne(raw: String) {
        val meal = gson.fromJson(raw, MealEntity::class.java)
        val id = (database.tables.meals.maxOfOrNull { it.id ?: 0 } ?: 0) + 1
        meal.id = id
        database.tables.meals.add(meal)
        database.commit()
    }

    fun updateMany(where: String, update: String) {
        val whereJson = gson.fromJson(where, MealEntity::class.java)
        val updateJson = gson.fromJson(update, JsonObject::class.java)
        val setFields = updateJson.getAsJsonObject("set")
        database.tables.meals.filter { matchesCondition(it, whereJson) }.forEach { meal ->
            setFields.entrySet().forEach { (key, value) ->
                when (key) {
                    "id" -> meal.id = value.asInt
                    "name" -> meal.name = value.asString
                    "calories" -> meal.calories = value.asDouble
                    "description" -> meal.description = value.asString
                    "objective" -> meal.objective = value.asInt
                    "preparationMode" -> meal.preparationMode = value.asString
                    "ingredients" -> meal.ingredients =
                        gson.fromJson(value, Array<IngredientMealEntity>::class.java)
                            .toMutableList()
                }
            }
        }
        database.commit()
    }

    fun deleteMany(where: String = "{}") {
        val whereJson = gson.fromJson(where, MealEntity::class.java)
        database.tables.meals.removeIf { matchesCondition(it, whereJson) }
        database.commit()
    }

    private fun MealEntity.convert() : Meal {
        val objective = database.tables.objectives.first { it.id == this.objective }.toDomain()
        val ingredients = this.ingredients.orEmpty().map { im ->
            IngredientMeal(
                ingredient = database.tables.ingredients.first { it.id == im.ingredient }.toDomain(),
                unitOfMeasurement = database.tables.unitOfMeasurements.first { it.id == im.unitOfMeasurement }.toDomain(),
                quantity = im.quantity ?: -1
            )
        }

        return this.toDomain(
            objective,
            ingredients
        )
    }
}