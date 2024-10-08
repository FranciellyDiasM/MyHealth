package br.com.quatrodcum.myhealth.model.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement


class UnitOfMeasurementDao(context: Context) {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun getAll(): List<UnitOfMeasurement> {
        val unitOfMeasurements = mutableListOf<UnitOfMeasurement>()
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${DB.UNIT_OF_MEASUREMENT.TABLE_NAME}",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val unitOfMeasurement = UnitOfMeasurement(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DB.UNIT_OF_MEASUREMENT.COLUMN_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(DB.UNIT_OF_MEASUREMENT.COLUMN_NAME))
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
            INSERT INTO ${DB.UNIT_OF_MEASUREMENT.TABLE_NAME} 
            (${DB.UNIT_OF_MEASUREMENT.COLUMN_NAME})
            VALUES (?);
            """,
            arrayOf(unitOfMeasurement.name)
        )
    }
}
