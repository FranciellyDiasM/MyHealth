package br.com.quatrodcum.myhealth.model.dao.mongodb.core

import br.com.quatrodcum.myhealth.model.dao.mongodb.Database
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.ObjectiveEntity.Companion.toDomain
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.UserEntity
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.UserEntity.Companion.toDomain
import br.com.quatrodcum.myhealth.model.domain.User
import com.google.gson.Gson
import com.google.gson.JsonObject

class UserCore(private val database: Database) {
    private val gson = Gson()

    private fun matchesCondition(user: UserEntity, where: UserEntity): Boolean {
        return listOf(
            where.id?.let { it == user.id },
            where.name?.let { it == user.name },
            where.email?.let { it == user.email },
            where.imc?.let { it == user.imc },
            where.password?.let { it == user.password },
            where.objective?.let { it == user.objective }
        ).all { it == null || it }
    }

    fun find(where: String = "{}"): List<User> {
        val whereJson = gson.fromJson(where, UserEntity::class.java)
        return database.tables.users.filter { matchesCondition(it, whereJson) }.map { entity ->
            val objective = database.tables.objectives.first { it.id == entity.objective }.toDomain()

            entity.toDomain(objective)
        }
    }

    fun insertOne(raw: String): UserEntity {
        val user = gson.fromJson(raw, UserEntity::class.java)
        val id = (database.tables.users.maxOfOrNull { it.id ?: 0 } ?: 0) + 1
        user.id = id
        database.tables.users.add(user)
        database.commit()
        return user
    }

    fun updateMany(where: String, update: String) {
        val whereJson = gson.fromJson(where, UserEntity::class.java)
        val updateJson = gson.fromJson(update, JsonObject::class.java)
        val setFields = updateJson.getAsJsonObject("set")
        database.tables.users.filter { matchesCondition(it, whereJson) }.forEach { user ->
            setFields.entrySet().forEach { (key, value) ->
                when (key) {
                    "id" -> user.id = value.asInt
                    "name" -> user.name = value.asString
                    "email" -> user.email = value.asString
                    "imc" -> user.imc = value.asDouble
                    "password" -> user.password = value.asString
                    "objective" -> user.objective = value.asInt
                }
            }
        }
        database.commit()
    }

    fun deleteMany(where: String = "{}") {
        val whereJson = gson.fromJson(where, UserEntity::class.java)
        database.tables.users.removeIf { matchesCondition(it, whereJson) }
        database.commit()
    }
}