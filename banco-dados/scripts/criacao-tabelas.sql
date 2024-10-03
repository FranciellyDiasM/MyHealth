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
	FOREIGN KEY (objetivo_id) REFERENCES objetivo(id),
	FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE unidade_medida (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	nome TEXT NOT NULL
);

CREATE TABLE ingrediente_refeicao (
	FOREIGN KEY (ingrediente_id) REFERENCES ingrediente(id),
	FOREIGN KEY (refeicao_id) REFERENCES refeicao(id),
	quantidade REAL NOT NULL,
	FOREIGN KEY (unidade_medida_id) REFERENCES unidade_medida(id)
);

