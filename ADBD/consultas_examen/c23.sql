-- Tasa de sanciones por puesto (numero de sanciones / numero de puestos) por año, entre 2000 y 2010.

WITH numPuestos AS(SELECT COUNT(*) FROM Puesto),numSanciones AS(SELECT COUNT(*) FROM Sancion s WHERE EXTRACT(year FROM s.fecha) >= 2000 AND EXTRACT(year FROM s.fecha) <= 2010)SELECT (CAST(ns.count AS FLOAT)/CAST(np.count AS FLOAT)) AS tasa FROM numPuestos np,numSanciones ns;

