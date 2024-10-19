package br.com.quatrodcum.myhealth.model.dao

object DB {
    object OBJECTIVE {
        const val TABLE_NAME = "objetivo"
        const val COLUMN_ID = "id"
        const val COLUMN_DESCRIPTION = "descricao"

        const val COLUMN_ID_ALIAS = "obj_id"
        const val COLUMN_DESCRIPTION_ALIAS = "obj_descricao"

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

        const val COLUMN_ID_ALIAS = "um_id"
        const val COLUMN_NAME_ALIAS = "um_nome"

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

        const val COLUMN_ID_ALIAS = "ing_id"
        const val COLUMN_NAME_ALIAS = "ing_nome"

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
                ('azeite'),
                ('tomate'), 
                ('alface'), 
                ('cenoura'), 
                ('peito de peru'), 
                ('queijo'), 
                ('pão integral'), 
                ('banana'), 
                ('aveia'), 
                ('mel'), 
                ('iogurte natural');
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
                ('Arroz com feijão', 'Prato simples de arroz com feijão temperado', 400, 'Cozinhar o arroz e o feijão separadamente e temperar.', 3),
                ('Sanduíche de peito de peru', 'Sanduíche com peito de peru, queijo e vegetais', 300, 'Montar o sanduíche com os ingredientes.', 1),
                ('Salada de alface e tomate', 'Salada simples com alface, tomate e temperos', 150, 'Misturar os ingredientes e temperar.', 2),
                ('Panqueca de aveia e banana', 'Panqueca saudável feita com aveia e banana', 200, 'Misturar os ingredientes e fritar em uma frigideira.', 1),
                ('Iogurte com mel e aveia', 'Iogurte natural com mel e aveia', 180, 'Misturar o iogurte com mel e aveia.', 3),
                ('Batata doce assada', 'Batata doce assada com azeite e temperos', 250, 'Assar a batata doce no forno com azeite e temperos.', 2),
                ('Frango grelhado com legumes', 'Frango grelhado acompanhado de legumes cozidos', 400, 'Grelhar o frango e cozinhar os legumes.', 1),
                ('Omelete de queijo e tomate', 'Omelete com queijo e tomate', 280, 'Bater os ovos, adicionar queijo e tomate, e fritar.', 1),
                ('Smoothie de banana e aveia', 'Bebida saudável com banana, aveia e iogurte', 220, 'Bater todos os ingredientes no liquidificador.', 3),
                ('Arroz integral com legumes', 'Arroz integral com legumes variados', 350, 'Cozinhar o arroz e os legumes separadamente e misturar.', 2),
                ('Salada de frutas', 'Salada de frutas variadas com iogurte', 200, 'Misturar as frutas e adicionar iogurte.', 3);
        """.trimIndent()
    }

    object INGREDIENT_MEAL {
        const val TABLE_NAME = "ingrediente_refeicao"

        const val COLUMN_INGREDIENT_ID = "ingrediente_id"
        const val COLUMN_MEAL_ID = "refeicao_id"
        const val COLUMN_QUANTITY = "quantidade"
        const val COLUMN_UNIT_OF_MEASURE_ID = "unidade_medida_id"

        const val COLUMN_QUANTITY_ALIAS = "ir_quantidade"

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
                -- Omelete
                (1, 1, 2, 8),       -- 2 unidades de ovo
                (2, 1, 100, 1),     -- 100 grama de cebola
                (3, 1, 1, 2),       -- 1 pitada de sal
                
                -- Salada de Frango 
                (7, 2, 200, 1),     -- 200 gramas de frango
                (6, 2, 1, 8),       -- 1 unidade de batata
                (10, 2, 1, 4),      -- 1 colher de chá de azeite
                (3, 2, 1, 2),       -- 1 pitada de sal
                (2, 2, 1, 8),       -- 1 unidade de cebola
                
                -- Arroz e feijao 
                (8, 3, 1, 10),      -- 1 xícara de arroz
                (9, 3, 1, 10),      -- 1 xícara de feijão
                (10, 3, 1, 3),      -- 1 colher de sopa de azeite
                (3, 3, 1, 2),       -- 1 pitada de sal
                (2, 3, 1, 9),       -- 1 fatia de cebola
                
                -- Sanduíche de peito de peru
                (11, 4, 2, 8),      -- 2 unidades de tomate
                (12, 4, 2, 8),      -- 2 unidades de alface
                (14, 4, 2, 8),      -- 2 fatias de peito de peru
                (15, 4, 1, 8),      -- 1 fatia de queijo
                (16, 4, 2, 8),      -- 2 fatias de pão integral
            
                -- Salada de alface e tomate
                (11, 5, 1, 8),      -- 1 unidade de tomate
                (12, 5, 3, 8),      -- 3 folhas de alface
                (3, 5, 1, 2),       -- 1 pitada de sal
                (10, 5, 1, 3),      -- 1 colher de sopa de azeite
            
                -- Panqueca de aveia e banana
                (17, 6, 1, 8),      -- 1 unidade de banana
                (18, 6, 50, 1),     -- 50 gramas de aveia
                (1, 6, 2, 8),       -- 2 unidades de ovo
            
                -- Iogurte com mel e aveia
                (19, 7, 1, 8),      -- 1 unidade de iogurte natural
                (18, 7, 30, 1),     -- 30 gramas de aveia
                (20, 7, 1, 3),      -- 1 colher de sopa de mel
            
                -- Batata doce assada
                (6, 8, 1, 8),       -- 1 unidade de batata doce
                (10, 8, 1, 3),      -- 1 colher de sopa de azeite
                (3, 8, 1, 2),       -- 1 pitada de sal
            
                -- Frango grelhado com legumes
                (7, 9, 200, 1),     -- 200 gramas de frango
                (6, 9, 1, 8),       -- 1 unidade de batata
                (13, 9, 1, 8),      -- 1 unidade de cenoura
                (11, 9, 1, 8),      -- 1 unidade de tomate
            
                -- Omelete de queijo e tomate
                (1, 10, 2, 8),      -- 2 unidades de ovo
                (15, 10, 1, 8),     -- 1 fatia de queijo
                (11, 10, 1, 8),     -- 1 unidade de tomate
            
                -- Smoothie de banana e aveia
                (17, 11, 1, 8),     -- 1 unidade de banana
                (18, 11, 30, 1),    -- 30 gramas de aveia
                (19, 11, 1, 8),     -- 1 unidade de iogurte natural
            
                -- Arroz integral com legumes
                (8, 12, 1, 10),     -- 1 xícara de arroz integral
                (13, 12, 1, 8),     -- 1 unidade de cenoura
                (11, 12, 1, 8),     -- 1 unidade de tomate
                (12, 12, 3, 8),     -- 3 folhas de alface
            
                -- Salada de frutas
                (17, 13, 1, 8),     -- 1 unidade de banana
                (11, 13, 1, 8),     -- 1 unidade de tomate
                (19, 13, 1, 8),     -- 1 unidade de iogurte natural
                (20, 13, 1, 3);     -- 1 colher de sopa de mel
        """.trimIndent()
    }
}