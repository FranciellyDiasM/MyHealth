package br.com.quatrodcum.myhealth.model.dao.mongodb

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.MEAL
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.OBJECTIVE.COLUMN_DESCRIPTION
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.OBJECTIVE.COLUMN_ID
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.OBJECTIVE.TABLE_NAME
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.USER
import br.com.quatrodcum.myhealth.model.domain.Objective


class ObjectiveDao(context: Context) {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun getAllObjectives(): List<Objective> {
        TODO()
    }

    fun insert(objective: Objective) {
        TODO()
    }

    fun update(objective: Objective) {
        TODO()
    }

    fun delete(objective: Objective) {
        TODO()
    }
}
