package br.com.quatrodcum.myhealth.model.dao.mongodb

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.com.quatrodcum.myhealth.model.domain.Objective
import br.com.quatrodcum.myhealth.model.domain.User

class UserDao(context: Context) {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

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
