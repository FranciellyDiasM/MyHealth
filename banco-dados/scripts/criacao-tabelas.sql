CREATE TABLE objetivo (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    descricao TEXT NOT NULL
);

CREATE TABLE usuario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    imc REAL NOT NULL,
    senha TEXT NOT NULL,
    objetivo_id INTEGER NOT NULL,
    FOREIGN KEY (objetivo_id) REFERENCES objetivo(id) ON DELETE CASCADE
);

CREATE TABLE unidade_medida (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL
);

CREATE TABLE ingrediente (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL
);

CREATE TABLE refeicao (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    descricao TEXT NOT NULL,
    calorias REAL NOT NULL,
    modo_preparo TEXT NOT NULL,
    objetivo_id INTEGER NOT NULL,
    FOREIGN KEY (objetivo_id) REFERENCES objetivo(id) ON DELETE CASCADE
);

CREATE TABLE ingrediente_refeicao (
    ingrediente_id INTEGER NOT NULL,
    refeicao_id INTEGER NOT NULL,
    quantidade REAL NOT NULL,
    unidade_medida_id INTEGER NOT NULL,
    FOREIGN KEY (ingrediente_id) REFERENCES ingrediente(id) ON DELETE CASCADE,
    FOREIGN KEY (refeicao_id) REFERENCES refeicao(id) ON DELETE CASCADE,
    FOREIGN KEY (unidade_medida_id) REFERENCES unidade_medida(id) ON DELETE CASCADE
);
