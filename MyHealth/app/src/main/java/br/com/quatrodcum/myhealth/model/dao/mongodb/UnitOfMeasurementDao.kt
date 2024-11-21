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
        val unitOfMeasurements = mutableListOf<UnitOfMeasurement>()
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val unitOfMeasurement = UnitOfMeasurement(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                )
                unitOfMeasurements.add(unitOfMeasurement)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return unitOfMeasurements
    }

    fun insert(unitOfMeasurement: UnitOfMeasurement) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        db.execSQL(
            """
            INSERT INTO $TABLE_NAME 
            ($COLUMN_NAME)
            VALUES (?);
            """,
            arrayOf(unitOfMeasurement.name)
        )
    }

    fun update(unitOfMeasurement: UnitOfMeasurement) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        db.execSQL(
            """
                    UPDATE $TABLE_NAME 
                    SET $COLUMN_NAME = ?
                    WHERE $COLUMN_ID = ?;
                """,
            arrayOf(unitOfMeasurement.name, unitOfMeasurement.id)
        )
    }

    fun delete(unitOfMeasurement: UnitOfMeasurement) {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        try {
            db.beginTransaction()
            db.execSQL(
                "DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = ?;",
                arrayOf(unitOfMeasurement.id)
            )

            db.execSQL(
                "DELETE FROM ${INGREDIENT_MEAL.TABLE_NAME} WHERE ${INGREDIENT_MEAL.COLUMN_UNIT_OF_MEASURE_ID} = ?",
                arrayOf(unitOfMeasurement.id)
            )

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
}
