SELECT 
    m.id AS meal_id, 
    m.nome AS meal_name, 
    m.calorias AS meal_calories, 
    m.descricao AS meal_description, 
    m.modo_preparo AS meal_preparation_mode,
    o.id AS objective_id, 
    o.descricao AS objective_description,
    i.id AS ingredient_id,
    i.nome AS ingredient_name,
    ir.quantidade AS ingredient_quantity,
    um.id AS unit_of_measure_id,
    um.nome AS unit_of_measure_name
FROM refeicao m
JOIN objetivo o ON m.objetivo_id = o.id
LEFT JOIN ingrediente_refeicao ir ON m.id = ir.refeicao_id
LEFT JOIN ingredient i ON ir.ingrediente_id = i.id
LEFT JOIN unidade_medida um ON ir.unidade_medida_id = um.id;


SELECT 
		u.nome, u.imc, o.descricao
FROM 
	usuario u
INNER JOIN
	objetivo o ON u.objetivo_id = o.id
ORDER BY
	u.imc DESC;
