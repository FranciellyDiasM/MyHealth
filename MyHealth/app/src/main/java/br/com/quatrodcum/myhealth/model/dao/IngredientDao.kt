package br.com.quatrodcum.myhealth.model.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.com.quatrodcum.myhealth.model.dao.DB.INGREDIENT.COLUMN_ID
import br.com.quatrodcum.myhealth.model.dao.DB.INGREDIENT.COLUMN_NAME
import br.com.quatrodcum.myhealth.model.dao.DB.INGREDIENT.TABLE_NAME
import br.com.quatrodcum.myhealth.model.dao.DB.INGREDIENT_MEAL
import br.com.quatrodcum.myhealth.model.domain.Ingredient

class IngredientDao(context: Context) {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun getAll(): List<Ingredient> {
        val ingredients = mutableListOf<Ingredient>()
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val ingredient = Ingredient(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                )
                ingredients.add(ingredient)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return ingredients
    }

    fun insert(ingredient: Ingredient) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        db.execSQL(
            """
            INSERT INTO $TABLE_NAME 
            ($COLUMN_NAME)
            VALUES (?);
            """,
            arrayOf(ingredient.name)
        )
    }

    fun update(ingredient: Ingredient) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        db.execSQL(
            """
                    UPDATE $TABLE_NAME 
                    SET $COLUMN_NAME = ?
                    WHERE $COLUMN_ID = ?;
                """,
            arrayOf(ingredient.name, ingredient.id)
        )
    }

    fun delete(ingredient: Ingredient) {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        try {
            db.beginTransaction()
            db.execSQL(
                "DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = ?;",
                arrayOf(ingredient.id)
            )

            db.execSQL(
                "DELETE FROM ${INGREDIENT_MEAL.TABLE_NAME} WHERE ${INGREDIENT_MEAL.COLUMN_INGREDIENT_ID} = ?",
                arrayOf(ingredient.id)
            )

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
}
