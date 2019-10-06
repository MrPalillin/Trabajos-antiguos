-- Tipo de puesto, numero y porcentaje del que hay un mayor número actualmente. Los tipos de puesto son: FRU', 'ALI', 'CAR', 'ELE' ó 'ULT'.

WITH tipoPuesto AS(SELECT c.tipo,COUNT(*) AS num FROM Puesto p,Concesion c WHERE c.fechaF IS NULL AND p.nro=c.nro GROUP BY c.tipo),maximo AS(SELECT SUM(tp.num) as total FROM tipoPuesto tp)SELECT tp.*,(tp.num *100/m.total) AS percentage FROM tipoPuesto tp,maximo m WHERE tp.num >= (SELECT MAX(tp.num) as total FROM tipoPuesto tp);

