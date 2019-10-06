drop table Sancion;
drop table Falta;
drop table TipoSancion;
drop table CausaFalta;
drop table Contrato;
drop table Vendible;
drop table Producto;
drop table DiasSinAbrir;
drop table Licencia;
drop table CausaExtincion;
drop table TipoPuesto;
drop table Auxiliar;
drop table Tipo_Auxiliar;
drop table Encargado;
drop table Trabajador;
drop table Familia;
drop table Tipo_Familiar;
drop table Titular;
drop table Persona;
drop table Puesto;
drop table Mercado;

create table Mercado(
    id_mercado int UNIQUE NOT NULL,
    nombre varchar(255) NOT NULL,
    primary key (id_mercado)
);

create table Puesto(
	numero_de_puesto int NOT NULL,
	id_mercado int NOT NULL,
	emplazamiento varchar(255) NOT NULL,
	dimension int NOT NULL,
	primary key (numero_de_puesto,id_mercado),
	foreign key (id_mercado) references Mercado (id_mercado)
);

create table Persona(
    dni varchar(11) UNIQUE NOT NULL,
    f_nacimiento date NOT NULL,
    numero_seguridad_social varchar(14) NOT NULL,
    nombre varchar(50) NOT NULL,
    ap1 varchar(50) NOT NULL,
    ap2 varchar(50),
    primary key (dni)
);

create table Titular(
    dni_titular varchar(11) UNIQUE NOT NULL,
    foreign key (dni_titular) references Persona (dni)
);

create table Tipo_Familiar(
    tipo int UNIQUE NOT NULL,
    nombre_tipo varchar(255) NOT NULL,
    primary key (tipo)
);

create table Familia(
	dni_titular varchar(11) NOT NULL,
	dni_familiar varchar(11) NOT NULL,
	tipo_familiar int NOT NULL,
	primary key (dni_titular,dni_familiar),
	foreign key (dni_titular) references Titular (dni_titular),
	foreign key (dni_familiar) references Persona (dni),
	foreign key (tipo_familiar) references Tipo_Familiar (tipo)
);

create table Trabajador(
    dni varchar(11) UNIQUE NOT NULL,
    tarjeta_manip_alim varchar(11),
    foreign key (dni) references Persona (dni)
);

create table Encargado(
	dni varchar(11) UNIQUE NOT NULL,
	foreign key (dni) references Persona (dni)
);

create table Tipo_Auxiliar(
	tipo int NOT NULL,
	nombreTipoAuxiliar varchar(50) NOT NULL,
	primary key (tipo)
);

create table Auxiliar(
	dni varchar(11) UNIQUE NOT NULL,
	tipo int NOT NULL,
	dni_encargado varchar(11) NOT NULL,
	primary key (dni),
	foreign key (dni) references Persona (dni),
	foreign key (dni_encargado) references Encargado (dni),
	foreign key (tipo) references Tipo_Auxiliar (tipo)
);

create table TipoPuesto(
    id_TipoPuesto int UNIQUE NOT NULL,
    nombreTipoPuesto varchar(100) NOT NULL,
    primary key (id_TipoPuesto)
);

create table CausaExtincion(
    id_CausaExtincion int UNIQUE NOT NULL,
    nombreCausaExtincion varchar(100) NOT NULL,
    primary key (id_CausaExtincion)
);

create table Licencia(
    id_licencia int UNIQUE NOT NULL,
    id_puesto int NOT NULL,
    id_mercado int NOT NULL,
    dni_titular varchar(11) NOT NULL,
    inicio_licencia date NOT NULL,
    fin_licencia date NOT NULL,
    causa_extincion int DEFAULT NULL,
    tipo int NOT NULL,
    obras int NOT NULL,
    fecha_ultimo_pago_canon date NOT NULL,
    gastos float DEFAULT 0.0,
    fecha_ultimo_pago_gastos date NOT NULL,
    heredado int NOT NULL,
    primary key(id_licencia),
    foreign key (id_puesto, id_mercado) references Puesto (numero_de_puesto,id_mercado),
    foreign key (dni_titular) references Titular (dni_titular),
    foreign key (tipo) references TipoPuesto (id_TipoPuesto),
    foreign key (causa_extincion) references CausaExtincion (id_CausaExtincion),
    constraint fecha_correcta
	check (fin_licencia > inicio_licencia),
    constraint gastos_positivos
	check (gastos >= 0.0)
);

