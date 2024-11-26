package br.com.quatrodcum.myhealth.model.dao.mongodb

import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.ObjectiveEntity
import com.google.gson.Gson
import com.google.gson.JsonObject


class ObjectiveDao(private val database: Database) {
    private val gson = Gson()

    private fun matchesCondition(objective: ObjectiveEntity, whereJson: JsonObject): Boolean {
        return whereJson.entrySet().all { (key, value) ->
            val objectiveValue = when (key) {
                "id" -> objective.id
                "description" -> objective.description
                else -> null
            }
            objectiveValue?.toString() == value.asString
        }
    }

    fun find(where: String? = null): List<ObjectiveEntity> {
        if (where.isNullOrEmpty()) {
            return database.objectives
        }
        val whereJson = gson.fromJson(where, JsonObject::class.java)
        return database.objectives.filter { matchesCondition(it, whereJson) }
    }

    fun insertOne(raw: String): ObjectiveEntity {
        val objective = gson.fromJson(raw, ObjectiveEntity::class.java)
        val id = (database.objectives.maxOfOrNull { it.id ?: 0 } ?: 0) + 1

        objective.id = id
        database.objectives.add(objective)
        return objective
    }

    fun updateMany(where: String, update: String) {
        val whereJson = gson.fromJson(where, JsonObject::class.java)
        val updateJson = gson.fromJson(update, JsonObject::class.java)
        val setFields = updateJson.getAsJsonObject("set")

        database.objectives.filter { matchesCondition(it, whereJson) }.forEach { objective ->
            setFields.entrySet().forEach { (key, value) ->
                when (key) {
                    "id" -> objective.id = value.asInt
                    "description" -> objective.description = value.asString
                }
            }
        }
    }

    fun delete(where: String) {
        val whereJson = gson.fromJson(where, JsonObject::class.java)
        database.objectives.removeIf { matchesCondition(it, whereJson) }
    }
}
