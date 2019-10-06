-- Cantidad total pagada en sanciones por cada titular

SELECT t.nombre,SUM(s.cantidad) AS total FROM Titular t,Concesion c,Sancion s WHERE t.dni=c.dni AND c.cod=s.cod GROUP BY t.dni;

