package br.com.quatrodcum.myhealth.model.dao.mongodb.core

import br.com.quatrodcum.myhealth.model.dao.mongodb.Database
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.ObjectiveEntity
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.ObjectiveEntity.Companion.toDomain
import br.com.quatrodcum.myhealth.model.domain.Objective
import com.google.gson.Gson
import com.google.gson.JsonObject


class ObjectiveCore(private val database: Database) {
    private val gson = Gson()

    private fun matchesCondition(objective: ObjectiveEntity, where: ObjectiveEntity): Boolean {
        return listOf(
            where.id?.let { it == objective.id },
            where.description?.let { it == objective.description }
        ).all { it == null || it }
    }

    fun find(where: String = "{}"): List<Objective> {
        val whereJson = gson.fromJson(where, ObjectiveEntity::class.java)
        return database.tables.objectives.filter { matchesCondition(it, whereJson) }.map {
            it.toDomain()
        }
    }

    fun insertOne(raw: String) {
        val objective = gson.fromJson(raw, ObjectiveEntity::class.java)
        val id = (database.tables.objectives.maxOfOrNull { it.id ?: 0 } ?: 0) + 1

        objective.id = id
        database.tables.objectives.add(objective)
        database.commit()
    }

    fun updateMany(where: String, update: String) {
        val whereJson = gson.fromJson(where, ObjectiveEntity::class.java)
        val updateJson = gson.fromJson(update, JsonObject::class.java)
        val setFields = updateJson.getAsJsonObject("set")

        database.tables.objectives.filter { matchesCondition(it, whereJson) }.forEach { objective ->
            setFields.entrySet().forEach { (key, value) ->
                when (key) {
                    "id" -> objective.id = value.asInt
                    "description" -> objective.description = value.asString
                }
            }
        }
        database.commit()
    }

    fun deleteMany(where: String = "{}") {
        val whereJson = gson.fromJson(where, ObjectiveEntity::class.java)
        val objectivesToDelete = database.tables.objectives.filter { matchesCondition(it, whereJson) }

        objectivesToDelete.forEach { objective ->
            database.tables.users.removeIf { user -> user.objective == objective.id }
            database.tables.meals.removeIf { meal -> meal.objective == objective.id}
        }

        database.tables.objectives.removeIf { matchesCondition(it, whereJson) }
        database.commit()
    }
}
