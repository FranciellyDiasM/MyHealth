package br.com.quatrodcum.myhealth.model.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.com.quatrodcum.myhealth.model.dao.DB.INGREDIENT
import br.com.quatrodcum.myhealth.model.dao.DB.INGREDIENT_MEAL
import br.com.quatrodcum.myhealth.model.dao.DB.MEAL.COLUMN_CALORIES
import br.com.quatrodcum.myhealth.model.dao.DB.MEAL.COLUMN_DESCRIPTION
import br.com.quatrodcum.myhealth.model.dao.DB.MEAL.COLUMN_ID
import br.com.quatrodcum.myhealth.model.dao.DB.MEAL.COLUMN_NAME
import br.com.quatrodcum.myhealth.model.dao.DB.MEAL.COLUMN_OBJECTIVE_ID
import br.com.quatrodcum.myhealth.model.dao.DB.MEAL.COLUMN_PREPARATION_MODE
import br.com.quatrodcum.myhealth.model.dao.DB.MEAL.TABLE_NAME
import br.com.quatrodcum.myhealth.model.dao.DB.OBJECTIVE
import br.com.quatrodcum.myhealth.model.dao.DB.UNIT_OF_MEASUREMENT
import br.com.quatrodcum.myhealth.model.domain.Ingredient
import br.com.quatrodcum.myhealth.model.domain.IngredientMeal
import br.com.quatrodcum.myhealth.model.domain.Meal
import br.com.quatrodcum.myhealth.model.domain.Objective
import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement
import br.com.quatrodcum.myhealth.util.getDouble
import br.com.quatrodcum.myhealth.util.getInt
import br.com.quatrodcum.myhealth.util.getString

