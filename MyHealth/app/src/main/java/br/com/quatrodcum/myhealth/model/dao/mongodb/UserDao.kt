package br.com.quatrodcum.myhealth.model.dao.mongodb

import br.com.quatrodcum.myhealth.db
import br.com.quatrodcum.myhealth.model.domain.User

class UserDao {

    fun getUser(userId: Int): User? {
        val users = db.users.find("""{ "id" : $userId }""")

        return users.firstOrNull()
    }

    fun getAll(): List<User> {
        return db.users.find()
    }

    fun insert(user: User) {
        val name = user.name
        val email = user.email
        val imc = user.imc
        val password = user.password
        val objective = user.objective.id

        db.users.insertOne(
            """
            { 
                "name" : "$name",
                "email" : "$email",
                "imc" : $imc,
                "password" : "$password",
                "objective" : $objective
            }
        """
        )
    }

    fun update(user: User) {
        val id = user.id
        val name = user.name
        val email = user.email
        val imc = user.imc
        val password = user.password
        val objective = user.objective.id

        db.users.updateMany(
            """{ "id" : $id }""",
            """{ 
                |"set" : { 
                |   "name" : "$name",
                |   "email" : "$email",
                |   "imc" : $imc,
                |   "password" : "$password",
                |   "objective" : $objective
                |}
            }""".trimMargin()
        )
    }

    fun delete(userId: Int) {
        db.users.deleteMany(
            """{ "id" : $userId }"""
        )
    }

    fun getUserByEmailAndPassword(email: String, password: String): User? {
        val users = db.users.find(
            """{ 
                   "email" : "$email",
                   "password" : "$password"
            }""")

        return users.firstOrNull()
    }

    fun countUsesByObjectiveId(objectiveId: Int): Int {
        return db.users.find(
            """{ "objective" : $objectiveId }"""
        ).size
    }
}
