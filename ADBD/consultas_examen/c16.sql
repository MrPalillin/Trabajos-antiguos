-- Concesión o concesiones más largas

WITH Tiempo AS(SELECT c.cod,(c.fechaF-c.fechaI) as diff FROM Concesion c WHERE c.fechaF IS NOT NULL)SELECT * FROM Tiempo t WHERE t.diff >= (SELECT MAX(t2.diff) FROM Tiempo t2);

