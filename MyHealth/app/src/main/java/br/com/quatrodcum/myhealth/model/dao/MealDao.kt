package br.com.quatrodcum.myhealth.model.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.com.quatrodcum.myhealth.model.dao.DB.MEAL.COLUMN_CALORIES
import br.com.quatrodcum.myhealth.model.dao.DB.MEAL.COLUMN_DESCRIPTION
import br.com.quatrodcum.myhealth.model.dao.DB.MEAL.COLUMN_ID
import br.com.quatrodcum.myhealth.model.dao.DB.MEAL.COLUMN_NAME
import br.com.quatrodcum.myhealth.model.dao.DB.MEAL.COLUMN_OBJECTIVE_ID
import br.com.quatrodcum.myhealth.model.dao.DB.MEAL.COLUMN_PREPARATION_MODE
import br.com.quatrodcum.myhealth.model.dao.DB.MEAL.TABLE_NAME
import br.com.quatrodcum.myhealth.model.domain.Meal
import br.com.quatrodcum.myhealth.model.domain.Objective
import br.com.quatrodcum.myhealth.util.getDouble
import br.com.quatrodcum.myhealth.util.getInt
import br.com.quatrodcum.myhealth.util.getString

class MealDao(context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun getMeal(mealId: Int): Meal? {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            """
                    SELECT 
                        m.*, 
                        o.${DB.OBJECTIVE.COLUMN_ID} AS ${DB.OBJECTIVE.COLUMN_ID_ALIAS}, 
                        o.${DB.OBJECTIVE.COLUMN_DESCRIPTION} AS ${DB.OBJECTIVE.COLUMN_DESCRIPTION_ALIAS}
                    FROM $TABLE_NAME m
                    JOIN ${DB.OBJECTIVE.TABLE_NAME} o
                    ON m.$COLUMN_OBJECTIVE_ID = o.${DB.OBJECTIVE.COLUMN_ID}
                    WHERE m.$COLUMN_ID = ?;
                """,
            arrayOf(mealId.toString())
        )

        return if (cursor.moveToFirst()) {
            val objective = Objective(
                id = cursor.getInt(DB.OBJECTIVE.COLUMN_ID_ALIAS),
                description = cursor.getString(DB.OBJECTIVE.COLUMN_DESCRIPTION_ALIAS)
            )

            val meal = Meal(
                id = cursor.getInt(COLUMN_ID),
                name = cursor.getString(COLUMN_NAME),
                description = cursor.getString(COLUMN_DESCRIPTION),
                calories = cursor.getDouble(COLUMN_CALORIES),
                preparationMode = cursor.getString(COLUMN_PREPARATION_MODE),
                objective = objective,
                ingredients = emptyList()
            )
            cursor.close()
            meal
        } else {
            cursor.close()
            null
        }
    }

    fun getAll(): List<Meal> {
        val meals = mutableListOf<Meal>()
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            """
                    SELECT 
                        m.*, 
                        o.${DB.OBJECTIVE.COLUMN_ID} AS ${DB.OBJECTIVE.COLUMN_ID_ALIAS}, 
                        o.${DB.OBJECTIVE.COLUMN_DESCRIPTION} AS ${DB.OBJECTIVE.COLUMN_DESCRIPTION_ALIAS}
                    FROM $TABLE_NAME m
                    JOIN ${DB.OBJECTIVE.TABLE_NAME} o
                    ON m.$COLUMN_OBJECTIVE_ID = o.${DB.OBJECTIVE.COLUMN_ID};
                """,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val objective = Objective(
                    id = cursor.getInt(DB.OBJECTIVE.COLUMN_ID_ALIAS),
                    description = cursor.getString(DB.OBJECTIVE.COLUMN_DESCRIPTION_ALIAS)
                )

                val meal = Meal(
                    id = cursor.getInt(COLUMN_ID),
                    name = cursor.getString(COLUMN_NAME),
                    description = cursor.getString(COLUMN_DESCRIPTION),
                    calories = cursor.getDouble(COLUMN_CALORIES),
                    preparationMode = cursor.getString(COLUMN_PREPARATION_MODE),
                    objective = objective,
                    ingredients = emptyList()
                )
                meals.add(meal)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return meals
    }

    fun insert(meal: Meal) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        db.execSQL(
            """
                    INSERT INTO $TABLE_NAME 
                    (${COLUMN_NAME}, ${COLUMN_DESCRIPTION}, ${COLUMN_CALORIES}, ${COLUMN_PREPARATION_MODE}, ${COLUMN_OBJECTIVE_ID})
                    VALUES (?, ?, ?, ?, ?);
                """,
            arrayOf(
                meal.name,
                meal.description,
                meal.calories,
                meal.preparationMode,
                meal.objective.id
            )
        )
    }

    fun delete(mealId: Int) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        db.execSQL(
            "DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = ?;",
            arrayOf(mealId)
        )
    }
}