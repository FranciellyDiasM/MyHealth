package br.com.quatrodcum.myhealth.model.dao.mongodb

import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.UnitOfMeasurementEntity
import com.google.gson.Gson
import com.google.gson.JsonObject

class UnitOfMeasurementDao(private val database: Database) {
    private val gson = Gson()

    private fun matchesCondition(unit: UnitOfMeasurementEntity, whereJson: JsonObject): Boolean {
        return whereJson.entrySet().all { (key, value) ->
            val unitValue = when (key) {
                "id" -> unit.id
                "name" -> unit.name
                else -> null
            }
            unitValue?.toString() == value.asString
        }
    }

    fun find(where: String? = null): List<UnitOfMeasurementEntity> {
        if (where.isNullOrEmpty()) {
            return database.unitOfMeasurements
        }
        val whereJson = gson.fromJson(where, JsonObject::class.java)
        return database.unitOfMeasurements.filter { matchesCondition(it, whereJson) }
    }

    fun insertOne(raw: String): UnitOfMeasurementEntity {
        val unit = gson.fromJson(raw, UnitOfMeasurementEntity::class.java)
        val id = (database.unitOfMeasurements.maxOfOrNull { it.id ?: 0 } ?: 0) + 1
        unit.id = id
        database.unitOfMeasurements.add(unit)
        return unit
    }

    fun updateMany(where: String, update: String) {
        val whereJson = gson.fromJson(where, JsonObject::class.java)
        val updateJson = gson.fromJson(update, JsonObject::class.java)
        val setFields = updateJson.getAsJsonObject("set")
        database.unitOfMeasurements.filter { matchesCondition(it, whereJson) }.forEach { unit ->
            setFields.entrySet().forEach { (key, value) ->
                when (key) {
                    "id" -> unit.id = value.asInt
                    "name" -> unit.name = value.asString
                }
            }
        }
    }

    fun delete(where: String) {
        val whereJson = gson.fromJson(where, JsonObject::class.java)
        database.unitOfMeasurements.removeIf { matchesCondition(it, whereJson) }
    }
}