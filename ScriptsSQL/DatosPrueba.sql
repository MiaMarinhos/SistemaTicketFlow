USE ticket_flow;

-- =====================================
-- REGION
-- =====================================
INSERT INTO region(idRegion, nombre) VALUES (1, 'Lima');
INSERT INTO region(nombre) VALUES
('Lima'),
('Arequipa'),
('Cusco');

-- =====================================
-- DISTRITO
-- =====================================
INSERT INTO distrito(idDistrito, nombre, idRegion) VALUES (1, 'San Miguel', 1);

INSERT INTO distrito(nombre, idRegion) VALUES

('San Isidro',2),
('Miraflores',3),
('Santiago',4);

-- =====================================
-- GENERO
-- =====================================
INSERT INTO genero(idGenero, genero) VALUES (1, 'MASCULINO');
INSERT INTO genero(idGenero, genero) VALUES (2, 'FEMENINO');


-- =====================================
-- ESTADO_USUARIO
-- =====================================
INSERT INTO estado_usuario(idEstado_usuario, estado) VALUES (1, 'ACTIVO');
INSERT INTO estado_usuario(idEstado_usuario, estado) VALUES (2, 'ELIMINADO');

-- =====================================
-- TIPO_USUARIO
-- =====================================
INSERT INTO tipo_usuario(idTipo_usuario, nombre) VALUES (1, 'CLIENTE');
INSERT INTO tipo_usuario(idTipo_usuario, nombre) VALUES (2, 'ANFITRION');
INSERT INTO tipo_usuario(idTipo_usuario, nombre) VALUES (3, 'ADMIN');

-- =====================================
-- BANCO
-- =====================================
INSERT INTO banco(nombre_largo,nombre_corto) VALUES
('Banco de Crédito del Perú','BCP'),
('Interbank','IBK'),
('BBVA Perú','BBVA');

-- =====================================
-- USUARIO
-- =====================================
INSERT INTO usuario(
idUsuario,dni,nombre,apellido_paterno,apellido_materno,
telefono,edad,correo_electronico,contrasena,
fecha_registro,fecha_nacimiento,idGenero,idDistrito,idEstado)
VALUES
(1,'12345678','Juan','Perez','Lopez',
'999111222',25,'juan@correo.com','123456',
'2026-01-01','2001-05-10',1,1,1),

(2,'23456789','Maria','Gomez','Torres',
'999222333',30,'maria@correo.com','123456',
'2026-01-01','1996-08-15',2,1,1),

(3,'34567890','Carlos','Ramirez','Diaz',
'999333444',35,'carlos@correo.com','123456',
'2026-01-01','1991-02-20',1,2,1),

(4,'45678901','Ana','Flores','Castro',
'999444555',28,'ana@correo.com','123456',
'2026-01-01','1998-04-01',2,3,1);

-- =====================================
-- USUARIO_X_TIPO
-- =====================================
INSERT INTO `usuario_x_tipo` VALUES (1,1);
INSERT INTO `usuario_x_tipo` VALUES (1,2);
INSERT INTO `usuario_x_tipo` VALUES (1,3);


-- =====================================
-- CLIENTE
-- =====================================
INSERT INTO cliente(idCliente,puntos_bonus) VALUES
(1,50),
(4,20);

-- =====================================
-- ADMINISTRADOR
-- =====================================
INSERT INTO administrador(
idAdmin,img_qr,monto_total,monto_neto,monto_disponible)
VALUES
(3,'admin_qr.png',10000,8500,7000);

-- =====================================
-- ANFITRION
-- =====================================
INSERT INTO anfitrion(
idAnfitrion,razon_social,ruc,cuenta_bancaria,idBanco)
VALUES
(2,'Eventos Lima SAC','20123456789','12345678901234',1);

-- =====================================
-- CATEGORIA_EVENTO
-- =====================================
INSERT INTO categoria_evento(
    nombre,
    dias_para_publicacion
)
VALUES
('Deportes',15),
('Teatro',10),
('Entretenimiento',10),
('Seminarios y Conferencias',20),
('Cursos y Talleres',15),
('Party',7);
-- =====================================
-- ESTADO_PUBLICACION
-- =====================================
INSERT INTO estado_publicacion(estado) VALUES
('PENDIENTE'),
('APROBADO'),
('RECHAZADO');

