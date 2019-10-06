-- 5 Concesiones finalizadas más largas y titular de cada una

WITH Tiempo AS(SELECT c.cod,(c.fechaF-c.fechaI) as diff FROM Concesion c WHERE c.fechaF IS NOT NULL ORDER BY diff DESC)SELECT * FROM Tiempo t WHERE 5 > (SELECT COUNT(*) FROM Tiempo t2 WHERE t2.diff > t.diff);

