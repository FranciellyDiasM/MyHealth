package br.com.quatrodcum.myhealth.controller

import android.content.Context
import br.com.quatrodcum.myhealth.model.dao.sqlite.MealDao
import br.com.quatrodcum.myhealth.model.dao.sqlite.UserDao
import br.com.quatrodcum.myhealth.model.data.LocalPreferences
import br.com.quatrodcum.myhealth.model.data.Session
import br.com.quatrodcum.myhealth.model.domain.Meal
import br.com.quatrodcum.myhealth.model.domain.User

class MenuController(context: Context) {
    private val localPreferences = LocalPreferences(context)

    private val userDao = UserDao(context)
    private val mealDao = MealDao(context)

    fun logout() {
        localPreferences.clearLogin()
        Session.currentUser = null
    }

    fun getCurrentUser() : User? {
        if(Session.currentUser != null) {
            return Session.currentUser
        }

        val login = localPreferences.getLogin()

        if(login != null) {
            val user = userDao.getUserByEmailAndPassword(login.email, login.password)

            if(user != null) {
                return user
            }
        }

        return null
    }

    fun getSuggestions(objectiveId: Int) : List<Meal> {
        return mealDao.getByObjective(objectiveId).take(3)
    }
}