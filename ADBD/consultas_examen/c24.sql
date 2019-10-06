-- Puesto que ha sido más tipos de establecimientos distintos (FRU', 'ALI', 'CAR', 'ELE' ó 'ULT') a lo largo de la historia del mercado.

WITH numTipos AS(SELECT c.nro,COUNT(DISTINCT c.tipo) FROM Concesion c GROUP BY c.nro) SELECT * FROM numTipos nt WHERE nt.count >=(SELECT MAX(nt2.count) FROM numTipos nt2);