-- =====================================
-- ESTADO_EVENTO
-- =====================================
INSERT INTO estado_evento(estado) VALUES
('ACTIVO'),
('CANCELADO'),
('FINALIZADO');

-- =====================================
-- EVENTO
-- =====================================
INSERT INTO evento(
idEvento,titulo,descripcion,capacidad_entradas,entradas_disponibles,
fecha,hora_inicio,hora_fin,ubicacion,
nombre_establecimiento,img,precio,
idDistrito,idAnfitrion,idCategoria_evento,
idEstado_publicacion,idEstado_evento)
VALUES

-- DEPORTES
(1,'Clasico del Futbol Peruano',
'Partido entre los equipos mas populares del pais',
600,600,'2026-11-15','16:00:00','18:00:00',
'Estadio Nacional','Estadio Nacional','clasico.jpg',
45.00,1,2,1,2,1),

(2,'Maraton Lima 10K y 21K',
'Competencia para corredores aficionados y profesionales',
1000,1000,'2026-11-22','06:00:00','11:00:00',
'Circuito Costa Verde','Costa Verde','maraton.jpg',
60.00,1,2,1,2,1),

(3,'Noche de Box Profesional',
'Cartelera de peleas por campeonato nacional',
400,400,'2026-12-05','20:00:00','23:00:00',
'Coliseo Lima','Coliseo Lima','box.jpg',
35.00,1,2,1,2,1),

(4,'Torneo de Tenis Open 2026',
'Final del campeonato nacional de tenis',
300,300,'2026-12-12','14:00:00','18:00:00',
'Club Lawn Tennis','Lawn Tennis','tenis.jpg',
50.00,1,2,1,2,1),

(5,'Campeonato Nacional de Surf',
'Competencia de surfistas profesionales',
250,250,'2026-12-18','09:00:00','15:00:00',
'Playa Punta Rocas','Punta Rocas','surf.jpg',
20.00,1,2,1,2,1),

(6,'Master de Padel Final',
'Final del circuito nacional de padel',
200,200,'2026-12-20','18:30:00','21:00:00',
'Padel Club Lima','Padel Club','padel.jpg',
40.00,1,2,1,2,1),

-- TEATRO
(7,'Hamlet Adaptacion Moderna',
'Nueva interpretacion del clasico de Shakespeare',
250,250,'2026-11-14','20:00:00','22:30:00',
'Teatro Municipal','Teatro Municipal','hamlet.jpg',
55.00,1,2,2,2,1),

(8,'Toc Toc Temporada Final',
'Ultimas funciones de la exitosa obra',
250,250,'2026-11-21','19:30:00','22:00:00',
'Teatro Peruano','Teatro Peruano','toctoc.jpg',
45.00,1,2,2,2,1),

(9,'Monologos de la Mente',
'Obra teatral de reflexion y comedia',
180,180,'2026-11-28','20:30:00','22:00:00',
'Centro Cultural','Centro Cultural','monologos.jpg',
35.00,1,2,2,2,1),

(10,'El Espejo Roto',
'Drama contemporaneo en tres actos',
180,180,'2026-12-04','19:00:00','21:00:00',
'Teatro Central','Teatro Central','espejo.jpg',
40.00,1,2,2,2,1),

(11,'Musical Broadway Lima',
'Adaptacion de exitos de Broadway',
500,500,'2026-12-11','18:00:00','21:00:00',
'Gran Teatro','Gran Teatro','broadway.jpg',
110.00,1,2,2,2,1),

(12,'Teatro Ciego',
'Experiencia teatral completamente sensorial',
150,150,'2026-12-18','21:00:00','22:30:00',
'Sala Oscura','Sala Oscura','ciego.jpg',
50.00,1,2,2,2,1),

-- ENTRETENIMIENTO
(13,'Comic Con Local 2026',
'Convencion de comics y cultura pop',
700,700,'2026-12-05','10:00:00','19:00:00',
'Centro de Convenciones','Centro de Convenciones','comiccon.jpg',
35.00,1,2,3,2,1),

