package br.com.quatrodcum.myhealth.model.dao.mongodb

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "app_database.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DB.OBJECTIVE.CREATE_TABLE)
        db.execSQL(DB.USER.CREATE_TABLE)
        db.execSQL(DB.UNIT_OF_MEASUREMENT.CREATE_TABLE)
        db.execSQL(DB.INGREDIENT.CREATE_TABLE)
        db.execSQL(DB.MEAL.CREATE_TABLE)
        db.execSQL(DB.INGREDIENT_MEAL.CREATE_TABLE)

        db.execSQL(DB.OBJECTIVE.INIT)
        db.execSQL(DB.USER.INIT)
        db.execSQL(DB.UNIT_OF_MEASUREMENT.INIT)
        db.execSQL(DB.INGREDIENT.INIT)
        db.execSQL(DB.MEAL.INIT)
        db.execSQL(DB.INGREDIENT_MEAL.INIT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DB.INGREDIENT_MEAL.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DB.INGREDIENT.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DB.UNIT_OF_MEASUREMENT.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DB.USER.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DB.MEAL.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DB.OBJECTIVE.TABLE_NAME}")

        onCreate(db)
    }

    fun deleteDatabase(): Boolean {
        return context.deleteDatabase(DATABASE_NAME)
    }
}
