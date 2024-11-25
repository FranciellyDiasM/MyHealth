package br.com.quatrodcum.myhealth.model.dao.mongodb

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.INGREDIENT.COLUMN_ID
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.INGREDIENT.COLUMN_NAME
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.INGREDIENT.TABLE_NAME
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.INGREDIENT_MEAL
import br.com.quatrodcum.myhealth.model.domain.Ingredient

class IngredientDao(context: Context) {

    fun getAll(): List<Ingredient> {
        TODO()
    }

    fun insert(ingredient: Ingredient) {
        TODO()
    }

    fun update(ingredient: Ingredient) {
        TODO()
    }

    fun delete(ingredient: Ingredient) {
        TODO()
    }
}
