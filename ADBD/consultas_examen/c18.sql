-- Tipo de puesto y numero del que hay un mayor número actualmente. Los tipos de puesto son: FRU', 'ALI', 'CAR', 'ELE' ó 'ULT'

WITH tipoPuesto AS(SELECT c.tipo,COUNT(*) AS num FROM Puesto p,Concesion c WHERE c.fechaF IS NULL AND p.nro=c.nro GROUP BY c.tipo)SELECT * FROM tipoPuesto tp WHERE tp.num >= (SELECT MAX(tp2.num)FROM tipoPuesto tp2);

