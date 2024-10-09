package br.com.quatrodcum.myhealth.model.dao

object DB {
    object OBJECTIVE {
        const val TABLE_NAME = "objetivo"
        const val COLUMN_ID = "id"
        const val COLUMN_DESCRIPTION = "descricao"

        const val COLUMN_ID_ALIAS = "objetivo_id"
        const val COLUMN_DESCRIPTION_ALIAS = "objetivo_descricao"

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

    object USER {
        const val TABLE_NAME = "usuario"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "nome"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_IMC = "imc"
        const val COLUMN_PASSWORD = "senha"
        const val COLUMN_OBJECTIVE_ID = "objetivo_id"

        val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT UNIQUE NOT NULL,
                $COLUMN_IMC REAL NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL,
                $COLUMN_OBJECTIVE_ID INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_OBJECTIVE_ID) REFERENCES ${OBJECTIVE.TABLE_NAME}(${OBJECTIVE.COLUMN_ID})
            );
        """.trimIndent()
    }

    object UNIT_OF_MEASUREMENT {
        const val TABLE_NAME = "unidade_medida"

        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "nome"

        val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL
            );
        """.trimIndent()

        val INIT = """
            INSERT INTO $TABLE_NAME ($COLUMN_NAME)
            VALUES ('grama'), ('pitada'), ('colher'), ('mililitro'), ('kilograma'), ('unidade');
        """.trimIndent()

    }

    object INGREDIENT {
        const val TABLE_NAME = "ingredient"

        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "nome"

        val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL
            );
        """.trimIndent()

        val INIT = """
            INSERT INTO $TABLE_NAME ($COLUMN_NAME)
            VALUES ('ovo'), ('cebola'), ('sal'), ('leite'), ('fermento'), ('batata');
        """.trimIndent()
    }
}