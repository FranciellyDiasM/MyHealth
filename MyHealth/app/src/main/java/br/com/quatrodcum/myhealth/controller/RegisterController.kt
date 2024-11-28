package br.com.quatrodcum.myhealth.controller

import android.content.Context
import br.com.quatrodcum.myhealth.model.dao.mongodb.ObjectiveDao
import br.com.quatrodcum.myhealth.model.dao.mongodb.UserDao
import br.com.quatrodcum.myhealth.model.domain.Objective
import br.com.quatrodcum.myhealth.model.domain.User
import kotlin.math.pow

class RegisterController(context: Context) {

    private val objectiveDao: ObjectiveDao = ObjectiveDao()
    private val userDao: UserDao = UserDao()

    fun getAllObjectives(): List<Objective> {
        return objectiveDao.getAllObjectives()
    }

    fun insertUser(user: User) {
        userDao.insert(user)
    }

    fun calculateImc(height: Double, weight: Double): Double {
        return weight / (height.pow(2.0))
    }
}