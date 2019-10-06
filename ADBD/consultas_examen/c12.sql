-- Sanciones entre 2000 y 2010 con el nombre del titular, ref de la sanción, y fecha

SELECT t.nombre,s.ref,s.fecha FROM Titular t,Concesion c,Sancion s WHERE t.dni=c.dni AND c.cod=s.cod AND EXTRACT(year FROM s.fecha) >=2000 AND EXTRACT (year FROM s.fecha) <= 2010;

