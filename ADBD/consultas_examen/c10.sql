-- Titular(es) que más puestos distintos han tenido

WITH NumPuestos AS(SELECT t.nombre,COUNT(DISTINCT c.nro) FROM Titular t,Concesion c,Puesto p WHERE t.dni=c.dni AND c.nro=p.nro GROUP BY t.nombre)SELECT * FROM NumPuestos np WHERE np.count >= (SELECT MAX(np2.count) FROM NumPuestos np2);

