package br.com.quatrodcum.myhealth.controller

import android.content.Context
import br.com.quatrodcum.myhealth.model.dao.mongodb.DatabaseDao as MongoDatabaseDao
import br.com.quatrodcum.myhealth.model.dao.sqlite.DatabaseDao as SqliteDatabaseDao
import br.com.quatrodcum.myhealth.model.dao.mongodb.UserDao
import br.com.quatrodcum.myhealth.model.data.LocalPreferences
import br.com.quatrodcum.myhealth.model.data.Session
import br.com.quatrodcum.myhealth.model.domain.Database
import br.com.quatrodcum.myhealth.model.domain.User

class SplashScreenController(context: Context) {

    private val userDao: UserDao = UserDao()
    private val databaseDao: MongoDatabaseDao by lazy { MongoDatabaseDao() }
    private val sqliteDatabaseDao: SqliteDatabaseDao by lazy { SqliteDatabaseDao(context) }
    private val localPreferences = LocalPreferences(context)

    fun loadLoggedUser(): User? {
        val login = localPreferences.getLogin() ?: return null
        val user = userDao.getUserByEmailAndPassword(login.email, login.password) ?: return null

        Session.currentUser = user

        return user
    }

    fun getDatabase() : Database {
        val database = databaseDao.getDatabase()

        val databaseIsEmpty = listOf(
            database.users,
            database.ingredients,
            database.objectives,
            database.meals,
            database.unitOfMeasurements
        ).count { it.isNotEmpty() } == 0

        if(databaseIsEmpty) {
            migrateFromSqlite()
        }

        return databaseDao.getDatabase()
    }

    private fun migrateFromSqlite() {
        val sqliteDatabase = sqliteDatabaseDao.getDatabase()

        databaseDao.loadFrom(sqliteDatabase)
    }

}