package br.com.quatrodcum.myhealth.model.dao.mongodb

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.INGREDIENT_MEAL
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.UNIT_OF_MEASUREMENT.COLUMN_ID
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.UNIT_OF_MEASUREMENT.COLUMN_NAME
import br.com.quatrodcum.myhealth.model.dao.mongodb.DB.UNIT_OF_MEASUREMENT.TABLE_NAME
import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement

class UnitOfMeasurementDao(context: Context) {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun getAll(): List<UnitOfMeasurement> {
        TODO()
    }

    fun insert(unitOfMeasurement: UnitOfMeasurement) {
        TODO()
    }

    fun update(unitOfMeasurement: UnitOfMeasurement) {
        TODO()
    }

    fun delete(unitOfMeasurement: UnitOfMeasurement) {
        TODO()
    }
}
