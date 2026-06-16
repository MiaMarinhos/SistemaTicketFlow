USE `ticket_flow` ;
-----------------------------------
--USUARIO
-----------------------------------
-- CREATE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_INSERTAR_USUARIO //

CREATE PROCEDURE SP_INSERTAR_USUARIO(
    OUT p_idUsuario INT,
    IN p_dni VARCHAR(45),
    IN p_nombre VARCHAR(45),
    IN p_apellido_paterno VARCHAR(45),
    IN p_apellido_materno VARCHAR(45),
    IN p_telefono VARCHAR(45),
	IN p_edad INT,
	IN p_idGenero INT,
    IN p_correo_electronico VARCHAR(45),
    IN p_contrasena VARCHAR(45),
    IN p_fecha_registro DATE,
    IN p_fecha_nacimiento DATE,
    IN p_idDistrito INT,
    IN p_idEstado INT,
    IN p_tipo INT
)
BEGIN
INSERT INTO usuario (
    dni, nombre, apellido_paterno, apellido_materno,
    telefono, edad, idGenero, correo_electronico, contrasena,
    fecha_registro, fecha_nacimiento,
    idDistrito, idEstado
)
VALUES (
           p_dni, p_nombre, p_apellido_paterno, p_apellido_materno,
           p_telefono, p_edad, p_idGenero, p_correo_electronico, p_contrasena,
           p_fecha_registro, p_fecha_nacimiento,
           p_idDistrito, p_idEstado
       );

SET p_idUsuario = LAST_INSERT_ID();

INSERT INTO usuario_x_tipo (
    idUsuario,
    idTipo_usuario
)
VALUES (
           p_idUsuario,
           p_tipo
       );
END //

DELIMITER ;


-- READ
DELIMITER //

DROP PROCEDURE IF EXISTS SP_LEER_USUARIO //

CREATE PROCEDURE SP_LEER_USUARIO(
    IN p_idUsuario INT
)
BEGIN
SELECT
    u.*,
    e.idEstado_usuario,
    e.estado AS nombre_estado,
    tu.idTipo_usuario,
    tu.nombre AS nombre_tipo_usuario
FROM usuario u
         INNER JOIN estado_usuario e
                    ON u.idEstado = e.idEstado_usuario
         INNER JOIN usuario_x_tipo uxt
                    ON u.idUsuario = uxt.idUsuario
         INNER JOIN tipo_usuario tu
                    ON uxt.idTipo_usuario = tu.idTipo_usuario
WHERE u.idUsuario = p_idUsuario;
END //

DELIMITER ;


-- UPDATE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_ACTUALIZAR_USUARIO //

CREATE PROCEDURE SP_ACTUALIZAR_USUARIO(
    IN p_idUsuario INT,
    IN p_dni VARCHAR(45),
    IN p_nombre VARCHAR(45),
    IN p_apellido_paterno VARCHAR(45),
    IN p_apellido_materno VARCHAR(45),
    IN p_telefono VARCHAR(45),
	IN p_edad INT,
	IN p_idGenero INT,
    IN p_correo_electronico VARCHAR(45),
    IN p_contrasena VARCHAR(45),
    IN p_fecha_nacimiento DATE,
    IN p_idDistrito INT,
    IN p_idEstado INT
)
BEGIN
UPDATE usuario
SET
    dni = p_dni,
    nombre = p_nombre,
    apellido_paterno = p_apellido_paterno,
    apellido_materno = p_apellido_materno,
    telefono = p_telefono,
	edad = p_edad,
	idGenero = p_idGenero,
    correo_electronico = p_correo_electronico,
    contrasena = p_contrasena,
    fecha_nacimiento = p_fecha_nacimiento,
    idDistrito = p_idDistrito,
    idEstado = p_idEstado
WHERE idUsuario = p_idUsuario;
END //

DELIMITER ;


-- DELETE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_ELIMINAR_USUARIO //

CREATE PROCEDURE SP_ELIMINAR_USUARIO(
    IN p_idUsuario INT
)
BEGIN
UPDATE usuario
SET idEstado = (
    SELECT idEstado_usuario
    FROM estado_usuario
    WHERE estado = 'ELIMINADO'
    LIMIT 1
    )
WHERE idUsuario = p_idUsuario;
END //

DELIMITER ;


-- LIST ALL
DELIMITER //

DROP PROCEDURE IF EXISTS SP_LISTAR_USUARIOS //

CREATE PROCEDURE SP_LISTAR_USUARIOS()
BEGIN
SELECT
    u.*,
    e.idEstado_usuario,
    e.estado AS nombre_estado,
    tu.idTipo_usuario,
    tu.nombre AS nombre_tipo_usuario
FROM usuario u
         INNER JOIN estado_usuario e
                    ON u.idEstado = e.idEstado_usuario
         INNER JOIN usuario_x_tipo uxt
                    ON u.idUsuario = uxt.idUsuario
         INNER JOIN tipo_usuario tu
                    ON uxt.idTipo_usuario = tu.idTipo_usuario;
END //

DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS SP_LEER_TIPO_USUARIO //
CREATE PROCEDURE SP_LEER_TIPO_USUARIO(IN p_idTipoUsuario INT)
BEGIN
SELECT * FROM tipo_usuario
WHERE idTipo_usuario=p_idTipoUsuario;
END //

DROP PROCEDURE IF EXISTS SP_LEER_TIPO_USUARIO_X_TIPO //
CREATE PROCEDURE SP_LEER_TIPO_USUARIO_X_TIPO(IN p_TipoUsuario VARCHAR(45))
BEGIN
SELECT *
FROM tipo_usuario
WHERE nombre=p_TipoUsuario;
END //

DROP PROCEDURE IF EXISTS SP_LEER_ESTADO_USUARIO //
CREATE PROCEDURE SP_LEER_ESTADO_USUARIO(IN p_idEstadoUsuario INT)
BEGIN
SELECT *
FROM estado_usuario
WHERE idEstado_usuario=p_idEstadoUsuario;
END //


--BuscarPorNombre
DELIMITER $$

CREATE PROCEDURE SP_BUSCAR_USUARIO_NOMBRE(
    IN p_nombre VARCHAR(45)
)
BEGIN

SELECT
    u.*,
    uxt.idTipo_usuario
FROM usuario u
         INNER JOIN usuario_x_tipo uxt
                    ON u.idUsuario = uxt.idUsuario
WHERE u.nombre LIKE CONCAT('%', p_nombre, '%');

END$$

DELIMITER ;

--Filtrar Por Tipo
DELIMITER $$

CREATE PROCEDURE SP_FILTRAR_USUARIO_TIPO(
    IN p_idTipo_usuario INT
)
BEGIN
SELECT
    u.*,
    uxt.idTipo_usuario
FROM usuario u
         INNER JOIN usuario_x_tipo uxt
                    ON u.idUsuario = uxt.idUsuario
WHERE uxt.idTipo_usuario = p_idTipo_usuario;
END$$

DELIMITER ;

--Filtrar Por Estado
DELIMITER $$

CREATE PROCEDURE SP_FILTRAR_USUARIO_ESTADO(
    IN p_idEstado INT
)
BEGIN

SELECT
    u.*,
    uxt.idTipo_usuario
FROM usuario u
         INNER JOIN usuario_x_tipo uxt
                    ON u.idUsuario = uxt.idUsuario
WHERE u.idEstado = p_idEstado;

END$$

DELIMITER ;

--Bloquear Usuario
DELIMITER $$

CREATE PROCEDURE SP_BLOQUEAR_USUARIO(
    IN p_idUsuario INT
)
BEGIN

UPDATE usuario
SET idEstado = 2
WHERE idUsuario = p_idUsuario;

END$$

DELIMITER ;

--DesbloquearUsuario
DELIMITER $$

CREATE PROCEDURE SP_DESBLOQUEAR_USUARIO(
    IN p_idUsuario INT
)
BEGIN

UPDATE usuario
SET idEstado = 1
WHERE idUsuario = p_idUsuario;

END$$

DELIMITER ;

--------------------------------------------------------------------------------------------------------------------------------------------
--CLIENTE
-----------------------------------
-- CREATE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_INSERTAR_CLIENTE //

CREATE PROCEDURE SP_INSERTAR_CLIENTE(
    IN p_id_usuario INT,
    IN p_puntos_bonus INT
)
BEGIN
INSERT INTO cliente (
    idCliente,
    puntos_bonus
)
VALUES (
           p_id_usuario,
           p_puntos_bonus
       );
END //

DELIMITER ;


-- READ
DELIMITER //

DROP PROCEDURE IF EXISTS SP_LEER_CLIENTE //

CREATE PROCEDURE SP_LEER_CLIENTE(
    IN p_idCliente INT
)
BEGIN
SELECT
    u.idUsuario,
    u.dni,
    u.nombre,
    u.apellido_paterno,
    u.apellido_materno,
    u.telefono,
	u.edad,
	u.idGenero,
    u.correo_electronico,
    u.contrasena,
    u.fecha_registro,
    u.fecha_nacimiento,
    u.idDistrito,
    u.idEstado,
    c.idCliente,
    c.puntos_bonus
FROM cliente c
         INNER JOIN usuario u
                    ON c.idCliente = u.idUsuario
WHERE c.idCliente = p_idCliente;
END //

DELIMITER ;


-- UPDATE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_ACTUALIZAR_CLIENTE //

CREATE PROCEDURE SP_ACTUALIZAR_CLIENTE(
    IN p_idCliente INT,
    IN p_puntos_bonus INT
)
BEGIN
UPDATE cliente
SET puntos_bonus = p_puntos_bonus
WHERE idCliente = p_idCliente;
END //

DELIMITER ;


-- DELETE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_ELIMINAR_CLIENTE //

CREATE PROCEDURE SP_ELIMINAR_CLIENTE(
    IN p_idCliente INT
)
BEGIN
DELETE FROM cliente
WHERE idCliente = p_idCliente;
END //

DELIMITER ;


-- LIST ALL
DELIMITER //

