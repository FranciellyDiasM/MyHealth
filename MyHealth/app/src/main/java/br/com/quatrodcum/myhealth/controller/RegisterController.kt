package br.com.quatrodcum.myhealth.controller

import android.content.Context
import br.com.quatrodcum.myhealth.model.dao.ObjectiveDao
import br.com.quatrodcum.myhealth.model.domain.Objective

class RegisterController(context: Context) {

    private val objectiveDao: ObjectiveDao = ObjectiveDao(context)

    fun getAllObjectives() : List<Objective> {
        return objectiveDao.getAllObjectives()
    }
}