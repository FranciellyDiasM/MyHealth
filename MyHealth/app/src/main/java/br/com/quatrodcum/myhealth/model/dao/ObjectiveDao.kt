package br.com.quatrodcum.myhealth.model.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.com.quatrodcum.myhealth.model.domain.Objective


class ObjectiveDao(context: Context) {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun getAllObjectives(): List<Objective> {
        val objectives = mutableListOf<Objective>()
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM ${DB.OBJECTIVE.TABLE_NAME}",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val objective = Objective(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DB.OBJECTIVE.COLUMN_ID)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(DB.OBJECTIVE.COLUMN_DESCRIPTION))
                )
                objectives.add(objective)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return objectives
    }
}
