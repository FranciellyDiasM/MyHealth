package br.com.quatrodcum.myhealth.model.dao.mongodb

import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.UserEntity
import com.google.gson.Gson
import com.google.gson.JsonObject

class UserDao(private val database: Database) {
    private val gson = Gson()

    private fun matchesCondition(user: UserEntity, whereJson: JsonObject): Boolean {
        return whereJson.entrySet().all { (key, value) ->
            val userValue = when (key) {
                "id" -> user.id
                "name" -> user.name
                "email" -> user.email
                "imc" -> user.imc
                "password" -> user.password
                "objective" -> user.objective
                else -> null
            }
            userValue?.toString() == value.asString
        }
    }

    fun find(where: String? = null): List<UserEntity> {
        if (where.isNullOrEmpty()) {
            return database.users
        }
        val whereJson = gson.fromJson(where, JsonObject::class.java)
        return database.users.filter { matchesCondition(it, whereJson) }
    }

    fun insertOne(raw: String): UserEntity {
        val user = gson.fromJson(raw, UserEntity::class.java)
        val id = (database.users.maxOfOrNull { it.id ?: 0 } ?: 0) + 1

        user.id = id
        database.users.add(user)
        return user
    }

    fun updateMany(where: String, update: String) {
        val whereJson = gson.fromJson(where, JsonObject::class.java)
        val updateJson = gson.fromJson(update, JsonObject::class.java)
        val setFields = updateJson.getAsJsonObject("set")

        database.users.filter { matchesCondition(it, whereJson) }.forEach { user ->
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
    }

    fun delete(where: String) {
        val whereJson = gson.fromJson(where, JsonObject::class.java)
        database.users.removeIf { matchesCondition(it, whereJson) }
    }
}