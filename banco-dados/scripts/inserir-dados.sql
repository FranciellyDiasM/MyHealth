INSERT INTO objetivo (descricao) VALUES ('Ganhar massa'),  ('Perder peso');

INSERT INTO usuario (nome, 	email,  senha, 	imc, objetivo_id)  VALUES  ('joao',  'joao@email.com', '123456',  22.7,	 1);
INSERT INTO usuario  (nome,  email,  senha,  imc, objetivo_id)  VALUES  ('maria',  'maria@email.com', '654321',  27.7,	 2);

INSERT INTO unidade_medida  (nome)  VALUES  ('grama'), ('pitada');

INSERT INTO refeicao  (nome,  descricao,  calorias,  modo_preparo, objetivo_id, usuario_id)  VALUES  ('ovo frito',  'ovo com sal direto na frigideira',  107.0,	'Ligar fogão, colocar óleo em uma frigideira e levá-la ao fogo, adicionar o ovo e o sal, esperar fritar e virar para fritar o outro lado, e fim.', 1, 1);

