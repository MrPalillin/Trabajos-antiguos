-- Historial de todos los puestos(nro, fechaI, fechaF, nombre_titular) ordenado en orden descendente por número de Puesto y en orden ascendente por fechaI ( los puestos están numerados del 1 al 10)

SELECT c.nro,c.fechaI,c.fechaF,t.nombre FROM Concesion c NATURAL JOIN Titular t NATURAL JOIN Puesto p ORDER BY p.nro DESC,c.fechaI ASC;

