package br.com.quatrodcum.myhealth.controller

import android.content.Context
import br.com.quatrodcum.myhealth.model.dao.MealDao
import br.com.quatrodcum.myhealth.model.dao.UserDao
import br.com.quatrodcum.myhealth.model.data.LocalPreferences
import br.com.quatrodcum.myhealth.model.domain.Login
import br.com.quatrodcum.myhealth.model.domain.Meal
import br.com.quatrodcum.myhealth.model.domain.User

class MealController(context: Context) {

    private val mealDao: MealDao = MealDao(context)

    fun getAllMeal() : List<Meal> {
        return mealDao.getAll()
    }
}