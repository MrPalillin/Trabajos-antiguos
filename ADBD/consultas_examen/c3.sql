-- Número de licencias que ha tenido cada puesto, incluyendo las activas

SELECT p.nro,COUNT(*) FROM Puesto p,Concesion c WHERE p.nro=c.nro GROUP BY p.nro;