DROP PROCEDURE IF EXISTS SP_LISTAR_CLIENTES //

CREATE PROCEDURE SP_LISTAR_CLIENTES()
BEGIN
SELECT
    u.idUsuario,
    u.dni,
    u.nombre,
    u.apellido_paterno,
    u.apellido_materno,
    u.telefono,
	u.edad,
	u.idGenero,
    u.correo_electronico,
    u.contrasena,
    u.fecha_registro,
    u.fecha_nacimiento,
    u.idDistrito,
    u.idEstado,
    c.idCliente,
    c.puntos_bonus
FROM cliente c
         INNER JOIN usuario u
                    ON c.idCliente = u.idUsuario
ORDER BY c.idCliente;
END //

DELIMITER ;

-----------------------------------
--ANFITRION
-----------------------------------
-- CREATE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_INSERTAR_ADMINISTRADOR //

CREATE PROCEDURE SP_INSERTAR_ADMINISTRADOR(
    IN p_id_usuario INT,
	IN p_img VARCHAR(450),
	IN p_monto_total DOUBLE,
	IN p_monto_neto DOUBLE,
	IN p_monto_disponible DOUBLE
)
BEGIN
INSERT INTO administrador (
    idAdministrador,
    img_qr,
	monto_total,
	monto_neto,
	monto_disponible
)
VALUES (
           p_id_usuario,
           p_img,
		   p_monto_total,
		   p_monto_neto,
		   p_monto_disponible
       );
END //

DELIMITER ;


-- READ
DELIMITER //

DROP PROCEDURE IF EXISTS SP_LEER_ADMINISTRADOR //

CREATE PROCEDURE SP_LEER_ADMINISTRADOR(
    IN p_idUsuario INT
)
BEGIN
SELECT
    u.idUsuario,
    u.dni,
    u.nombre,
    u.apellido_paterno,
    u.apellido_materno,
    u.telefono,
	u.edad,
	u.idGenero,
    u.correo_electronico,
    u.contrasena,
    u.fecha_registro,
    u.fecha_nacimiento,
    u.idDistrito,
    u.idEstado,
    a.idAdmin,
    a.img_qr,
	a.monto_total,
	a.monto_neto,
	a.monto_disponible
FROM administrador a
         INNER JOIN usuario u
                    ON a.idAdmin = u.idUsuario
WHERE a.idAdmin = p_idUsuario;
END //

DELIMITER ;


-- UPDATE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_ACTUALIZAR_ADMINISTRADOR //

CREATE PROCEDURE SP_ACTUALIZAR_ADMINISTRADOR(
    IN p_idAdministrador INT,
    IN p_img VARCHAR(450),
	IN p_monto_total DOUBLE,
	IN p_monto_neto DOUBLE,
	IN p_monto_disponible DOUBLE
)
BEGIN
UPDATE administrador
SET
    img_qr = p_img,
    monto_total = p_monto_total,
    monto_neto = p_monto_neto,
    monto_disponible = p_monto_disponible
WHERE idAdministrador = p_idAdministrador;
END //

DELIMITER ;


-- DELETE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_ELIMINAR_ADMINISTRADOR //

CREATE PROCEDURE SP_ELIMINAR_ADMINISTRADOR(
    IN p_idAdministrador INT
)
BEGIN
DELETE FROM administrador
WHERE idAdministrador = p_idAdministrador;
END //

DELIMITER ;


-- LIST ALL
DELIMITER //

DROP PROCEDURE IF EXISTS SP_LISTAR_ADMINISTRADORES //

CREATE PROCEDURE SP_LISTAR_ADMINISTRADORES()
BEGIN
SELECT
    idAdministrador,
    codigo,
    nombre,
    apellido_paterno,
    apellido_materno,
    dni,
    contrasena
FROM administrador
ORDER BY idAdministrador;
END //

DELIMITER ;

-- READ ROL
DELIMITER //

DROP PROCEDURE IF EXISTS SP_LEER_USUARIO_POR_ROL //

CREATE PROCEDURE SP_LEER_USUARIO_POR_ROL(
    IN p_idUsuario INT,
	IN p_rol VARCHAR(45)
)
BEGIN
 SELECT 
     u.idUsuario,
     u.correo_electronico,
     u.contrasena,
     t.idTipo_usuario,
     t.nombre AS nombre_rol
 FROM usuario u, tipo_usuario t 
 WHERE u.idUsuario = p_idUsuario AND t.nombre = p_rol;
END //

DELIMITER ;

-- READ COMPROBACION
DELIMITER //

DROP PROCEDURE IF EXISTS SP_COMPROBAR_USUARIO //

CREATE PROCEDURE SP_COMPROBAR_USUARIO(
    IN p_correo VARCHAR(45),
	IN p_rol VARCHAR(45)
)
BEGIN
	SELECT u.idUsuario
	FROM usuario u, usuario_x_tipo x, tipo_usuario t 
	WHERE u.correo_electronico = @correo AND t.nombre = @rol
    AND u.idUsuario = x.idUsuario AND t.idTipo_usuario = x.idTipo_usuario;
END //

DELIMITER ;