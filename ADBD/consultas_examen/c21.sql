-- Puesto (nro) que más tiempo ha sido una frutería ('FRU') en toda la historia del mercado

WITH s1 AS(SELECT c.nro,SUM(c.fechaF-c.fechaI) AS diff FROM Concesion c WHERE c.tipo LIKE 'FRU' AND c.fechaF IS NOT NULL GROUP BY c.nro),s2 AS (SELECT c.nro,SUM(CURRENT_DATE-c.fechaI) AS diff FROM Concesion c WHERE c.tipo LIKE 'FRU' AND c.fechaF IS NULL GROUP BY c.nro),s3 AS(SELECT * FROM s1 UNION SELECT * FROM s2),s4 AS(SELECT s.nro,SUM(s.diff) FROM s3 s GROUP BY s.nro), s5 AS (SELECT MAX(s.sum) FROM s4 s)SELECT s.* FROM s4 s,s5 ss WHERE s.sum >= ss.max;

