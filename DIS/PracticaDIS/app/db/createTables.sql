--Derby does not support DROP TABLE IF EXISTS VENTA

DROP TABLE OPERACIONSOBREPEDIDODEHORNO;
DROP TABLE ESTADODEPEDIDODEHORNO;
DROP TABLE LINEAVENTAPARAPEDIDO;
DROP TABLE LINEADEPEDIDODEHORNO;
DROP TABLE PEDIDODEHORNO;
DROP TABLE CLIENTE;

DROP TABLE LINEADEVENTA;
DROP TABLE VENTA;

DROP TABLE FACTURA;
DROP TABLE TRANSFERENCIA;

DROP TABLE LINEADEPEDIDOAPROVEEDOR;
DROP TABLE PEDIDOAPROVEEDOR;
DROP TABLE PROVEEDOR;

DROP TABLE USOENPASOSDERECETA;
DROP TABLE PRODUCTO;

DROP TABLE DISPONIBILIDADEMPLEADO;
DROP TABLE VINCULACIONCONLAEMPRESA;
DROP TABLE ROLESENEMPRESA;
DROP TABLE EMPLEADO;

DROP TABLE TIPODEDISPONIBILIDAD;
DROP TABLE TIPODEVINCULACION;
DROP TABLE TIPODEROL;

-- Enum
create table TIPODEROL
(
    IdTipo SMALLINT not null,
    nombreTipo VARCHAR(20) not null,
        PRIMARY KEY(IdTipo)
);

INSERT INTO TIPODEROL
VALUES  (1,'Supervisor'),
        (2,'Encargado'),
        (3,'EmpleadoDeHorno'),
        (4,'Dependiente');

-- Enum
create table TIPODEVINCULACION
(
    IdTipo SMALLINT not null,
    NombreTipo VARCHAR(20) not null unique,
        PRIMARY KEY(IdTipo)

);

INSERT INTO TIPODEVINCULACION
VALUES  (1,'Contratado'),
        (2,'Despedido'),
        (3,'EnERTE');

-- Enum
create table TIPODEDISPONIBILIDAD
(
    IdTipo SMALLINT not null,
    NombreTipo VARCHAR(20) not null unique,
        PRIMARY KEY(IdTipo)
);

INSERT INTO TIPODEDISPONIBILIDAD
VALUES  (1,'Vacaciones'),
        (2,'BajaTemporal'),
    (3, 'Trabajando');

-- Entity
create table EMPLEADO
(
    Nif VARCHAR(9) not null,
    Password VARCHAR(65) not null,
    Nombre VARCHAR(20) not null,
    Apellidos VARCHAR(30) not null,
    FechaInicioEnEmpresa DATE not null,
        PRIMARY KEY(Nif)
);

-- Association
create table ROLESENEMPRESA
(
    ComienzoEnRol DATE not null,
    Empleado VARCHAR(9) not null,
    Rol SMALLINT not null,
            FOREIGN KEY(Empleado) REFERENCES EMPLEADO(Nif),
            FOREIGN KEY(Rol) REFERENCES TIPODEROL(IdTipo)
);

-- Association
create table VINCULACIONCONLAEMPRESA
(
    inicio DATE not null,
    Empleado VARCHAR(9) not null,
    Vinculo SMALLINT not null,
        FOREIGN KEY(Empleado) REFERENCES EMPLEADO(Nif),
        FOREIGN KEY(Vinculo) REFERENCES TIPODEVINCULACION(IdTipo) 
);

-- Association
create table DISPONIBILIDADEMPLEADO
(
    Comienzo DATE not null,
    FinalPrevisto DATE,
    Empleado VARCHAR(9) not null,
    Disponibilidad SMALLINT not null,
        FOREIGN KEY(Empleado) REFERENCES EMPLEADO(Nif),
        FOREIGN KEY(Disponibilidad) REFERENCES TIPODEDISPONIBILIDAD(IdTipo)
);

