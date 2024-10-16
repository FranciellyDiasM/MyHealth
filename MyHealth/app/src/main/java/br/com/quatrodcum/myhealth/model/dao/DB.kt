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
            VALUES ('Ganhar massa muscular'), ('Perder peso'), ('Dieta rica em ferro');
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

        val INIT = """
            INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_EMAIL, $COLUMN_IMC, $COLUMN_PASSWORD, $COLUMN_OBJECTIVE_ID)
            VALUES 
                ('João Silva', 'joao@email.com', 23.4, '123', 1),
                ('Maria Souza', 'maria@email.com', 27.8, '123', 2);
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
            VALUES 
                ('grama'), 
                ('pitada'), 
                ('colher de sopa'), 
                ('colher de chá'), 
                ('mililitro'), 
                ('litro'), 
                ('quilograma'), 
                ('unidade'), 
                ('fatia'), 
                ('xícara');
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
            VALUES 
                ('ovo'), 
                ('cebola'), 
                ('sal'), 
                ('leite'), 
                ('fermento'), 
                ('batata'), 
                ('frango'), 
                ('arroz'), 
                ('feijão'), 
                ('azeite');
        """.trimIndent()
    }

    object MEAL {
        const val TABLE_NAME = "refeicao"

        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "nome"
        const val COLUMN_DESCRIPTION = "descricao"
        const val COLUMN_CALORIES = "calorias"
        const val COLUMN_PREPARATION_MODE = "modo_preparo"
        const val COLUMN_OBJECTIVE_ID = "objetivo_id"

        val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT NOT NULL,
                $COLUMN_CALORIES REAL NOT NULL,
                $COLUMN_PREPARATION_MODE TEXT NOT NULL,
                $COLUMN_OBJECTIVE_ID INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_OBJECTIVE_ID) REFERENCES ${DB.OBJECTIVE.TABLE_NAME}(${DB.OBJECTIVE.COLUMN_ID})
            );
        """.trimIndent()

        val INIT = """
            INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_DESCRIPTION, $COLUMN_CALORIES, $COLUMN_PREPARATION_MODE, $COLUMN_OBJECTIVE_ID)
            VALUES 
                ('Omelete', 'Omelete com ovos e temperos', 250, 'Bater os ovos, fritar em uma frigideira.', 1),
                ('Salada de frango', 'Salada com peito de frango grelhado e vegetais', 350, 'Grelhar o frango e misturar com os vegetais.', 2),
                ('Arroz com feijão', 'Prato simples de arroz com feijão temperado', 400, 'Cozinhar o arroz e o feijão separadamente e temperar.', 3);
        """.trimIndent()
    }

    object INGREDIENT_MEAL {
        const val TABLE_NAME = "ingrediente_refeicao"

        const val COLUMN_INGREDIENT_ID = "ingrediente_id"
        const val COLUMN_MEAL_ID = "refeicao_id"
        const val COLUMN_QUANTITY = "quantidade"
        const val COLUMN_UNIT_OF_MEASURE_ID = "unidade_medida_id"

        val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_INGREDIENT_ID INTEGER NOT NULL,
                $COLUMN_MEAL_ID INTEGER NOT NULL,
                $COLUMN_QUANTITY REAL NOT NULL,
                $COLUMN_UNIT_OF_MEASURE_ID INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_INGREDIENT_ID) REFERENCES ${INGREDIENT.TABLE_NAME}(${INGREDIENT.COLUMN_ID}),
                FOREIGN KEY ($COLUMN_MEAL_ID) REFERENCES ${MEAL.TABLE_NAME}(${MEAL.COLUMN_ID}),
                FOREIGN KEY ($COLUMN_UNIT_OF_MEASURE_ID) REFERENCES ${UNIT_OF_MEASUREMENT.TABLE_NAME}(${UNIT_OF_MEASUREMENT.COLUMN_ID})
            );
        """.trimIndent()


        val INIT = """
            INSERT INTO $TABLE_NAME ($COLUMN_INGREDIENT_ID, $COLUMN_MEAL_ID, $COLUMN_QUANTITY, $COLUMN_UNIT_OF_MEASURE_ID)
            VALUES 
                (1, 1, 2, 8),       -- Omelete 2 unidades de ovo
                (2, 1, 100, 1),     -- Omelete 100 grama de cebola
                (3, 1, 1, 2),       -- Omelete 1 pitada de sal
                (7, 2, 200, 1),     -- Salada de Frango 200 gramas de frango
                (6, 2, 1, 8),       -- Salada de Frango 1 unidade de batata
                (10, 2, 1, 4),      -- Salada de Frango 1 colher de chá de azeite
                (3, 2, 1, 2),       -- Salada de Frango 1 pitada de sal
                (2, 2, 1, 8),       -- Salada de Frango 1 unidade de cebola
                (8, 3, 1, 10),      -- Arroz e feijao 1 xícara de arroz
                (9, 3, 1, 10),      -- Arroz e feijao 1 xícara de feijão
                (10, 3, 1, 3),      -- Arroz e feijao 1 colher de sopa de azeite
                (3, 3, 1, 2),       -- Arroz e feijao 1 pitada de sal
                (2, 3, 1, 9);       -- Arroz e feijao 1 fatia de cebola
        """.trimIndent()
    }


}