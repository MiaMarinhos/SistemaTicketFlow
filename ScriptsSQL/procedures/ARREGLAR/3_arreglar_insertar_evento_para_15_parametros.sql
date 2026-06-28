USE ticket_flow;

DROP PROCEDURE IF EXISTS SP_INSERTAR_EVENTO;

DELIMITER $$

CREATE PROCEDURE SP_INSERTAR_EVENTO(
    IN p_titulo VARCHAR(100),
    IN p_descripcion VARCHAR(250),
    IN p_capacidad_entradas INT,
    IN p_fecha DATE,
    IN p_hora_inicio TIME,
    IN p_hora_fin TIME,
    IN p_ubicacion VARCHAR(100),
    IN p_nombre_establecimiento VARCHAR(45),
    IN p_img VARCHAR(450),
    IN p_precio DOUBLE,
    IN p_idDistrito INT,
    IN p_idAnfitrion INT,
    IN p_idCategoria_evento INT,
    IN p_idEstado_publicacion INT,
    IN p_idEstado_evento INT
)
BEGIN
    INSERT INTO evento (
        titulo,
        descripcion,
        capacidad_entradas,
        entradas_disponibles,
        fecha,
        hora_inicio,
        hora_fin,
        ubicacion,
        nombre_establecimiento,
        img,
        precio,
        idDistrito,
        idAnfitrion,
        idCategoria_evento,
        idEstado_publicacion,
        idEstado_evento
    )
    VALUES (
        p_titulo,
        p_descripcion,
        p_capacidad_entradas,
        p_capacidad_entradas,
        p_fecha,
        p_hora_inicio,
        p_hora_fin,
        p_ubicacion,
        p_nombre_establecimiento,
        p_img,
        p_precio,
        p_idDistrito,
        p_idAnfitrion,
        p_idCategoria_evento,
        p_idEstado_publicacion,
        p_idEstado_evento
    );
END$$

DELIMITER ;