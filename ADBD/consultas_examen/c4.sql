-- Número de licencias que ha tenido cada puesto, sin incluir las activas

SELECT p.nro,COUNT(*) FROM Puesto p,Concesion c WHERE p.nro=c.nro AND c.fechaF IS NOT NULL GROUP BY p.nro;

