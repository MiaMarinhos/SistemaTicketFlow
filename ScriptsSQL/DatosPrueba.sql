use ticket_flow;


INSERT INTO region(idRegion, nombre) VALUES (1, 'Lima');
-- select * from region;

INSERT INTO distrito(idDistrito, nombre, idRegion) VALUES (1, 'San Miguel', 1);

-- select * from distrito;

INSERT INTO tipo_usuario(idTipo_usuario, nombre) VALUES (1, 'CLIENTE');
INSERT INTO tipo_usuario(idTipo_usuario, nombre) VALUES (2, 'ANFITRION');
INSERT INTO tipo_usuario(idTipo_usuario, nombre) VALUES (3, 'ADMIN');

/*
DELETE FROM tipo_usuario 
WHERE idTipo_usuario = 5;

select * from tipo_usuario;
*/

INSERT INTO estado_usuario(idEstado_usuario, estado) VALUES (1, 'ACTIVO');
INSERT INTO estado_usuario(idEstado_usuario, estado) VALUES (2, 'ELIMINADO');

INSERT INTO genero(idGenero, genero) VALUES (1, 'MASCULINO');
INSERT INTO genero(idGenero, genero) VALUES (2, 'FEMENINO');

-- select * from estado_usuario;
-- select * from genero;

INSERT INTO `ticket_flow`.`usuario` (`idUsuario`,
    `dni`,  `nombre`, `apellido_paterno`, `apellido_materno`, `telefono`, 
    `edad`, `correo_electronico`, `contrasena`, `fecha_registro`, 
    `fecha_nacimiento`, `idGenero`, `idDistrito`, `idEstado`
) 
VALUES (
    1, '71234567', 'Juan', 'Pérez',  'Gómez', '987654321', '25', 
    'juan@gmail.com', '123', 
    '2026-05-31', '2001-04-15', 1, 1, 1
);

-- select * from usuario;


INSERT INTO `usuario_x_tipo` VALUES (1,1);
INSERT INTO `usuario_x_tipo` VALUES (1,2);
INSERT INTO `usuario_x_tipo` VALUES (1,3);

select * from usuario;
select * from usuario_x_tipo;