-- Entity
create table PRODUCTO
(
    Codigo VARCHAR(20) not null,
    Nombre VARCHAR(50) not null,
    Descripcion VARCHAR(200) not null,
    Existencias SMALLINT not null,
    CantidadMinimaEnStock SMALLINT not null, 
    Subtipo VARCHAR(50) not null, -- {subtipo=="MateriaPrima" o subtipo=="ProductoDeHorno"}
    Precio FLOAT not null, -- {para Vendibles es precio de venta, para Comprables precio de compra}
    DiasParaEntregaDelProveedor SMALLINT, -- {not null para Comprables, null para Vendibles}
    TipoDeMateriaPrima VARCHAR(50), --{not null para subtipo=="MateriaPrima", null para subtipo!="MateriaPrima"}
        PRIMARY KEY(Codigo)
);

-- Association
create table USOENPASOSDERECETA
(
    Cantidad SMALLINT,
    MateriaPrima VARCHAR(20),
    ProductoDeHorno VARCHAR(20),
        FOREIGN KEY (MateriaPrima) REFERENCES PRODUCTO(Codigo),
        FOREIGN KEY (ProductoDeHorno) REFERENCES PRODUCTO(Codigo)
);

-- Entity
create table PROVEEDOR
(
    Cif VARCHAR(9) not null,
    Nombre VARCHAR(50) not null,
    Telefono VARCHAR(15) not null,
    Email VARCHAR(50) not null,
        PRIMARY KEY(Cif)
);

-- Entity
create table PEDIDOAPROVEEDOR
(
    NumeroDePedido INTEGER not null,
    FechaDeRealizacion DATE not null,
    EstaPendiente VARCHAR(1) not null,
    Proveedor VARCHAR(9) not null,
        PRIMARY KEY(NumeroDePedido),
        FOREIGN KEY(Proveedor) REFERENCES PROVEEDOR(Cif)
);

-- Entity
create table LINEADEPEDIDOAPROVEEDOR
(
    Cantidad SMALLINT not null,
    Pedido INTEGER not null,
    Producto VARCHAR(20) not null,
        FOREIGN KEY(Pedido) REFERENCES PEDIDOAPROVEEDOR(NumeroDePedido),
        FOREIGN KEY(Producto) REFERENCES PRODUCTO(Codigo)
);

-- Entity 
create table TRANSFERENCIA
(
    Id INTEGER not null,
    Importe FLOAT not null,
    FechaDeRealizacion DATE not null,
    Comprobada VARCHAR(1) not null,
    Administrativo VARCHAR(9) not null,
        PRIMARY KEY(Id),
        FOREIGN KEY(Administrativo) REFERENCES EMPLEADO(Nif)
);

-- Entity
create table FACTURA
(
    Id INTEGER not null,
    FechaDeEmision DATE not null,
    Importe FLOAT not null,
    CuentaBancaria VARCHAR(34) not null,
    Pedido INTEGER not null,
    EnTransferencia INTEGER, --null si no se ha realizado transferencia
        PRIMARY KEY(Id),
        FOREIGN KEY(EnTransferencia) REFERENCES TRANSFERENCIA(Id),
        FOREIGN KEY(Pedido) REFERENCES PEDIDOAPROVEEDOR(NumeroDePedido)
);

-- Entity
create table VENTA
(
    IdDeVenta INTEGER not null,
    FechaDeVenta DATE not null,
    Dependiente VARCHAR(9) not null,
        PRIMARY KEY(IdDeVenta),
        FOREIGN KEY(Dependiente) REFERENCES EMPLEADO(Nif)

);

-- Entity
create table LINEADEVENTA
(
    Cantidad SMALLINT not null,
    Venta INTEGER not null,
    Producto VARCHAR(20) not null,
        FOREIGN KEY(Venta) REFERENCES VENTA(IdDeVenta),
        FOREIGN KEY(Producto) REFERENCES PRODUCTO(Codigo)
);

-- Entity
create table CLIENTE 
(
    Nif VARCHAR(9) not null,
    Nombre VARCHAR(20) not null,
    Apellidos VARCHAR(30) not null,
    Telefono INTEGER,
    Email VARCHAR(50),
        PRIMARY KEY(Nif)
);

-- Entity
create table PEDIDODEHORNO
(
    NumeroDePedido INTEGER not null,
    FechaEnLaQueSeQuiere DATE not null,
    Cliente VARCHAR(9) not null,
    Dependiente VARCHAR(9) not null,
        PRIMARY KEY(NumeroDePedido),
        FOREIGN KEY(Cliente) REFERENCES CLIENTE(Nif),
        FOREIGN KEY(Dependiente) REFERENCES EMPLEADO(Nif)
);