create table DiasSinAbrir(
	id_licencia int NOT NULL,
	fecha_inicio Date NOT NULL,
	dias_sin_abrir int NOT NULL DEFAULT 1,
	autorizado int NOT NULL,
	primary key (id_licencia,fecha_inicio),
	foreign key (id_licencia) references Licencia (id_licencia)
);

create table Producto(
	nombre_prod varchar(255) UNIQUE NOT NULL,
	refrigeracion int NOT NULL,
	primary key (nombre_prod)
);

create table Vendible(
	precio float NOT NULL,
	nombre_producto varchar(255) NOT NULL,
	id_licencia int NOT NULL,
	primary key (nombre_producto, id_licencia),
	foreign key (nombre_producto) references Producto (nombre_prod),
	foreign key (id_licencia) references Licencia (id_licencia)
);

create table Contrato(
    id_licencia int NOT NULL,
    dni_trabajador varchar(11) NOT NULL,
    fecha_inicio date NOT NULL,
    fecha_fin date NOT NULL,
    primary key (id_licencia, dni_trabajador, fecha_inicio),
    foreign key (id_licencia) references Licencia (id_licencia),
    foreign key (dni_trabajador) references Trabajador (dni)
);

create table CausaFalta(
    id_CausaFalta int UNIQUE NOT NULL,
    nombreCausaFalta varchar (100) NOT NULL,
    primary key (id_CausaFalta)
);

create table TipoSancion(
    id_TipoSancion int UNIQUE NOT NULL,
    nombreTipoSancion varchar(100) NOT NULL,
    primary key (id_TipoSancion)
);

create table Falta(
    id_falta int UNIQUE NOT NULL,
    id_licencia int NOT NULL,
    gravedad int NOT NULL,
    fecha_hora timestamp NOT NULL,
    causa int NOT NULL,
    primary key (id_falta),
    foreign key (id_licencia) references Licencia (id_licencia),
    foreign key (causa) references CausaFalta(id_CausaFalta),
    constraint tipos_gravedad
	check (gravedad >= 1 and gravedad <= 3)
);

create table Sancion(
    id_sancion int UNIQUE NOT NULL,
    tipo_sancion int NOT NULL,
    cuantia float NOT NULL,
    id_falta int NOT NULL,
    primary key(id_sancion),
    foreign key(id_falta) references Falta(id_falta),
    foreign key(tipo_sancion) references TipoSancion (id_TipoSancion)
);

insert into Mercado values (1, 'Val');
insert into Mercado values (2, 'Abasto');

insert into Puesto values (1, 1, 'Mercado del Val', 64);
insert into Puesto values (2, 1, 'Plaza España', 40);
insert into Puesto values (1, 2, 'Mercadillo', 38);
insert into Puesto values (2, 2, 'Mercadillo', 56);
insert into Puesto values (3, 2, 'Plaza Mayor', 74);

