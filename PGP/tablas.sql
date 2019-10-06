DROP TABLE if EXISTS PARTICIPACION;
DROP TABLE if EXISTS ESFUERZOSEMANAL;
DROP TABLE if EXISTS ACTPREDECESIORAS;
DROP TABLE if EXISTS PERSONA_ACTIVIDAD;
DROP TABLE if EXISTS ACTIVIDAD;
DROP TABLE if EXISTS PROYECTO;
DROP TABLE if EXISTS PERSONA;

CREATE TABLE PERSONA(
  dni CHAR(9),
  nombre VARCHAR(20) NOT NULL,
  apellidos VARCHAR(50) NOT NULL,
  psswd CHAR(30) NOT NULL,
  nivel INTEGER NOT NULL,
  tipo_usuario VARCHAR(20) NOT NULL,
  CONSTRAINT pk_persona PRIMARY KEY (dni)
);

CREATE TABLE PROYECTO(
  id_proyecto INTEGER NOT NULL AUTO_INCREMENT,
  dni_jefe CHAR(9),
  fecha_inicio DATE,
  fecha_fin DATE,
  desripcion VARCHAR(140),
  FOREIGN KEY fk_jefe (dni_jefe) REFERENCES PERSONA (dni)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT pk_proyecto PRIMARY KEY (id_proyecto)
);

CREATE TABLE PARTICIPACION(
  id_proyecto INTEGER NOT NULL,
  dni CHAR(9),
  porcentaje REAL,
  rol CHAR(80) NOT NULL,
  FOREIGN KEY fk_participante (dni) REFERENCES PERSONA (dni)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  FOREIGN KEY fk_proyecto (id_proyecto) REFERENCES PROYECTO (id_proyecto)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT pk_participacion PRIMARY KEY (id_proyecto, dni)
);

CREATE TABLE ACTIVIDAD(
  id_actividad INTEGER,
  id_proyecto INTEGER NOT NULL,
  descripcion VARCHAR (140),
  duracion_estimada INTEGER,
  duracion_real INTEGER,
  rol_requerido CHAR(80), --podría ser enum
  FOREIGN KEY fk_poyecto (id_proyecto) REFERENCES PROYECTO (id_proyecto)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT pk_actividad PRIMARY KEY (id_actividad, id_proyecto)
);

CREATE TABLE ACTPREDECESORAS(
  id_anterior INTEGER NOT NULL,
  id_nueva INTEGER NOT NULL,
  id_proyecto INTEGER NOT NULL,
  FOREIGN KEY fk_act_nueva (id_anterior, id_proyecto) REFERENCES ACTIVIDAD (id_actividad, id_proyecto)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  FOREIGN KEY fk_act_anterior (id_nueva, id_proyecto) REFERENCES ACTIVIDAD (id_actividad, id_proyecto) 
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT pk_actpredecesoras PRIMARY KEY (id_anterior,id_nueva, id_proyecto)
);

CREATE TABLE PERSONA_ACTIVIDAD(
  dni CHAR(9),
  id_actividad INTEGER NOT NULL,
  id_proyecto INTEGER NOT NULL,
  FOREIGN KEY fk_actividad (id_actividad, id_proyecto) REFERENCES ACTIVIDAD (id_actividad, id_proyecto)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  FOREIGN KEY fk_dni (dni) REFERENCES PERSONA (dni)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT pk_persona_actividad PRIMARY KEY (dni, id_actividad, id_proyecto)
);

CREATE TABLE ESFUERZOSEMANAL(
  num_semana INTEGER NOT NULL,
  dni CHAR(9),
  id_actividad INTEGER NOT NULL,
  id_proyecto INTEGER NOT NULL,
  comentario CHAR(140),
  horas_invertidas INTEGER,
  aprobado BOOLEAN DEFAULT false NOT NULL,
  FOREIGN KEY fk_persona_actividad (dni, id_actividad, id_proyecto) REFERENCES PERSONA_ACTIVIDAD (dni, id_actividad, id_proyecto)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT pk_esfuerzosemanal PRIMARY KEY (num_semana, dni, id_actividad, id_proyecto)
);


INSERT INTO PERSONA VALUES ('00000000a','admin','admin','000',0,'Administrador');
INSERT INTO PERSONA VALUES ('11111111b','usuario1','uno','111',1,'Jefe Proyecto');
INSERT INTO PERSONA VALUES ('22222222c','usuario2','dos','222',1,'Jefe Proyecto');
INSERT INTO PERSONA VALUES ('33333333d','usuario3','tres','333',2,'Desarrollador');
INSERT INTO PERSONA VALUES ('44444444e','usuario4','cuatro','444',2,'Desarrollador');
INSERT INTO PERSONA VALUES ('55555555d','usuario5','cinco','555',3,'Desarrollador');

INSERT INTO PROYECTO (dni_jefe,fecha_inicio,desripcion) VALUES ('11111111b','2018-11-05','Proyecto numero uno.');
INSERT INTO PROYECTO (dni_jefe,fecha_inicio,desripcion) VALUES ('22222222c','2018-11-26','Proyecto numero dos.');

INSERT INTO PARTICIPACION VALUES (1,'33333333d',50,'Diseñador');
INSERT INTO PARTICIPACION VALUES (1,'44444444e',50,'Diseñador');
INSERT INTO PARTICIPACION VALUES (2,'33333333d',50,'Diseñador');
INSERT INTO PARTICIPACION VALUES (2,'44444444e',25,'Analista-programador');
INSERT INTO PARTICIPACION VALUES (2,'55555555d',25,'Responsable pruebas');

INSERT INTO ACTIVIDAD (id_actividad, id_proyecto, descripcion, duracion_estimada, rol_requerido) VALUES (1, 1,'Actividad uno del proyecto uno.',10,'Analista');
INSERT INTO ACTIVIDAD (id_actividad, id_proyecto, descripcion, duracion_estimada, rol_requerido) VALUES (2, 1,'Actividad dos del proyecto uno.',11,'Diseñador');
INSERT INTO ACTIVIDAD (id_actividad, id_proyecto, descripcion, duracion_estimada, rol_requerido) VALUES (1, 2,'Actividad uno del proyecto dos.',10,'Responsable equipo de pruebas');
INSERT INTO ACTIVIDAD (id_actividad, id_proyecto, descripcion, duracion_estimada, rol_requerido) VALUES (2, 2,'Actividad dos del proyecto dos.',11,'Probador');

INSERT INTO ACTPREDECESORAS VALUES (1,2,1);

INSERT INTO PERSONA_ACTIVIDAD VALUES ('33333333d',1,1);
INSERT INTO PERSONA_ACTIVIDAD VALUES ('44444444e',2,1);

INSERT INTO ESFUERZOSEMANAL (num_semana, dni, id_actividad, id_proyecto, comentario, horas_invertidas) VALUES (1,'33333333d',1,1,'comentario',8);
INSERT INTO ESFUERZOSEMANAL (num_semana, dni, id_actividad, id_proyecto, comentario, horas_invertidas) VALUES (2,'33333333d',1,1,'comentario',8);
INSERT INTO ESFUERZOSEMANAL (num_semana, dni, id_actividad, id_proyecto, comentario, horas_invertidas) VALUES (2,'44444444e',2,1,'comentario',10);

