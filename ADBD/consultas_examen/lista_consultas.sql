-- Titular(es) que más puestos distintos han tenido

WITH NumPuestos AS(SELECT t.nombre,COUNT(DISTINCT c.nro) FROM Titular t,Concesion c,Puesto p WHERE t.dni=c.dni AND c.nro=p.nro GROUP BY t.nombre)SELECT * FROM NumPuestos np WHERE np.count >= (SELECT MAX(np2.count) FROM NumPuestos np2);

-- 5 Concesiones finalizadas más largas y titular de cada una

WITH Tiempo AS(SELECT c.cod,(c.fechaF-c.fechaI) as diff FROM Concesion c WHERE c.fechaF IS NOT NULL ORDER BY diff DESC)SELECT * FROM Tiempo t WHERE 5 > (SELECT COUNT(*) FROM Tiempo t2 WHERE t2.diff > t.diff);

-- Sanciones entre 2000 y 2010 con el nombre del titular, ref de la sanción, y fecha

SELECT t.nombre,s.ref,s.fecha FROM Titular t,Concesion c,Sancion s WHERE t.dni=c.dni AND c.cod=s.cod AND EXTRACT(year FROM s.fecha) >=2000 AND EXTRACT (year FROM s.fecha) <= 2010;

-- Titulares sin ninguna sanción

SELECT DISTINCT t.nombre FROM Titular t,Concesion c WHERE t.dni=c.dni AND c.cod NOT IN (SELECT s.cod FROM Sancion s);

-- Cantidad total pagada en sanciones por cada titular

SELECT t.nombre,SUM(s.cantidad) AS total FROM Titular t,Concesion c,Sancion s WHERE t.dni=c.dni AND c.cod=s.cod GROUP BY t.dni;

-- Cantidad media pagada en sanciones por cada titular

SELECT t.nombre,AVG(s.cantidad) AS total FROM Titular t,Concesion c,Sancion s WHERE t.dni=c.dni AND c.cod=s.cod GROUP BY t.dni;

-- Concesión o concesiones más largas

WITH Tiempo AS(SELECT c.cod,(c.fechaF-c.fechaI) as diff FROM Concesion c WHERE c.fechaF IS NOT NULL)SELECT * FROM Tiempo t WHERE t.diff >= (SELECT MAX(t2.diff) FROM Tiempo t2);

-- Persona/s (dni, nombre) que al menos han tenido 2 infracciones.

SELECT t.* FROM Titular t,concesion c,Sancion s WHERE t.dni=c.dni AND c.cod=s.cod GROUP BY t.dni HAVING COUNT(*) >= 2;

-- Tipo de puesto y numero del que hay un mayor número actualmente. Los tipos de puesto son: FRU', 'ALI', 'CAR', 'ELE' ó 'ULT'

WITH tipoPuesto AS(SELECT c.tipo,COUNT(*) AS num FROM Puesto p,Concesion c WHERE c.fechaF IS NULL AND p.nro=c.nro GROUP BY c.tipo)SELECT * FROM tipoPuesto tp WHERE tp.num >= (SELECT MAX(tp2.num)FROM tipoPuesto tp2);

-- Tipo puesto, numero, porcentaje del total ( sólo los puestos actuales). Los tipos de puesto son: FRU', 'ALI', 'CAR', 'ELE' ó 'ULT'

WITH tipoPuesto AS(SELECT c.tipo,COUNT(*) AS num FROM Puesto p,Concesion c WHERE c.fechaF IS NULL AND p.nro=c.nro GROUP BY c.tipo),maximo AS(SELECT SUM(tp.num) as total FROM tipoPuesto tp)SELECT tp.*,(tp.num *100/m.total) AS percentage FROM tipoPuesto tp,maximo m;

--Titulares (nombre) con licencias activas

SELECT t.nombre FROM Titular t,Concesion c WHERE t.dni=c.dni and c.fechaF IS NULL; 

-- Tipo de puesto, numero y porcentaje del que hay un mayor número actualmente. Los tipos de puesto son: FRU', 'ALI', 'CAR', 'ELE' ó 'ULT'.

WITH tipoPuesto AS(SELECT c.tipo,COUNT(*) AS num FROM Puesto p,Concesion c WHERE c.fechaF IS NULL AND p.nro=c.nro GROUP BY c.tipo),maximo AS(SELECT SUM(tp.num) as total FROM tipoPuesto tp)SELECT tp.*,(tp.num *100/m.total) AS percentage FROM tipoPuesto tp,maximo m WHERE tp.num >= (SELECT MAX(tp.num) as total FROM tipoPuesto tp);

-- Puesto (nro) que más tiempo ha sido una frutería ('FRU') en toda la historia del mercado

