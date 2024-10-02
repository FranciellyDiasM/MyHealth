package br.com.quatrodcum.myhealth.model.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "app_database.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DB.OBJECTIVE.CREATE_TABLE)
        db.execSQL(DB.USER.CREATE_TABLE)

        db.execSQL(DB.OBJECTIVE.INIT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DB.OBJECTIVE.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DB.USER.TABLE_NAME}")

        onCreate(db)
    }
}
