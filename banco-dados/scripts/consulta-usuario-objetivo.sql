SELECT 
		u.nome, u.imc, o.descricao
FROM 
	usuario u
INNER JOIN
	objetivo o ON u.objetivo_id = o.id
ORDER BY
	u.imc DESC;  
	
