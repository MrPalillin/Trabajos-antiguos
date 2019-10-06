-- Los 5 titulares actuales (nombre) con más sanciones

WITH NumSanciones AS(SELECT t.nombre,COUNT(*) AS nsan FROM Titular t,Concesion c,Sancion s
WHERE t.dni=c.dni AND c.cod=s.cod AND c.fechaF IS NULL GROUP BY t.nombre)SELECT * FROM NumSanciones ns WHERE 5 > (SELECT COUNT(*) FROM NumSanciones ns2 WHERE ns2.nsan > ns.nsan) ORDER BY ns.nsan DESC;