-- Entity
create table LINEADEPEDIDODEHORNO
(
    Cantidad SMALLINT not null,
    Pedido INTEGER not null,
    Producto VARCHAR(20) not null,
        FOREIGN KEY(Pedido) REFERENCES PEDIDODEHORNO(NumeroDePedido),
        FOREIGN KEY(Producto) REFERENCES PRODUCTO(Codigo)
);

-- Association
create table LINEAVENTAPARAPEDIDO
(
    Venta INTEGER not null,
    Pedido INTEGER not null,
        FOREIGN KEY(Venta) REFERENCES VENTA(IdDeVenta),
        FOREIGN KEY(Pedido) REFERENCES PEDIDODEHORNO(NumeroDePedido)
    
);

-- Enum
create table ESTADODEPEDIDODEHORNO
(
    IdTipo SMALLINT not null,
    nombreTipo VARCHAR(20) not null,
        PRIMARY KEY(IdTipo)
);

INSERT INTO ESTADODEPEDIDODEHORNO
VALUES  (1,'Registrado'),
        (2,'Preparando'),
        (3,'Terminado'),
        (4,'Entregado');

-- Entity
create table OPERACIONSOBREPEDIDODEHORNO 
(
    Momento TIMESTAMP not null,
    Tipo SMALLINT not null,
    Empleado VARCHAR(9) not null,
    PedidoDeHorno INTEGER not null,
        PRIMARY KEY(Momento),
        FOREIGN KEY(Tipo) REFERENCES ESTADODEPEDIDODEHORNO(IdTipo),
        FOREIGN KEY(Empleado) REFERENCES EMPLEADO(Nif),
        FOREIGN KEY(PedidoDeHorno) REFERENCES PEDIDODEHORNO(NumeroDePedido)
);







---------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------
-- DATOS

-- Las contraseñas son 'segura'
INSERT INTO EMPLEADO 

VALUES ('12345678Z', '22bed3883ae12120caccff1d3e18343f2db878016fb5e54dcb8dab72df6f1dd6', 'Hermenegildo Manuel', 'Ruipérez Núñez','2014-02-25'),
       ('12123434A', '22bed3883ae12120caccff1d3e18343f2db878016fb5e54dcb8dab72df6f1dd6', 'Segura Patricio', 'Mian', '2018-12-10'),
       ('98765432E', '22bed3883ae12120caccff1d3e18343f2db878016fb5e54dcb8dab72df6f1dd6', 'Julian Jose', 'Marcos', '2014-02-25'),
       ('22213434B', '22bed3883ae12120caccff1d3e18343f2db878016fb5e54dcb8dab72df6f1dd6', 'Martinez Martinez', 'Jose Carlos', '2019-05-05'),
       ('12345679J', '22bed3883ae12120caccff1d3e18343f2db878016fb5e54dcb8dab72df6f1dd6', 'García Forca', 'Fede', '2019-05-28');


INSERT INTO ROLESENEMPRESA VALUES ('2018-09-10', '12123434A', 4),
                                  ('2018-12-10', '12123434A', 2),
                                  ('2014-02-25', '98765432E', 1),
                                  ('2015-04-14', '98765432E', 4),
                                  ('2014-02-25', '12345678Z', 2),
                                  ('2015-04-14', '12345678Z', 3),
                                  ('2019-05-05', '22213434B', 1);

INSERT INTO VINCULACIONCONLAEMPRESA VALUES ('2014-02-25', '12345678Z', 1),
                                           ('2014-02-25', '98765432E', 1),
                                           ('2018-12-10', '12123434A', 1),
                                           ('2019-05-05', '22213434B', 1),
                                           ('2019-05-28', '22213434B', 1),
                                           ('2019-05-29', '22213434B', 2);

INSERT INTO DISPONIBILIDADEMPLEADO
VALUES ('2014-02-25', NULL,         '12345678Z', 3),
       ('2014-06-23', '2014-08-29', '12345678Z', 1),
       ('2014-08-29', NULL,         '12345678Z', 3),
       ('2014-11-05', '2015-02-05', '12345678Z', 2),
       ('2015-02-12', NULL,         '12345678Z', 3),

       ('2018-09-10', NULL,         '12123434A', 3),
       ('2018-11-10', '2018-12-01', '12123434A', 1),
       ('2018-12-02', NULL,         '12123434A', 3),

       ('2014-02-25', NULL,         '98765432E', 3),
       ('2016-02-25', '2016-03-25', '98765432E', 2),
       ('2016-03-27', NULL,         '98765432E', 3),

       ('2019-05-05', NULL,         '22213434B', 3),
       ('2019-05-29', '2019-08-20', '22213434B', 2),

       ('2019-05-28', NULL, '12345679J', 3);

