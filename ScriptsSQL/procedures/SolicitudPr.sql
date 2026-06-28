USE `ticket_flow` ;
-- ---------------------------------
-- ESTADO SOLICITUD
-- ---------------------------------
-- Create
DELIMITER $$

drop procedure if exists USP_INSERTAR_ESTADO_SOLICITUD;

CREATE PROCEDURE USP_INSERTAR_ESTADO_SOLICITUD(
    OUT p_idEstadoSolicitud INT,
    IN p_estado VARCHAR(45)
)
BEGIN

INSERT INTO estado_solicitudes(
    estado
)
VALUES(
          p_estado
      );

SET p_idEstadoSolicitud = LAST_INSERT_ID();

END$$

DELIMITER ;

-- Read
DELIMITER $$

drop procedure if exists USP_LEER_ESTADO_SOLICITUD;

CREATE PROCEDURE USP_LEER_ESTADO_SOLICITUD(
    IN p_idEstadoSolicitud INT
)
BEGIN

SELECT
    idEstado_solicitudes,
    estado
FROM estado_solicitudes
WHERE idEstado_solicitudes = p_idEstadoSolicitud;

END$$

DELIMITER ;

-- Update
DELIMITER $$

drop procedure if exists USP_ACTUALIZAR_ESTADO_SOLICITUD;

CREATE PROCEDURE USP_ACTUALIZAR_ESTADO_SOLICITUD(
    IN p_idEstadoSolicitud INT,
    IN p_estado VARCHAR(45)
)
BEGIN

UPDATE estado_solicitudes
SET estado = p_estado
WHERE idEstado_solicitudes = p_idEstadoSolicitud;

END$$

DELIMITER ;

-- Delete
drop procedure if exists USP_ELIMINAR_ESTADO_SOLICITUD;

DELIMITER $$

CREATE PROCEDURE USP_ELIMINAR_ESTADO_SOLICITUD(
    IN p_idEstadoSolicitud INT
)
BEGIN

DELETE FROM estado_solicitudes
WHERE idEstado_solicitudes = p_idEstadoSolicitud;

END$$

DELIMITER ;

-- ListAll

drop procedure if exists USP_LISTAR_ESTADOS_SOLICITUD;

DELIMITER $$

CREATE PROCEDURE USP_LISTAR_ESTADOS_SOLICITUD()
BEGIN

SELECT
    idEstado_solicitudes,
    estado
FROM estado_solicitudes;

END$$

DELIMITER ;


-- ---------------------------------
-- SOLICITUD
-- ---------------------------------
-- Create

drop procedure if exists USP_INSERTAR_SOLICITUD;

DELIMITER $$

CREATE PROCEDURE USP_INSERTAR_SOLICITUD(
    OUT p_idSolicitudes INT,
    IN p_telefono_contacto VARCHAR(45),
    IN p_correo_contacto VARCHAR(45),
    IN p_motivo VARCHAR(250),
    IN p_idAdministrador INT,
    IN p_idUsuario INT,
    IN p_idEstado INT
)
BEGIN

INSERT INTO solicitudes(
    telefono_contacto,
    correo_contacto,
    motivo,
    idAdministrador,
    idUsuario,
    idEstado
)
VALUES(
          p_telefono_contacto,
          p_correo_contacto,
          p_motivo,
          p_idAdministrador,
          p_idUsuario,
          p_idEstado
      );

SET p_idSolicitudes = LAST_INSERT_ID();

END$$

DELIMITER ;

-- Read

drop procedure if exists USP_LEER_SOLICITUD;

DELIMITER $$

CREATE PROCEDURE USP_LEER_SOLICITUD(
    IN p_idSolicitudes INT
)
BEGIN

SELECT
    idSolicitudes,
    telefono_contacto,
    correo_contacto,
    motivo,
    idAdministrador,
    idUsuario,
    idEstado
FROM solicitudes
WHERE idSolicitudes = p_idSolicitudes;

END$$

DELIMITER ;

-- Update

drop procedure if exists USP_ACTUALIZAR_SOLICITUD;

DELIMITER $$