WITH s1 AS(SELECT c.nro,SUM(c.fechaF-c.fechaI) AS diff FROM Concesion c WHERE c.tipo LIKE 'FRU' AND c.fechaF IS NOT NULL GROUP BY c.nro),s2 AS (SELECT c.nro,SUM(CURRENT_DATE-c.fechaI) AS diff FROM Concesion c WHERE c.tipo LIKE 'FRU' AND c.fechaF IS NULL GROUP BY c.nro),s3 AS(SELECT * FROM s1 UNION SELECT * FROM s2),s4 AS(SELECT s.nro,SUM(s.diff) FROM s3 s GROUP BY s.nro), s5 AS (SELECT MAX(s.sum) FROM s4 s)SELECT s.* FROM s4 s,s5 ss WHERE s.sum >= ss.max;

-- Porcentaje de puestos de cada tipo (FRU', 'ALI', 'CAR', 'ELE' ó 'ULT') actuales sin contar los vacíos.

WITH tipoPuesto AS(SELECT c.tipo,COUNT(*) AS num FROM Puesto p,Concesion c WHERE c.fechaF IS NULL AND p.nro=c.nro GROUP BY c.tipo),maximo AS(SELECT SUM(tp.num) as total FROM tipoPuesto tp)SELECT tp.*,(tp.num *100/m.total) AS percentage FROM tipoPuesto tp,maximo m;

-- Tasa de sanciones por puesto (numero de sanciones / numero de puestos) por año, entre 2000 y 2010.

WITH numPuestos AS(SELECT COUNT(*) FROM Puesto),numSanciones AS(SELECT COUNT(*) FROM Sancion s WHERE EXTRACT(year FROM s.fecha) >= 2000 AND EXTRACT(year FROM s.fecha) <= 2010)SELECT (CAST(ns.count AS FLOAT)/CAST(np.count AS FLOAT)) AS tasa FROM numPuestos np,numSanciones ns;

-- Puesto que ha sido más tipos de establecimientos distintos (FRU', 'ALI', 'CAR', 'ELE' ó 'ULT') a lo largo de la historia del mercado.

WITH numTipos AS(SELECT c.nro,COUNT(DISTINCT c.tipo) FROM Concesion c GROUP BY c.nro) SELECT * FROM numTipos nt WHERE nt.count >=(SELECT MAX(nt2.count) FROM numTipos nt2);

-- Titulares(nombre) actuales que nunca en la historia del mercado han sido titulares de una frutería 'FRU'

WITH fruteros AS (SELECT DISTINCT t.nombre FROM Concesion c,Titular t WHERE c.dni=t.dni AND c.tipo LIKE 'FRU'),sel AS(SELECT t.nombre FROM Titular t WHERE t.nombre NOT IN (SELECT * FROM fruteros))SELECT s.* FROM sel s WHERE s.nombre IN (SELECT DISTINCT t.nombre FROM Titular t,Concesion c WHERE t.dni=c.dni AND c.fechaF IS NULL);

-- Historial de licencias de un puesto dado. Ej: Puesto 2

SELECT * FROM Concesion c WHERE c.nro = 2;

-- Número de licencias que ha tenido cada puesto, incluyendo las activas

SELECT p.nro,COUNT(*) FROM Puesto p,Concesion c WHERE p.nro=c.nro GROUP BY p.nro;

-- Número de licencias que ha tenido cada puesto, sin incluir las activas

SELECT p.nro,COUNT(*) FROM Puesto p,Concesion c WHERE p.nro=c.nro AND c.fechaF IS NOT NULL GROUP BY p.nro;

-- Titulares (nombre) actuales sin ninguna sanción

SELECT DISTINCT t.nombre FROM Titular t,Concesion c WHERE t.dni=c.dni AND c.cod NOT IN(SELECT cod FROM Sancion);

-- Los 5 titulares actuales (nombre) con más sanciones

WITH NumSanciones AS(SELECT t.nombre,COUNT(*) AS nsan FROM Titular t,Concesion c,Sancion s
WHERE t.dni=c.dni AND c.cod=s.cod AND c.fechaF IS NULL GROUP BY t.nombre)SELECT * FROM NumSanciones ns WHERE 5 > (SELECT COUNT(*) FROM NumSanciones ns2 WHERE ns2.nsan > ns.nsan) ORDER BY ns.nsan DESC;


-- Historial de todos los puestos(nro, fechaI, fechaF, nombre_titular) ordenado en orden descendente por número de Puesto y en orden ascendente por fechaI ( los puestos están numerados del 1 al 10)

SELECT c.nro,c.fechaI,c.fechaF,t.nombre FROM Concesion c NATURAL JOIN Titular t NATURAL JOIN Puesto p ORDER BY p.nro DESC,c.fechaI ASC;

-- Titulares con sancion en el año 2011

SELECT DISTINCT t.nombre,c.cod FROM Titular t,Concesion c,Sancion s WHERE t.dni=c.dni AND c.cod=s.cod AND EXTRACT(year FROM s.fecha) = '2011';

-- Puesto con mas sanciones

WITH NumSanciones AS(SELECT p.nro,COUNT(*) as nsan FROM Puesto p NATURAL JOIN Concesion c NATURAL JOIN Sancion s GROUP BY p.nro)SELECT * FROM NumSanciones ns WHERE ns.nsan >= (SELECT MAX(ns2.nsan) FROM NumSanciones ns2);

