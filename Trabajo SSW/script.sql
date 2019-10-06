DROP TABLE IF EXISTS residente;
DROP TABLE IF EXISTS vivenda;
DROP TABLE IF EXISTS atenciones;
DROP TABLE IF EXISTS profesionales;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS minusvalias;
DROP TABLE IF EXISTS usuarios;


CREATE TABLE usuarios(
	DNI varchar(255),
	nombre varchar(255) NOT NULL,
	apellido1 varchar(255) NOT NULL,
	apellido2 varchar(255),
	contraseña varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	f_nacim varchar(255) NOT NULL,
	movil varchar(255) NOT NULL,
	sexo varchar(255) NOT NULL,
	PRIMARY KEY(DNI)
);

CREATE TABLE clientes(
	idCliente varchar(255),
	direccion varchar(255) NOT NULL,
	minusvalias varchar(255),
	FOREIGN KEY(idCliente) REFERENCES usuarios(DNI),
	PRIMARY KEY(idCliente)
);

CREATE TABLE profesionales(
	idProf varchar(255),
	profesion enum('reformas','medico','atencion','comida','deporte','hobbies'),
	FOREIGN KEY(idProf) REFERENCES usuarios(DNI),
	PRIMARY KEY(idProf)
);
CREATE TABLE atenciones(
	idAtencion int AUTO_INCREMENT,
	idClienteAtencion varchar(255),
	idProfAtencion varchar(255),
	hora Datetime,
	observaciones varchar(255),
	PRIMARY KEY(idAtencion),
	FOREIGN KEY(idClienteAtencion) REFERENCES clientes(idCliente),
	FOREIGN KEY(idProfAtencion) REFERENCES profesionales(idProf)
);

INSERT INTO usuarios VALUES ('00000000Z','Enrique','Torices','Narices','enrique','enrique@cliente.es','12-06-1994','123456789','Hombre');
INSERT INTO usuarios VALUES ('45218546Q','Daniel','de Vicente','Garrote','daniel','daniel.vicente@alumnos.uva.es','12-06-1994','123456789','Hombre');
INSERT INTO usuarios VALUES ('11111111A','Benigna','Alfonso','Garrote','benigna','benigna@cliente.es','12-06-1994','123456789','Mujer');
INSERT INTO usuarios VALUES ('22222222B','Santiago','Alfonso','Garrote','santiago','santiago@cliente.es','12-06-1994','123456789','Hombre');
INSERT INTO usuarios VALUES ('33333333C','Fernando','Alonso','Correa','fernando','fernando@cliente.es','12-06-1994','123456789','Hombre');
INSERT INTO usuarios VALUES ('44444444D','Michael','Jackson','','michael','michael@cliente.es','12-06-1994','123456789','Hombre');
INSERT INTO usuarios VALUES ('84438467M','Miguel','Bayon','Sanz','miguel','miguel@trabajador.es','28-06-1994','987654321','Hombre');
INSERT INTO usuarios VALUES ('84438468M','Pepe','Viyuela','Suarez','pepe','pepe@trabajador.es','28-06-1994','987654321','Hombre');
INSERT INTO usuarios VALUES ('84438469M','Francisco','Gutierrez','Sanz','francisco','francisco@trabajador.es','28-06-1994','987654321','Hombre');
INSERT INTO usuarios VALUES ('84438460M','Alfonso','Alberto','Teniente','alfonso','alfonso@trabajador.es','28-06-1994','987654321','Hombre');
INSERT INTO usuarios VALUES ('84438461M','Alberto','Alfonso','Diaz','alberto','alberto@trabajador.es','28-06-1994','987654321','Hombre');
INSERT INTO usuarios VALUES ('84438462M','Trinidad','Gonzalez','de la Fuente','trinidad','trinidad@trabajador.es','28-06-1994','987654321','Mujer');
INSERT INTO clientes VALUES('45218546Q','Plaza mayor nº 12','ceguera');
INSERT INTO clientes VALUES('11111111A','Calle Valderabuey nº 3','sordera');
INSERT INTO clientes VALUES('22222222B','Calle San Ildefonso nº 17','cojera');
INSERT INTO clientes VALUES('33333333C','Paseo Zorrilla nº 117','TOC');
INSERT INTO clientes VALUES('44444444D','Avenida de Salamanca nº 1','ELA');
INSERT INTO profesionales VALUES('84438467M','reformas');
INSERT INTO profesionales VALUES('84438468M','medico');
INSERT INTO profesionales VALUES('84438469M','atencion');
INSERT INTO profesionales VALUES('84438460M','comida');
INSERT INTO profesionales VALUES('84438461M','deporte');
INSERT INTO profesionales VALUES('84438462M','hobbies');
INSERT INTO atenciones VALUES('1','45218546Q','84438467M','2015-06-23 18:50:22',"Sin problemas");
