-- Titulares (nombre) actuales sin ninguna sanción

SELECT DISTINCT t.nombre FROM Titular t,Concesion c WHERE t.dni=c.dni AND c.cod NOT IN(SELECT cod FROM Sancion);

