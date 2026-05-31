use ticket_flow;

INSERT INTO region(nombre) VALUES ('Lima');

INSERT INTO distrito(nombre, idRegion) VALUES ('San Miguel', 1);

INSERT INTO tipo_usuario(nombre) VALUES ('CLIENTE');
INSERT INTO tipo_usuario(nombre) VALUES ('ANFITRION');

select * from tipo_usuario;

INSERT INTO estado_usuario(estado) VALUES ('ACTIVO');
INSERT INTO estado_usuario(estado) VALUES ('ELIMINADO');

INSERT INTO genero(genero) VALUES ('MASCULINO');

INSERT INTO `ticket_flow`.`usuario` (
    `dni`,  `nombre`, `apellido_paterno`, `apellido_materno`, `telefono`, 
    `edad`, `correo_electronico`, `contrasena`, `fecha_registro`, 
    `fecha_nacimiento`, `idGenero`, `idDistrito`, `idEstado`
) 
VALUES (
    '71234567', 'Juan', 'Pérez',  'Gómez', '987654321', '25', 
    'juan.perez@email.com', 'ClaveSegura123', 
    '2026-05-31', '2001-04-15', 1, 1, 1
);

INSERT INTO `usuario_x_tipo` VALUES (1,1);
INSERT INTO `usuario_x_tipo` VALUES (1,2);

select * from usuario;
select * from usuario_x_tipo;