(14,'Show de Magia',
'Espectaculo de magia e ilusionismo',
250,250,'2026-12-06','18:30:00','20:00:00',
'Teatro Magico','Teatro Magico','magia.jpg',
40.00,1,2,3,2,1),

(15,'Festival del Terror',
'Experiencia tematica de horror',
400,400,'2026-12-12','18:00:00','23:00:00',
'Parque de la Exposicion','Parque','terror.jpg',
25.00,1,2,3,2,1),

(16,'Torneo Nacional Gaming',
'Competencia profesional de videojuegos',
500,500,'2026-12-19','11:00:00','20:00:00',
'Arena Gamer','Arena Gamer','gaming.jpg',
30.00,1,2,3,2,1),

(17,'Circo Contemporaneo',
'Espectaculo familiar de acrobacias',
350,350,'2026-12-25','16:00:00','18:30:00',
'Carpa Central','Carpa Central','circo.jpg',
65.00,1,2,3,2,1),

(18,'Autos Clasicos',
'Exhibicion de vehiculos historicos',
300,300,'2027-01-03','10:00:00','17:00:00',
'Campo Ferial','Campo Ferial','autos.jpg',
20.00,1,2,3,2,1),

-- SEMINARIOS
(19,'Tech Summit IA',
'Congreso sobre inteligencia artificial',
500,500,'2026-11-18','09:00:00','18:00:00',
'Centro Empresarial','Centro Empresarial','ia.jpg',
150.00,1,2,4,2,1),

(20,'Marketing Digital',
'Congreso internacional de marketing',
400,400,'2026-11-25','10:00:00','18:00:00',
'Hotel Lima','Hotel Lima','marketing.jpg',
120.00,1,2,4,2,1),

(21,'Liderazgo y Emprendimiento',
'Foro para emprendedores',
300,300,'2026-12-02','15:00:00','20:00:00',
'Centro Empresarial','Centro Empresarial','liderazgo.jpg',
80.00,1,2,4,2,1),

(22,'Finanzas Personales',
'Seminario de gestion financiera',
250,250,'2026-12-09','18:00:00','21:00:00',
'Universidad Lima','Universidad Lima','finanzas.jpg',
45.00,1,2,4,2,1),

(23,'Arquitectura Sostenible',
'Innovacion y sostenibilidad urbana',
200,200,'2026-12-15','09:30:00','14:00:00',
'Colegio de Arquitectos','CAP','arquitectura.jpg',
95.00,1,2,4,2,1),

(24,'Transformacion Digital',
'Tendencias empresariales modernas',
300,300,'2026-12-20','09:00:00','16:00:00',
'Centro de Negocios','Centro Negocios','digital.jpg',
100.00,1,2,4,2,1),

-- CURSOS Y TALLERES
(25,'Cocteleria de Autor',
'Taller practico de bebidas premium',
80,80,'2026-11-20','19:00:00','22:00:00',
'Bar Escuela','Bar Escuela','cocteleria.jpg',
70.00,1,2,5,2,1),

(26,'Fotografia Urbana',
'Curso intensivo de fotografia',
60,60,'2026-11-27','15:00:00','19:00:00',
'Centro Fotografico','Centro Fotografico','foto.jpg',
55.00,1,2,5,2,1),

(27,'Cocina Italiana',
'Masterclass gastronomica',
50,50,'2026-12-04','11:00:00','15:00:00',
'Escuela de Cocina','Escuela Cocina','italiana.jpg',
90.00,1,2,5,2,1),

(28,'Oratoria y Desplante',
'Mejora tus habilidades comunicativas',
70,70,'2026-12-11','18:00:00','21:00:00',
'Centro de Capacitacion','Capacitacion','oratoria.jpg',
40.00,1,2,5,2,1),

(29,'Desarrollo con Blazor',
'Introduccion al framework Blazor',
100,100,'2026-12-18','16:00:00','20:00:00',
'Campus Tecnologico','Campus Tech','blazor.jpg',
100.00,1,2,5,2,1),

(30,'Pintura y Vino',
'Taller recreativo para adultos',
60,60,'2026-12-23','19:30:00','22:00:00',
'Studio Arte','Studio Arte','vino.jpg',
65.00,1,2,5,2,1),