insert into Persona values ('45821687L', '1948-09-05', '16J78D526F14', 'Felipe', 'Alonso', 'Rodríguez');
insert into Persona values ('24865378K', '1968-11-11', '254F68H123Y4', 'Rodrigo', 'Peña', 'Nieto');
insert into Persona values ('57843697L', '1991-01-13', '1725638FH21D', 'Gonzalo', 'Enríquez', 'Vicente');
insert into Persona values ('36852144J', '1978-08-28', '245GF36JKL98', 'Daniel', 'Pérez', 'Benito');
insert into Persona values ('24568754F', '1936-09-29', '24D36H75T36D', 'Guillermo', 'Bernardo', 'Anta');
insert into Persona values ('36784215D', '1966-06-06', '244FDS6358FD', 'Lorena', 'Gonzalez', 'Bayón');
insert into Persona values ('87546988D', '1990-11-15', '1257D6SS34U7', 'Purificación', 'García', 'Alonso');
insert into Persona values ('17535634D', '1990-11-15', '17852398ASDF', 'Felipe', 'Pérez', 'Benito');
insert into Persona values ('18873654Y', '1987-07-30', '125DF635SD78', 'Gonzalo', 'Enríquez', 'Vicente');
insert into Persona values ('18392746D', '1954-10-20', '154DS63IU4H4', 'Antonio', 'Iglesias', 'Galán');
insert into Persona values ('78963369D', '1972-02-02', '25DST76DH354', 'Mario', 'Bartolomé', 'Zapatero');
insert into Persona values ('87564893K', '1971-02-17', '27FD68IOP524', 'Ana', 'Blasco', 'Prieto');
insert into Persona values ('25486798G', '1957-12-29', '8SF69G14J34U', 'Eduardo', 'Lopez', 'Vega');
insert into Persona values ('13879564Q', '1971-10-10', '97F5H3J15U97', 'Guillermo', 'Duero', 'Peña');
insert into Persona values ('64879513K', '1980-04-13', '8D524H879T37', 'Andrea', 'Pérez', 'Antilla');

insert into Titular values ('36852144J');
insert into Titular values ('17535634D');
insert into Titular values ('57843697L');
insert into Titular values ('36784215D');
insert into Titular values ('18392746D');
insert into Titular values ('87546988D');

insert into Tipo_Familiar values (1, 'Cónyuge');
insert into Tipo_Familiar values (2, 'Hijo o hija');

insert into Familia values ('36852144J', '17535634D', 2);

insert into Trabajador values ('45821687L', '24867515D');
insert into Trabajador values ('24865378K', '24867159Y');
insert into Trabajador values ('57843697L', '34875496K');
insert into Trabajador values ('36852144J', '32575698D');
insert into Trabajador values ('24568754F', '23657896D');
insert into Trabajador values ('36784215D', '12785362D');
insert into Trabajador values ('87546988D', '12478635D');
insert into Trabajador values ('17535634D', '84657954I');
insert into Trabajador values ('18873654Y', '24867515D');
insert into Trabajador values ('18392746D', '17474123L');
insert into Trabajador values ('78963369D', '85220347D');
insert into Trabajador values ('87564893K', '78521476U');

insert into Encargado values ('25486798G');

insert into Tipo_Auxiliar values (1, 'Limpieza');
insert into Tipo_Auxiliar values (2, 'Vigilancia');

insert into Auxiliar values ('13879564Q', 1, '25486798G');
insert into Auxiliar values ('64879513K', 2, '25486798G');

insert into TipoPuesto values (1, 'Pescaderia');
insert into TipoPuesto values (2, 'Carniceria');
insert into TipoPuesto values (3, 'Frutas_y_verduras');
insert into TipoPuesto values (4, 'Minorista_polivalente_de_alimentación');
insert into TipoPuesto values (5, 'Panaderia');
insert into TipoPuesto values (6, 'Bar');
insert into TipoPuesto values (7, 'Otros');
insert into TipoPuesto values (8, 'Almacen');

insert into CausaExtincion values (1, 'Renuncia');
insert into CausaExtincion values (2, 'Quiebra');
insert into CausaExtincion values (3, 'Causas_de_interes_publico');
insert into CausaExtincion values (4, 'Fallecimiento');
insert into CausaExtincion values (5, 'Disolución_sociedad');
insert into CausaExtincion values (6, 'Cesion_ilegal');
insert into CausaExtincion values (7, 'Perdida_requisitos');
insert into CausaExtincion values (8, 'Cierre_prolongado');
insert into CausaExtincion values (9, 'Cierre_mercado');
insert into CausaExtincion values (10, 'Falta_sanitaria');
insert into CausaExtincion values (11, 'Falta_pago_canon');

