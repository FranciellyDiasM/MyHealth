package br.com.quatrodcum.myhealth.model.dao.mongodb

import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.IngredientEntity
import com.google.gson.Gson
import com.google.gson.JsonObject

class IngredientDao(private val database: Database) {
    private val gson = Gson()

    private fun matchesCondition(ingredient: IngredientEntity, whereJson: JsonObject): Boolean {
        return whereJson.entrySet().all { (key, value) ->
            val ingredientValue = when (key) {
                "id" -> ingredient.id
                "name" -> ingredient.name
                else -> null
            }
            ingredientValue?.toString() == value.asString
        }
    }

    fun find(where: String? = null): List<IngredientEntity> {
        if (where.isNullOrEmpty()) {
            return database.ingredients
        }
        val whereJson = gson.fromJson(where, JsonObject::class.java)
        return database.ingredients.filter { matchesCondition(it, whereJson) }
    }

    fun insertOne(raw: String): IngredientEntity {
        val ingredient = gson.fromJson(raw, IngredientEntity::class.java)
        val id = (database.ingredients.maxOfOrNull { it.id ?: 0 } ?: 0) + 1
        ingredient.id = id
        database.ingredients.add(ingredient)
        return ingredient
    }

    fun updateMany(where: String, update: String) {
        val whereJson = gson.fromJson(where, JsonObject::class.java)
        val updateJson = gson.fromJson(update, JsonObject::class.java)
        val setFields = updateJson.getAsJsonObject("set")
        database.ingredients.filter { matchesCondition(it, whereJson) }.forEach { ingredient ->
            setFields.entrySet().forEach { (key, value) ->
                when (key) {
                    "id" -> ingredient.id = value.asInt
                    "name" -> ingredient.name = value.asString
                }
            }
        }
    }

    fun delete(where: String) {
        val whereJson = gson.fromJson(where, JsonObject::class.java)
        database.ingredients.removeIf { matchesCondition(it, whereJson) }
    }
}