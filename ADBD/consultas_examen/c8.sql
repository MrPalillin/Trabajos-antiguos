-- Titulares con sancion en el año 2011

SELECT DISTINCT t.nombre,c.cod FROM Titular t,Concesion c,Sancion s WHERE t.dni=c.dni AND c.cod=s.cod AND EXTRACT(year FROM s.fecha) = '2011';

