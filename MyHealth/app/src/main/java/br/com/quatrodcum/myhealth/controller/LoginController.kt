package br.com.quatrodcum.myhealth.controller

import android.content.Context
import br.com.quatrodcum.myhealth.model.dao.UserDao
import br.com.quatrodcum.myhealth.model.data.LocalPreferences
import br.com.quatrodcum.myhealth.model.domain.Login
import br.com.quatrodcum.myhealth.model.domain.User

class LoginController(context: Context) {

    private val userDao: UserDao = UserDao(context)
    private val localPreferences = LocalPreferences(context)

    fun getCurrentLogin(): Login? {
        return localPreferences.getLogin()
    }

    fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }

    fun saveCurrentLogin(login: Login) {
        localPreferences.saveLogin(login)
    }
}