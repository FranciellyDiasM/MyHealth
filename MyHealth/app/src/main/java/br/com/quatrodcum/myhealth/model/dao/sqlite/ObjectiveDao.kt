package br.com.quatrodcum.myhealth.model.dao.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.com.quatrodcum.myhealth.model.dao.sqlite.DB.MEAL
import br.com.quatrodcum.myhealth.model.dao.sqlite.DB.OBJECTIVE.COLUMN_DESCRIPTION
import br.com.quatrodcum.myhealth.model.dao.sqlite.DB.OBJECTIVE.COLUMN_ID
import br.com.quatrodcum.myhealth.model.dao.sqlite.DB.OBJECTIVE.TABLE_NAME
import br.com.quatrodcum.myhealth.model.dao.sqlite.DB.USER
import br.com.quatrodcum.myhealth.model.domain.Objective


class ObjectiveDao(context: Context) {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun getAllObjectives(): List<Objective> {
        val objectives = mutableListOf<Objective>()
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val objective = Objective(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                )
                objectives.add(objective)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return objectives
    }

    fun insert(objective: Objective) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        db.execSQL(
            """
            INSERT INTO $TABLE_NAME 
            ($COLUMN_DESCRIPTION)
            VALUES (?);
            """,
            arrayOf(objective.description)
        )
    }

    fun update(objective: Objective) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        db.execSQL(
            """
                    UPDATE $TABLE_NAME 
                    SET $COLUMN_DESCRIPTION = ?
                    WHERE $COLUMN_ID = ?;
                """,
            arrayOf(objective.description, objective.id)
        )
    }

    fun delete(objective: Objective) {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        try {
            db.beginTransaction()
            db.execSQL(
                "DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = ?;",
                arrayOf(objective.id)
            )

            db.execSQL(
                "DELETE FROM ${USER.TABLE_NAME} WHERE ${USER.COLUMN_OBJECTIVE_ID} = ?;",
                arrayOf(objective.id)
            )

            db.execSQL(
                "DELETE FROM ${MEAL.TABLE_NAME} WHERE ${MEAL.COLUMN_OBJECTIVE_ID} = ?;",
                arrayOf(objective.id)
            )

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
}