insert into Licencia values (56789, 1, 1, '36852144J', '2014-01-01', '2016-03-02',    4, 1, 0, '2015-11-01',  13.00, '2015-02-05', 0);
insert into Licencia values (56790, 1, 1, '17535634D', '2016-03-02', '2024-01-01', NULL, 1, 1, '2018-11-27',   0.00, '2018-11-27', 1);
insert into Licencia values (60023, 2, 1, '57843697L', '2018-03-02', '2028-03-02', NULL, 3, 1, '2018-11-01',  80.00, '2018-04-05', 0);
insert into Licencia values (20034, 1, 2, '36784215D', '2018-03-01', '2028-03-01', NULL, 2, 1, '2018-11-01',   0.00, '2018-02-12', 0);
insert into Licencia values (12204, 2, 2, '18392746D', '2008-06-05', '2018-06-05', NULL, 4, 0, '2018-06-05',  80.24, '2018-06-07', 0);
insert into Licencia values (12203, 3, 2, '18392746D', '2018-06-05', '2028-04-03', NULL, 4, 0, '2018-06-05',   0.00, '2018-05-11', 0);
insert into Licencia values (54732, 1, 1, '87546988D', '2001-03-12', '2005-08-21',    2, 5, 0, '2005-07-15', 180.24, '2005-07-14', 0);

insert into DiasSinAbrir values (56790, '2017-05-02', 3, 1);
insert into DiasSinAbrir values (56790, '2017-05-05', 1, 0);
insert into DiasSinAbrir values (12204, '2010-12-01', 2, 0);
insert into DiasSinAbrir values (12204, '2011-02-21', 3, 0);

insert into Producto values('Salmón', 1);
insert into Producto values('Lenguado', 1);
insert into Producto values('Filete de ternera', 1);
insert into Producto values('Solomillo', 1);
insert into Producto values('Pan clasico', 0);
insert into Producto values('Baguette', 0);
insert into Producto values('Lechuga', 1);
insert into Producto values('Tomate', 1);
insert into Producto values('Plátano', 0);
insert into Producto values('Manzana', 0);

insert into Vendible values(7.51, 'Salmón', 56789);
insert into Vendible values(6.87, 'Lenguado', 56789);
insert into Vendible values(7.06, 'Salmón', 56790);
insert into Vendible values(6.42, 'Lenguado', 56790);
insert into Vendible values(1.89, 'Lechuga', 60023);
insert into Vendible values(2.24, 'Tomate', 60023);
insert into Vendible values(1.15, 'Plátano', 60023);
insert into Vendible values(1.18, 'Manzana', 60023);
insert into Vendible values(6.87, 'Filete de ternera', 20034);
insert into Vendible values(8.56, 'Solomillo', 20034);
insert into Vendible values(2.32, 'Tomate', 12204);
insert into Vendible values(1.14, 'Plátano', 12204);
insert into Vendible values(1.20, 'Manzana', 12204);
insert into Vendible values(1.72, 'Lechuga', 12203);
insert into Vendible values(2.35, 'Tomate', 12203);
insert into Vendible values(1.19, 'Plátano', 12203);
insert into Vendible values(1.10, 'Manzana', 12203);
insert into Vendible values(1.07, 'Pan clasico', 54732);
insert into Vendible values(1.00, 'Baguette', 54732);

insert into Contrato values(56790, '17535634D','2016-05-02','2024-01-01');
insert into Contrato values(60023, '57843697L','2018-03-04','2028-03-02');
insert into Contrato values(20034, '36784215D','2015-01-10','2016-01-10');
insert into Contrato values(12203, '18392746D','2019-04-26','2028-04-03');
insert into Contrato values(56790, '78963369D','2016-05-02','2024-01-10');
insert into Contrato values(56790, '24568754F','2016-05-02','2024-01-10');
insert into Contrato values(56790, '36852144J','2016-05-02','2024-01-20');
insert into Contrato values(60023, '87546988D','2018-06-20','2028-03-02');
insert into Contrato values(20034, '18873654Y','2018-03-01','2018-09-01');
insert into Contrato values(12203, '45821687L','2018-12-01','2026-04-03');
insert into Contrato values(20034, '87564893K','2018-03-01','2019-05-01');
insert into Contrato values(56790, '24865378K','2016-03-02','2024-01-01');

