--Titulares (nombre) con licencias activas

SELECT t.nombre FROM Titular t,Concesion c WHERE t.dni=c.dni and c.fechaF IS NULL; 

