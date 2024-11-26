package br.com.quatrodcum.myhealth.model.dao.mongodb

import br.com.quatrodcum.myhealth.model.domain.User

class UserDao() {
    private val dbHelper: DatabaseHelper = DatabaseHelper()

    fun getUser(userId: Int): User? {
        TODO()
    }

    fun getAll(): List<User> {
        TODO()
    }

    fun insert(user: User) {
        TODO()
    }

    fun update(user: User) {
        TODO()
    }

    fun delete(userId: Int) {
        TODO()
    }

    fun getUserByEmailAndPassword(email: String, password: String): User? {
        TODO()
    }

    fun countUsesByObjectiveId(objectiveId: Int): Int {
        TODO()
    }
}
