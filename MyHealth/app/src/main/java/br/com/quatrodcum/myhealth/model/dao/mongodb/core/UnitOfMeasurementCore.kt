package br.com.quatrodcum.myhealth.model.dao.mongodb.core

import br.com.quatrodcum.myhealth.model.dao.mongodb.Database
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.UnitOfMeasurementEntity
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.UnitOfMeasurementEntity.Companion.toDomain
import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement
import com.google.gson.Gson
import com.google.gson.JsonObject

class UnitOfMeasurementCore(private val database: Database) {
    private val gson = Gson()

    private fun matchesCondition(unit: UnitOfMeasurementEntity, where: UnitOfMeasurementEntity): Boolean {
        return listOf(
            where.id?.let { it == unit.id },
            where.name?.let { it == unit.name }
        ).all { it == null || it }
    }

    fun find(where: String = "{}"): List<UnitOfMeasurement> {
        val whereJson = gson.fromJson(where, UnitOfMeasurementEntity::class.java)
        return database.tables.unitOfMeasurements.filter { matchesCondition(it, whereJson) }.map {
            it.toDomain()
        }
    }

    fun insertOne(raw: String) {
        val unit = gson.fromJson(raw, UnitOfMeasurementEntity::class.java)
        val id = (database.tables.unitOfMeasurements.maxOfOrNull { it.id ?: 0 } ?: 0) + 1

        unit.id = id
        database.tables.unitOfMeasurements.add(unit)
        database.commit()
    }

    fun updateMany(where: String, update: String) {
        val whereJson = gson.fromJson(where, UnitOfMeasurementEntity::class.java)
        val updateJson = gson.fromJson(update, JsonObject::class.java)
        val setFields = updateJson.getAsJsonObject("set")

        database.tables.unitOfMeasurements.filter { matchesCondition(it, whereJson) }.forEach { unit ->
            setFields.entrySet().forEach { (key, value) ->
                when (key) {
                    "id" -> unit.id = value.asInt
                    "name" -> unit.name = value.asString
                }
            }
        }
        database.commit()
    }

    fun deleteMany(where: String = "{}") {
        val whereJson = gson.fromJson(where, UnitOfMeasurementEntity::class.java)
        val toDelete = database.tables.unitOfMeasurements.filter { matchesCondition(it, whereJson) }

        toDelete.forEach { unitOfMeasurement ->
            database.tables.meals.forEach { meal ->
                meal.ingredients?.removeIf { ingredientMeal ->
                    ingredientMeal.unitOfMeasurement == unitOfMeasurement.id
                }
            }
        }

        database.tables.unitOfMeasurements.removeIf { matchesCondition(it, whereJson) }

        database.commit()
    }
}