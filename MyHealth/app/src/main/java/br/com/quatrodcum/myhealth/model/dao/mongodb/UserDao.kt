package br.com.quatrodcum.myhealth.model.dao.mongodb

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.com.quatrodcum.myhealth.model.domain.Objective
import br.com.quatrodcum.myhealth.model.domain.User

class UserDao(context: Context) {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun getUser(userId: Int): User? {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            """
            SELECT 
                u.*, 
                o.${DB.OBJECTIVE.COLUMN_ID} AS ${DB.OBJECTIVE.COLUMN_ID_ALIAS}, 
                o.${DB.OBJECTIVE.COLUMN_DESCRIPTION} AS ${DB.OBJECTIVE.COLUMN_DESCRIPTION_ALIAS}
            FROM ${DB.USER.TABLE_NAME} u
            JOIN ${DB.OBJECTIVE.TABLE_NAME} o
            ON u.${DB.USER.COLUMN_OBJECTIVE_ID} = o.${DB.OBJECTIVE.COLUMN_ID}
            WHERE u.${DB.USER.COLUMN_ID} = ?;
            """,
            arrayOf(userId.toString())
        )

        return if (cursor.moveToFirst()) {
            val objective = Objective(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DB.OBJECTIVE.COLUMN_ID_ALIAS)),
                description = cursor.getString(cursor.getColumnIndexOrThrow(DB.OBJECTIVE.COLUMN_ID_ALIAS))
            )

            val user = User(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DB.USER.COLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(DB.USER.COLUMN_NAME)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(DB.USER.COLUMN_EMAIL)),
                imc = cursor.getDouble(cursor.getColumnIndexOrThrow(DB.USER.COLUMN_IMC)),
                password = cursor.getString(cursor.getColumnIndexOrThrow(DB.USER.COLUMN_PASSWORD)),
                objective = objective
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    fun getAll(): List<User> {
        val users = mutableListOf<User>()
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            """
            SELECT 
                u.*, 
                o.${DB.OBJECTIVE.COLUMN_ID} AS ${DB.OBJECTIVE.COLUMN_ID_ALIAS}, 
                o.${DB.OBJECTIVE.COLUMN_DESCRIPTION} AS ${DB.OBJECTIVE.COLUMN_DESCRIPTION_ALIAS}
            FROM ${DB.USER.TABLE_NAME} u
            JOIN ${DB.OBJECTIVE.TABLE_NAME} o
            ON u.${DB.USER.COLUMN_OBJECTIVE_ID} = o.${DB.OBJECTIVE.COLUMN_ID};
            """,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val objective = Objective(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DB.OBJECTIVE.COLUMN_ID_ALIAS)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(DB.OBJECTIVE.COLUMN_DESCRIPTION_ALIAS))
                )

                val user = User(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DB.USER.COLUMN_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(DB.USER.COLUMN_NAME)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(DB.USER.COLUMN_EMAIL)),
                    imc = cursor.getDouble(cursor.getColumnIndexOrThrow(DB.USER.COLUMN_IMC)),
                    password = cursor.getString(cursor.getColumnIndexOrThrow(DB.USER.COLUMN_PASSWORD)),
                    objective = objective
                )
                users.add(user)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return users
    }

    fun insert(user: User) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        db.execSQL(
            """
            INSERT INTO ${DB.USER.TABLE_NAME} 
            (${DB.USER.COLUMN_NAME}, ${DB.USER.COLUMN_EMAIL}, ${DB.USER.COLUMN_IMC}, ${DB.USER.COLUMN_PASSWORD}, ${DB.USER.COLUMN_OBJECTIVE_ID})
            VALUES (?, ?, ?, ?, ?);
            """,
            arrayOf(user.name, user.email, user.imc, user.password, user.objective.id)
        )
    }

    fun update(user: User) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        db.execSQL(
            """
                    UPDATE ${DB.USER.TABLE_NAME} 
                    SET ${DB.USER.COLUMN_NAME} = ?, ${DB.USER.COLUMN_EMAIL} = ?, ${DB.USER.COLUMN_IMC} = ?, ${DB.USER.COLUMN_PASSWORD} = ?, ${DB.USER.COLUMN_OBJECTIVE_ID} = ?
                    WHERE ${DB.USER.COLUMN_ID} = ?;
                """,
            arrayOf(user.name, user.email, user.imc, user.password, user.objective.id, user.id)
        )
    }

    fun delete(userId: Int) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        db.execSQL(
            "DELETE FROM ${DB.USER.TABLE_NAME} WHERE ${DB.USER.COLUMN_ID} = ?;",
            arrayOf(userId)
        )
    }

    fun getUserByEmailAndPassword(email: String, password: String): User? {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            """
                SELECT u.*, o.${DB.OBJECTIVE.COLUMN_ID} AS ${DB.OBJECTIVE.COLUMN_ID_ALIAS}, 
                       o.${DB.OBJECTIVE.COLUMN_DESCRIPTION} AS ${DB.OBJECTIVE.COLUMN_DESCRIPTION_ALIAS}
                FROM ${DB.USER.TABLE_NAME} u
                JOIN ${DB.OBJECTIVE.TABLE_NAME} o ON u.${DB.USER.COLUMN_OBJECTIVE_ID} = o.${DB.OBJECTIVE.COLUMN_ID}
                WHERE u.${DB.USER.COLUMN_EMAIL} = ? AND u.${DB.USER.COLUMN_PASSWORD} = ?;
                """, arrayOf(email, password)
        )

        return if (cursor.moveToFirst()) {
            val objective = Objective(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DB.OBJECTIVE.COLUMN_ID_ALIAS)),
                description = cursor.getString(cursor.getColumnIndexOrThrow(DB.OBJECTIVE.COLUMN_DESCRIPTION_ALIAS))
            )

            val user = User(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DB.USER.COLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(DB.USER.COLUMN_NAME)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(DB.USER.COLUMN_EMAIL)),
                imc = cursor.getDouble(cursor.getColumnIndexOrThrow(DB.USER.COLUMN_IMC)),
                password = cursor.getString(cursor.getColumnIndexOrThrow(DB.USER.COLUMN_PASSWORD)),
                objective = objective
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    fun countUsesByObjectiveId(objectiveId: Int): Int {
        val db = dbHelper.readableDatabase

        db.rawQuery(
            """
                SELECT 
                    COUNT(*) 
                FROM 
                    ${DB.USER.TABLE_NAME} 
                WHERE 
                    ${DB.USER.COLUMN_OBJECTIVE_ID} = ?;
            """.trimIndent(),
            arrayOf(objectiveId.toString())
        ).use { cursor ->

            if(cursor.moveToFirst()) {
                return cursor.getInt(0)
            }

        }

        return 0
    }
}
