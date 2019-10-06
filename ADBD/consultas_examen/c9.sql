-- Puesto con mas sanciones

WITH NumSanciones AS(SELECT p.nro,COUNT(*) as nsan FROM Puesto p NATURAL JOIN Concesion c NATURAL JOIN Sancion s GROUP BY p.nro)SELECT * FROM NumSanciones ns WHERE ns.nsan >= (SELECT MAX(ns2.nsan) FROM NumSanciones ns2);

