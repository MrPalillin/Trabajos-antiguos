-- Titulares(nombre) actuales que nunca en la historia del mercado han sido titulares de una frutería 'FRU'

WITH fruteros AS (SELECT DISTINCT t.nombre FROM Concesion c,Titular t WHERE c.dni=t.dni AND c.tipo LIKE 'FRU'),sel AS(SELECT t.nombre FROM Titular t WHERE t.nombre NOT IN (SELECT * FROM fruteros))SELECT s.* FROM sel s WHERE s.nombre IN (SELECT DISTINCT t.nombre FROM Titular t,Concesion c WHERE t.dni=c.dni AND c.fechaF IS NULL);