CREATE PROCEDURE USP_ACTUALIZAR_SOLICITUD(
    IN p_idSolicitudes INT,
    IN p_telefono_contacto VARCHAR(45),
    IN p_correo_contacto VARCHAR(45),
    IN p_motivo VARCHAR(250),
    IN p_idAdministrador INT,
    IN p_idUsuario INT,
    IN p_idEstado INT
)
BEGIN

UPDATE solicitudes
SET
    telefono_contacto = p_telefono_contacto,
    correo_contacto = p_correo_contacto,
    motivo = p_motivo,
    idAdministrador = p_idAdministrador,
    idUsuario = p_idUsuario,
    idEstado = p_idEstado
WHERE idSolicitudes = p_idSolicitudes;

END$$

DELIMITER ;

-- Delete

drop procedure if exists USP_ELIMINAR_SOLICITUD;

DELIMITER $$

CREATE PROCEDURE USP_ELIMINAR_SOLICITUD(
    IN p_idSolicitudes INT
)
BEGIN

DELETE FROM solicitudes
WHERE idSolicitudes = p_idSolicitudes;

END$$

DELIMITER ;

-- ListAll

drop procedure if exists USP_LISTAR_SOLICITUDES;

DELIMITER $$

CREATE PROCEDURE USP_LISTAR_SOLICITUDES()
BEGIN
    SELECT
        s.idSolicitudes,
        s.telefono_contacto,
        s.correo_contacto,
        s.motivo,
        s.idAdministrador,
        s.idUsuario,
        s.idEstado,

        u.nombre,
        u.apellido_paterno,
        u.apellido_materno,

        es.estado AS estado_solicitud
    FROM solicitudes s
    INNER JOIN usuario u
        ON s.idUsuario = u.idUsuario
    INNER JOIN estado_solicitudes es
        ON s.idEstado = es.idEstado_solicitudes
    ORDER BY s.idSolicitudes;
END$$

DELIMITER ;

-- Buscar Solicitud por Nombre

drop procedure if exists USP_BUSCAR_SOLICITUD_NOMBRE;

DELIMITER $$

CREATE PROCEDURE USP_BUSCAR_SOLICITUD_NOMBRE(
    IN p_nombre VARCHAR(45)
)
BEGIN

SELECT
    s.*,
    u.nombre,
    u.apellido_paterno,
    u.correo_electronico
FROM solicitudes s
         INNER JOIN usuario u
                    ON s.idUsuario = u.idUsuario
WHERE u.nombre LIKE CONCAT('%', p_nombre, '%');

END$$

DELIMITER ;

-- Filtrar Por Estado

drop procedure if exists USP_FILTRAR_SOLICITUD_ESTADO;

DELIMITER $$

CREATE PROCEDURE USP_FILTRAR_SOLICITUD_ESTADO(
    IN p_idEstado INT
)
BEGIN
    SELECT
        s.idSolicitudes,
        s.telefono_contacto,
        s.correo_contacto,
        s.motivo,
        s.idAdministrador,
        s.idUsuario,
        s.idEstado,

        u.nombre,
        u.apellido_paterno,
        u.apellido_materno,

        es.estado AS estado_solicitud

    FROM solicitudes s

    INNER JOIN usuario u
        ON s.idUsuario = u.idUsuario

    INNER JOIN estado_solicitudes es
        ON s.idEstado = es.idEstado_solicitudes

    WHERE s.idEstado = p_idEstado

    ORDER BY s.idSolicitudes ASC;
END$$

DELIMITER ;

-- Aprobar Solicitud

drop procedure if exists USP_APROBAR_SOLICITUD;

DELIMITER $$

CREATE PROCEDURE USP_APROBAR_SOLICITUD(
    IN p_idSolicitud INT
)
BEGIN
UPDATE solicitudes
SET idEstado = 2
WHERE idSolicitudes = p_idSolicitud;
END$$

DELIMITER ;

-- Rechazar Solicitud
drop procedure if exists USP_RECHAZAR_SOLICITUD;

DELIMITER $$

CREATE PROCEDURE USP_RECHAZAR_SOLICITUD(
    IN p_idSolicitud INT
)
BEGIN

UPDATE solicitudes
SET idEstado = 3
WHERE idSolicitudes = p_idSolicitud;

END$$

DELIMITER ;