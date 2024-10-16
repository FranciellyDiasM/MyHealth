package br.com.quatrodcum.myhealth.util

import android.database.Cursor

fun Cursor.getInt(columnName: String): Int {
    return this.getInt(getColumnIndexOrThrow(columnName))
}

fun Cursor.getString(columnName: String): String {
    return this.getString(getColumnIndexOrThrow(columnName))
}

fun Cursor.getDouble(columnName: String): Double {
    return this.getDouble(getColumnIndexOrThrow(columnName))
}