class MealDao(context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun get(mealId: Int): Meal? {
        val db = dbHelper.readableDatabase
        var meal: Meal? = null

        val cursor = db.rawQuery(
            """SELECT 
                    m.*,
                    o.id AS ${OBJECTIVE.COLUMN_ID_ALIAS}, 
                    o.descricao AS ${OBJECTIVE.COLUMN_DESCRIPTION_ALIAS},
                    i.id AS ${INGREDIENT.COLUMN_ID_ALIAS},
                    i.nome AS ${INGREDIENT.COLUMN_NAME_ALIAS},
                    ir.quantidade AS ${INGREDIENT_MEAL.COLUMN_QUANTITY_ALIAS},
                    um.id AS ${UNIT_OF_MEASUREMENT.COLUMN_ID_ALIAS},
                    um.nome AS ${UNIT_OF_MEASUREMENT.COLUMN_NAME_ALIAS}
                FROM $TABLE_NAME m
                JOIN ${OBJECTIVE.TABLE_NAME} o ON m.${COLUMN_OBJECTIVE_ID} = o.${OBJECTIVE.COLUMN_ID}
                LEFT JOIN ${INGREDIENT_MEAL.TABLE_NAME} ir ON m.${COLUMN_ID} = ir.${INGREDIENT_MEAL.COLUMN_MEAL_ID}
                LEFT JOIN ${INGREDIENT.TABLE_NAME} i ON ir.${INGREDIENT_MEAL.COLUMN_INGREDIENT_ID} = i.${INGREDIENT.COLUMN_ID}
                LEFT JOIN ${UNIT_OF_MEASUREMENT.TABLE_NAME} um ON ir.${INGREDIENT_MEAL.COLUMN_UNIT_OF_MEASURE_ID} = um.${UNIT_OF_MEASUREMENT.COLUMN_ID}
                WHERE m.${COLUMN_ID} = ?;
                """.trimIndent(), arrayOf(mealId.toString())
        )

        cursor.use {
            if (cursor.moveToFirst()) {
                val mealName = cursor.getString(COLUMN_NAME)
                val mealCalories = cursor.getDouble(COLUMN_CALORIES)
                val mealDescription = cursor.getString(COLUMN_DESCRIPTION)
                val mealPreparationMode = cursor.getString(COLUMN_PREPARATION_MODE)

                val objectiveId = cursor.getInt(OBJECTIVE.COLUMN_ID_ALIAS)
                val objectiveDescription = cursor.getString(OBJECTIVE.COLUMN_DESCRIPTION_ALIAS)
                val objective = Objective(objectiveId, objectiveDescription)

                meal = Meal(
                    id = mealId,
                    name = mealName,
                    calories = mealCalories,
                    description = mealDescription,
                    objective = objective,
                    preparationMode = mealPreparationMode,
                    ingredients = mutableListOf()
                )

                do {
                    val ingredientId = cursor.getInt(INGREDIENT.COLUMN_ID_ALIAS)
                    val ingredientName = cursor.getString(INGREDIENT.COLUMN_NAME_ALIAS)
                    val ingredientQuantity = cursor.getInt(INGREDIENT_MEAL.COLUMN_QUANTITY_ALIAS)
                    val unitOfMeasureId = cursor.getInt(UNIT_OF_MEASUREMENT.COLUMN_ID_ALIAS)
                    val unitOfMeasureName = cursor.getString(UNIT_OF_MEASUREMENT.COLUMN_NAME_ALIAS)

                    if (ingredientId > 0) {
                        val ingredient = Ingredient(id = ingredientId, name = ingredientName)
                        val unitOfMeasure = UnitOfMeasurement(unitOfMeasureId, unitOfMeasureName)

                        val ingredientMeal =
                            IngredientMeal(ingredient, unitOfMeasure, ingredientQuantity)

                        (meal?.ingredients as MutableList).add(ingredientMeal)
                    }

                } while (cursor.moveToNext())
            }
        }

        return meal
    }

    fun getAll(): List<Meal> {
        val db = dbHelper.readableDatabase
        val mealsMap = mutableMapOf<Int, Meal>()

        val cursor = db.rawQuery(
            """SELECT 
                m.*,
                o.id AS ${OBJECTIVE.COLUMN_ID_ALIAS}, 
                o.descricao AS ${OBJECTIVE.COLUMN_DESCRIPTION_ALIAS},
                i.id AS ${INGREDIENT.COLUMN_ID_ALIAS},
                i.nome AS ${INGREDIENT.COLUMN_NAME_ALIAS},
                ir.quantidade AS ${INGREDIENT_MEAL.COLUMN_QUANTITY_ALIAS},
                um.id AS ${UNIT_OF_MEASUREMENT.COLUMN_ID_ALIAS},
                um.nome AS ${UNIT_OF_MEASUREMENT.COLUMN_NAME_ALIAS}
            FROM $TABLE_NAME m
            JOIN ${OBJECTIVE.TABLE_NAME} o ON m.${COLUMN_OBJECTIVE_ID} = o.${OBJECTIVE.COLUMN_ID}
            LEFT JOIN ${INGREDIENT_MEAL.TABLE_NAME} ir ON m.${COLUMN_ID} = ir.${INGREDIENT_MEAL.COLUMN_MEAL_ID}
            LEFT JOIN ${INGREDIENT.TABLE_NAME} i ON ir.${INGREDIENT_MEAL.COLUMN_INGREDIENT_ID} = i.${INGREDIENT.COLUMN_ID}
            LEFT JOIN ${UNIT_OF_MEASUREMENT.TABLE_NAME} um ON ir.${INGREDIENT_MEAL.COLUMN_UNIT_OF_MEASURE_ID} = um.${UNIT_OF_MEASUREMENT.COLUMN_ID};"""
                .trimIndent(), null
        )

        cursor.use {
            if (cursor.moveToFirst()) {
                do {
                    val mealId = cursor.getInt(COLUMN_ID)

                    val meal = mealsMap.getOrPut(mealId) {
                        val mealName = cursor.getString(COLUMN_NAME)
                        val mealCalories = cursor.getDouble(COLUMN_CALORIES)
                        val mealDescription = cursor.getString(COLUMN_DESCRIPTION)
                        val mealPreparationMode = cursor.getString(COLUMN_PREPARATION_MODE)

                        val objectiveId = cursor.getInt(OBJECTIVE.COLUMN_ID_ALIAS)
                        val objectiveDescription =
                            cursor.getString(OBJECTIVE.COLUMN_DESCRIPTION_ALIAS)
                        val objective = Objective(objectiveId, objectiveDescription)

                        Meal(
                            id = mealId,
                            name = mealName,
                            calories = mealCalories,
                            description = mealDescription,
                            objective = objective,
                            preparationMode = mealPreparationMode,
                            ingredients = mutableListOf()
                        )
                    }

                    Log.i("MyHealth", meal.name)

                    val ingredientId = cursor.getInt(INGREDIENT.COLUMN_ID_ALIAS)
                    val ingredientName = cursor.getString(INGREDIENT.COLUMN_NAME_ALIAS)
                    val ingredientQuantity = cursor.getInt(INGREDIENT_MEAL.COLUMN_QUANTITY_ALIAS)
                    val unitOfMeasureId = cursor.getInt(UNIT_OF_MEASUREMENT.COLUMN_ID_ALIAS)
                    val unitOfMeasureName = cursor.getString(UNIT_OF_MEASUREMENT.COLUMN_NAME_ALIAS)


                    if (ingredientId > 0) {
                        val ingredient = Ingredient(id = ingredientId, name = ingredientName)
                        val unitOfMeasure = UnitOfMeasurement(unitOfMeasureId, unitOfMeasureName)

                        val ingredientMeal =
                            IngredientMeal(ingredient, unitOfMeasure, ingredientQuantity)

                        (meal.ingredients as MutableList).add(ingredientMeal)
                    }


                } while (cursor.moveToNext())
            }
        }

        return mealsMap.values.toList()
    }

    fun insert(meal: Meal) {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        try {
            db.beginTransaction()

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

            var mealInsertedId = -1
            db.rawQuery("SELECT last_insert_rowid()", null).use { cursor ->
                if (cursor.moveToFirst()) {
                    mealInsertedId = cursor.getInt(0)
                }
            }

            meal.ingredients.forEach { ingredientMeal ->
                db.execSQL(
                    """
                            INSERT INTO ${INGREDIENT_MEAL.TABLE_NAME}
                            (
                                ${INGREDIENT_MEAL.COLUMN_INGREDIENT_ID}, 
                                ${INGREDIENT_MEAL.COLUMN_MEAL_ID}, 
                                ${INGREDIENT_MEAL.COLUMN_QUANTITY}, 
                                ${INGREDIENT_MEAL.COLUMN_UNIT_OF_MEASURE_ID}) 
                            VALUES(?, ?, ?, ?);
                        """,
                    arrayOf(
                        ingredientMeal.ingredient.id,
                        mealInsertedId,
                        ingredientMeal.quantity,
                        ingredientMeal.unitOfMeasurement.id
                    )
                )
            }

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    fun update(meal: Meal) {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        try {
            db.beginTransaction()

            db.execSQL(
                """
                    UPDATE $TABLE_NAME 
                    SET $COLUMN_NAME = ?, $COLUMN_DESCRIPTION = ?, $COLUMN_CALORIES = ?, $COLUMN_PREPARATION_MODE = ?, $COLUMN_OBJECTIVE_ID = ?
                    WHERE $COLUMN_ID = ?;
                """,
                arrayOf(
                    meal.name,
                    meal.description,
                    meal.calories,
                    meal.preparationMode,
                    meal.objective.id,
                    meal.id
                )
            )

            db.execSQL(
                """
                    DELETE FROM ${INGREDIENT_MEAL.TABLE_NAME} WHERE ${INGREDIENT_MEAL.COLUMN_MEAL_ID} = ?;
                """.trimIndent(),
                arrayOf(meal.id)
            )

            meal.ingredients.forEach { ingredientMeal ->
                db.execSQL(
                    """
                            INSERT INTO ${INGREDIENT_MEAL.TABLE_NAME}
                            (
                                ${INGREDIENT_MEAL.COLUMN_INGREDIENT_ID}, 
                                ${INGREDIENT_MEAL.COLUMN_MEAL_ID}, 
                                ${INGREDIENT_MEAL.COLUMN_QUANTITY}, 
                                ${INGREDIENT_MEAL.COLUMN_UNIT_OF_MEASURE_ID}) 
                            VALUES(?, ?, ?, ?);
                        """,
                    arrayOf(
                        ingredientMeal.ingredient.id,
                        meal.id,
                        ingredientMeal.quantity,
                        ingredientMeal.unitOfMeasurement.id
                    )
                )
            }

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    fun delete(mealId: Int) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        db.execSQL(
            "DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = ?;",
            arrayOf(mealId)
        )
    }
}