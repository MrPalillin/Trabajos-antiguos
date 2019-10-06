-- Persona/s (dni, nombre) que al menos han tenido 2 infracciones.

SELECT t.* FROM Titular t,concesion c,Sancion s WHERE t.dni=c.dni AND c.cod=s.cod GROUP BY t.dni HAVING COUNT(*) >= 2;