insert into CausaFalta values (1, 'Discusion');
insert into CausaFalta values (2, 'Negligencia_aseo');
insert into CausaFalta values (3, 'Incumplimiento_instrucciones');
insert into CausaFalta values (4, 'Ensuciar');
insert into CausaFalta values (5, 'Incumplimiento_enfermedad');
insert into CausaFalta values (6, 'Otros');
insert into CausaFalta values (7, 'Reiteracion_leves');
insert into CausaFalta values (8, 'Falta_visibildad');
insert into CausaFalta values (9, 'Alteración_orden_publico');
insert into CausaFalta values (10, 'Desacato');
insert into CausaFalta values (11, 'Obras_sin_autorizacion');
insert into CausaFalta values (12, 'Daños_materiales');
insert into CausaFalta values (13, 'Faltan_albaranes');
insert into CausaFalta values (14, 'Traspaso_ilegal');
insert into CausaFalta values (15, 'Cierre_injutificado');
insert into CausaFalta values (16, 'Venta_no_autorizada');
insert into CausaFalta values (17, 'Falta_carne_manipulador');
insert into CausaFalta values (18, 'Venta_sin_licencia');
insert into CausaFalta values (19, 'Impago_exacciones');
insert into CausaFalta values (20, 'Ausencia_albaran');
insert into CausaFalta values (21, 'Abandono_mas_de_un_mes');
insert into CausaFalta values (22, 'Reiteracion_graves');
insert into CausaFalta values (23, 'Disturbios');
insert into CausaFalta values (24, 'Incumpliiento_resolución');

insert into TipoSancion values (1, 'Requisado_articulos');
insert into TipoSancion values (2, 'Suspension_obras_e_instalaciones');
insert into TipoSancion values (3, 'Multa');
insert into TipoSancion values (4, 'Suspension_concesion');
insert into TipoSancion values (5, 'Caducidad_concesion');

insert into Falta values(1357, 60023, 1, '2018-10-27 17:45:00',  3);
insert into Falta values(2159, 20034, 2, '2018-06-01 18:26:00', 12);
insert into Falta values(3768, 54732, 1, '2005-06-10 16:52:00',  2);
insert into Falta values(4435, 54732, 1, '2005-07-30 19:09:00',  1);
insert into Falta values(5489, 54732, 2, '2005-07-30 19:09:00',  8);

insert into Sancion values (13570, 3, 51.99, 1357);
insert into Sancion values (21590, 3, 00.00, 2159);
insert into Sancion values (21591, 1, 80.00, 2159);
insert into Sancion values (37680, 3, 40.00, 3768);
insert into Sancion values (44350, 3, 50.00, 4435);
insert into Sancion values (54890, 3, 30.00, 5489);

select L.id_licencia, L.dni_titular from Licencia L where L.tipo=1 and L.causa_extincion is null;

SELECT pe.dni,pe.nombre,pe.ap1,pe.ap2 FROM Puesto p,Licencia l,Titular t,Persona pe WHERE p.numero_de_puesto=l.id_puesto and p.id_mercado=1 and l.causa_extincion IS NULL and t.dni_titular=l.dni_titular and pe.dni=t.dni_titular;

SELECT l.id_licencia FROM Licencia l WHERE EXTRACT(year FROM l.inicio_licencia)>=2010 and EXTRACT(year FROM l.inicio_licencia) <=2020;

SELECT DISTINCT p.dni FROM Persona p,Titular t,Licencia l,Falta f WHERE p.dni=t.dni_titular and t.dni_titular=l.dni_titular and l.id_licencia=f.id_licencia;

SELECT l.id_licencia,l.dni_titular FROM Licencia l WHERE l.causa_extincion IS NOT NULL;
