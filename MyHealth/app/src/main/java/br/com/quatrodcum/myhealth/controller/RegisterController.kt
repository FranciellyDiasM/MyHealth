package br.com.quatrodcum.myhealth.controller

import android.content.Context
import br.com.quatrodcum.myhealth.model.dao.ObjectiveDao
import br.com.quatrodcum.myhealth.model.dao.UserDao
import br.com.quatrodcum.myhealth.model.domain.Objective
import br.com.quatrodcum.myhealth.model.domain.User

class RegisterController(context: Context) {

    private val objectiveDao: ObjectiveDao = ObjectiveDao(context)
    private val userDao: UserDao = UserDao(context)

    fun getAllObjectives() : List<Objective> {
        return objectiveDao.getAllObjectives()
    }

    fun insertUser(user: User) {
        userDao.insert(user)
    }
}