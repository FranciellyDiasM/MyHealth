package br.com.quatrodcum.myhealth.model.dao

object DB {
    object OBJECTIVE {
        const val TABLE_NAME = "objetivo"
        const val COLUMN_ID = "id"
        const val COLUMN_DESCRIPTION = "descricao"

        val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DESCRIPTION TEXT NOT NULL
            );
        """.trimIndent()

        val INIT = """
            INSERT INTO $TABLE_NAME ($COLUMN_DESCRIPTION)
            VALUES ('Ganhar massa'), ('Perder Peso');
        """.trimIndent()
    }
}