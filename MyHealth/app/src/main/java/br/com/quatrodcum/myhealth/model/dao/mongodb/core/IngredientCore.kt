package br.com.quatrodcum.myhealth.model.dao.mongodb.core

import br.com.quatrodcum.myhealth.model.dao.mongodb.Database
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.IngredientEntity
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.IngredientEntity.Companion.toDomain
import br.com.quatrodcum.myhealth.model.domain.Ingredient
import com.google.gson.Gson
import com.google.gson.JsonObject

class IngredientCore(private val database: Database) {
    private val gson = Gson()

    private fun matchesCondition(ingredient: IngredientEntity, where: IngredientEntity): Boolean {
        return listOf(
            where.id?.let { it == ingredient.id },
            where.name?.let { it == ingredient.name }
        ).all { it == null || it }
    }

    fun find(where: String = "{}"): List<Ingredient> {
        val whereJson = gson.fromJson(where, IngredientEntity::class.java)
        return database.tables.ingredients.filter { matchesCondition(it, whereJson) }.map {
            it.toDomain()
        }
    }

    fun insertOne(raw: String) {
        val ingredient = gson.fromJson(raw, IngredientEntity::class.java)
        val id = (database.tables.ingredients.maxOfOrNull { it.id ?: 0 } ?: 0) + 1
        ingredient.id = id
        database.tables.ingredients.add(ingredient)
        database.commit()
    }

    fun updateMany(where: String, update: String) {
        val whereJson = gson.fromJson(where, IngredientEntity::class.java)
        val updateJson = gson.fromJson(update, JsonObject::class.java)
        val setFields = updateJson.getAsJsonObject("set")

        database.tables.ingredients.filter { matchesCondition(it, whereJson) }.forEach { ingredient ->
            setFields.entrySet().forEach { (key, value) ->
                when (key) {
                    "id" -> ingredient.id = value.asInt
                    "name" -> ingredient.name = value.asString
                }
            }
        }
    }

    fun deleteMany(where: String = "{}") {
        val whereJson = gson.fromJson(where, IngredientEntity::class.java)
        val toDelete = database.tables.ingredients.filter { matchesCondition(it, whereJson) }

        toDelete.forEach { ingredient ->
            database.tables.meals.forEach { meal ->
                meal.ingredients?.removeIf { ingredientMeal ->
                    ingredientMeal.ingredient == ingredient.id
                }
            }
        }

        database.tables.ingredients.removeIf { matchesCondition(it, whereJson) }
        database.commit()
    }
}