-- PARTY
(31,'Neon Glow Party',
'Fiesta tematica neon al aire libre',
400,400,'2026-11-21','22:00:00','04:00:00',
'Open Air Club','Open Air','neon.jpg',
50.00,1,2,6,2,1),

(32,'Retro 80s y 90s',
'Fiesta con exitos clasicos',
350,350,'2026-11-28','21:00:00','03:00:00',
'Disco Retro','Disco Retro','retro.jpg',
35.00,1,2,6,2,1),

(33,'White Party 2027',
'Celebracion de ano nuevo',
700,700,'2026-12-31','23:00:00','05:00:00',
'Club Costa','Club Costa','white.jpg',
150.00,1,2,6,2,1),

(34,'Pool Party Summer',
'Fiesta de verano en piscina',
300,300,'2027-01-09','14:00:00','22:00:00',
'Resort Lima','Resort Lima','pool.jpg',
60.00,1,2,6,2,1),

(35,'Electro Sunset',
'Musica electronica al atardecer',
250,250,'2027-01-16','17:30:00','23:30:00',
'Rooftop Sky','Rooftop Sky','electro.jpg',
45.00,1,2,6,2,1),

(36,'Beach Party Festival',
'Festival playero con DJs invitados',
800,800,'2027-01-23','16:00:00','02:00:00',
'Playa Asia','Asia Beach','beach.jpg',
80.00,1,2,6,2,1);

-- =====================================
-- PUNTOS_BONUS
-- =====================================
INSERT INTO puntos_bonus(
puntos_canjeables,descuento)
VALUES
(100,10),
(200,20),
(300,30);
select * from puntos_bonus;
-- =====================================
-- ESTADO_COMPRAS
-- =====================================
INSERT INTO estado_compras(estado) VALUES
('EN PROCESO'),
('CONFIRMADA'),
('ANULADA');
select * from estado_compras;
select * from evento;
select * from cliente;
select * from puntos_bonus;
INSERT INTO puntos_bonus(idPuntos_bonus, puntos_canjeables, descuento) VALUES
(0, 0, 0);
INSERT INTO estado_evento(idEstado_evento, estado) VALUES
(4, 'DISPONIBLE'),
(5, 'AGOTADO');
select * from estado_evento;
-- =====================================
-- COMPRAS
-- =====================================
INSERT INTO compras(
idCompras,entradas_compradas,fecha_compra,
metodo_pago,hora_compra,estado,
monto_parcial,monto_total,
idPuntos_bonus,idCliente,idEvento,idEstado)
VALUES
(1,2,'2026-06-10',
'YAPE','18:00:00','CONFIRMADO',
240.00,216.00,
1,1,1,2),

(2,1,'2026-06-11',
'PLIN','19:00:00','EN PROCESO',
50.00,50.00,
1,4,2,1);

-- =====================================
-- ESTADO_PAGOS
-- =====================================
INSERT INTO estado_pagos(estado) VALUES
('PENDIENTE'),
('PAGADO'),
('VENCIDO');

-- =====================================
-- PAGOS
-- =====================================
INSERT INTO pagos(
idPagos,fecha_pago,fecha_limite_pago,
total_a_pagar,comprobante,
idEvento,idEstado)
VALUES
(1,'2026-06-12','2026-06-20',
500.00,'COMP-001.pdf',
1,2),

(2,'2026-06-13','2026-06-25',
250.00,'COMP-002.pdf',
2,1);

-- =====================================
-- ESTADO_SOLICITUDES
-- =====================================
INSERT INTO estado_solicitudes(estado) VALUES
('PENDIENTE'),
('APROBADA'),
('RECHAZADA');

-- =====================================
-- SOLICITUDES
-- =====================================
INSERT INTO solicitudes(
telefono_contacto,
correo_contacto,
motivo,
idAdministrador,
idUsuario,
idEstado)
VALUES
(
'999888777',
'juan@correo.com',
'Deseo convertirme en anfitrión',
3,
1,
1
),

(
'999777666',
'ana@correo.com',
'Solicitud para publicar eventos',
3,
4,
2
);