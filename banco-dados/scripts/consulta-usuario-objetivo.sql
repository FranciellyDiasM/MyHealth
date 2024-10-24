-- BUSCA TODAS AS RECEITAS COM OS INGREDIENTES
SELECT 
  m.*, 
  o.id AS obj_id, 
  o.descricao AS obj_descricao, 
  i.id AS ing_id, 
  i.nome AS ing_nome, 
  ir.quantidade AS ir_quantidade, 
  um.id AS um_id, 
  um.nome AS um_nome 
FROM 
  refeicao m 
  JOIN objetivo o ON m.objetivo_id = o.id 
  LEFT JOIN ingrediente_refeicao ir ON m.id = ir.refeicao_id 
  LEFT JOIN ingrediente i ON ir.ingrediente_id = i.id 
  LEFT JOIN unidade_medida um ON ir.unidade_medida_id = um.id 

-- BUSCA OMELETE E TODOS INGREDIENTES
SELECT 
  m.*, 
  o.id AS obj_id, 
  o.descricao AS obj_descricao, 
  i.id AS ing_id, 
  i.nome AS ing_nome, 
  ir.quantidade AS ir_quantidade, 
  um.id AS um_id, 
  um.nome AS um_nome 
FROM 
  refeicao m 
  JOIN objetivo o ON m.objetivo_id = o.id 
  LEFT JOIN ingrediente_refeicao ir ON m.id = ir.refeicao_id 
  LEFT JOIN ingrediente i ON ir.ingrediente_id = i.id 
  LEFT JOIN unidade_medida um ON ir.unidade_medida_id = um.id 
WHERE 
  m.id = 1;

  -- BUSCA USUARIOS COM OS OBJETIVOS
SELECT 
		u.nome, u.imc, o.descricao
FROM 
	usuario u
INNER JOIN
	objetivo o ON u.objetivo_id = o.id
ORDER BY
	u.imc DESC;
	
	-- BUSCA TODAS AS REFEICOES PARA USUARIO JOAO
	SELECT r.id, r.nome, r.descricao, r.calorias, r.modo_preparo
FROM refeicao r
JOIN objetivo o ON r.objetivo_id = o.id
JOIN usuario u ON u.objetivo_id = o.id
WHERE u.id = 1;
