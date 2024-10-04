CREATE TABLE objetivo (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    descricao TEXT NOT NULL
);

CREATE TABLE usuario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    senha TEXT NOT NULL,
    imc REAL NOT NULL,
    objetivo_id INTEGER NOT NULL,
    FOREIGN KEY (objetivo_id) REFERENCES objetivo(id)
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
    usuario_id INTEGER NOT NULL, -- Adicionando a coluna usuario_id
    FOREIGN KEY (objetivo_id) REFERENCES objetivo(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) -- Agora pode referenciar
);

CREATE TABLE unidade_medida (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL
);

CREATE TABLE ingrediente_refeicao (
    id INTEGER PRIMARY KEY AUTOINCREMENT, -- Adicionei uma chave primária
    ingrediente_id INTEGER NOT NULL,
    refeicao_id INTEGER NOT NULL,
    quantidade REAL NOT NULL,
    unidade_medida_id INTEGER NOT NULL,
    FOREIGN KEY (ingrediente_id) REFERENCES ingrediente(id),
    FOREIGN KEY (refeicao_id) REFERENCES refeicao(id),
    FOREIGN KEY (unidade_medida_id) REFERENCES unidade_medida(id)
);
