package br.com.quatrodcum.myhealth.controller

import android.content.Context
import br.com.quatrodcum.myhealth.model.dao.DatabaseDao
import br.com.quatrodcum.myhealth.model.dao.UserDao
import br.com.quatrodcum.myhealth.model.data.LocalPreferences
import br.com.quatrodcum.myhealth.model.data.Session
import br.com.quatrodcum.myhealth.model.domain.Database
import br.com.quatrodcum.myhealth.model.domain.User

class SplashScreenController(context: Context) {

    private val userDao: UserDao = UserDao(context)
    private val databaseDao: DatabaseDao = DatabaseDao(context)
    private val localPreferences = LocalPreferences(context)

    fun loadLoggedUser(): User? {
        val login = localPreferences.getLogin() ?: return null
        val user = userDao.getUserByEmailAndPassword(login.email, login.password) ?: return null

        Session.currentUser = user

        return user
    }

    fun getDatabase() : Database {
        return databaseDao.getDatabase()
    }

}