INSERT INTO PROVEEDOR
VALUES  ('15264859N', 'Panusa', '942335708', 'comercial@panusa.es'),
        ('35246857R', 'Ogiberri', '943721983', 'comercial@ogiberri.es'),
        ('56468656T', 'ReposteriaPrueba', '946888666', 'comercial@reposteriaprueba.es');


INSERT INTO PEDIDOAPROVEEDOR
VALUES  (1, '2015-05-13', 'N', '15264859N'),
        (2, '2017-12-22', 'N', '15264859N'),
        (3, '2019-04-14', 'N', '15264859N'),
        (4, '2018-12-20', 'N', '56468656T'),
        (5, '2019-01-20', 'N', '56468656T'),
        (6, '2019-04-22', 'N', '35246857R'),
        (7, '2019-05-28', 'N', '35246857R');

INSERT INTO TRANSFERENCIA VALUES(1, 50.01,'2018-12-20','1','12123434A'),
                                (2, 122.21,'2018-01-23','1','12123434A'),
                                (3, 70.12,'2018-04-23','1','12123434A'),
                                (4, 88.12,'2019-05-28','0','12123434A');

INSERT INTO FACTURA
VALUES  (1, '2015-05-14', 123.45, 'ES-...', 1, NULL),
        (2, '2017-12-25', 666.66, 'ES-...', 2, NULL),
        (3, '2019-04-14', 96.69, 'ES-...', 3, NULL),
        (4, '2018-12-20', 50.01, 'ES-...', 4, 1),
        (5, '2019-01-22', 122.01, 'ES-...', 5, 1),
        (6, '2019-04-22', 70.12, 'ES-...', 6, 1),
        (7, '2019-05-28', 88.12, 'ES-...', 7, 1);

INSERT INTO PRODUCTO VALUES('85584985','p1','descripcion',5,2,'ProductoDeHorno',19.50,5,null),
                           ('32005867','p2','descripcion',2,1,'ProductoDeHorno',12.50,5,null),
                           ('98718416','p3','descripcion',0,2,'ProductoDeHorno',9.25,5,null);

INSERT INTO CLIENTE VALUES('75264259Q','Juan Jose','Martinez Martinez',971252152,'juan@cliente.es'),
                          ('65264100S','Pedro','Durán Durán',971252152,'pedroDuran@cliente.es'),
                          ('89264210Y','Carlos','Medrán Solo',981252352,'carlosMedrán@cliente.es');


INSERT INTO PEDIDODEHORNO VALUES(1,'2019-05-27','75264259Q','12345678Z'),
                                (2,'2019-05-28','65264100S','12345678Z'),
                                (3,'2019-05-28','65264100S','12345678Z'),
                                (4,'2019-05-29','75264259Q','98765432E'),
                                (5, CURRENT_DATE,'65264100S','12345678Z');
                               /* (6, CURRENT_DATE,'89264210Y','98765432E');*/



INSERT INTO LINEADEPEDIDODEHORNO VALUES(2,1,'85584985'),
                                       (1,2,'32005867'),
                                       (4,3,'98718416'),
                                       (9,4,'98718416'),
                                       (8,5,'98718416'),
                                       (2,5,'32005867');
                                    /*   (7,6,'85584985');
*/

INSERT INTO OPERACIONSOBREPEDIDODEHORNO VALUES ('2019-05-27 14:55:38', 1, '12345678Z', 1),
                                       ('2019-05-27 14:55:40', 2, '12345678Z', 2),
                                       ('2019-05-27 14:55:42', 1, '12345678Z', 3),
                                       ('2019-05-27 14:55:44', 1, '98765432E', 4),
                                       (CURRENT_TIMESTAMP, 1, '12345678Z', 5);
                                       /*(CURRENT_TIMESTAMP, 1, '98765432E', 6);*/
                                        