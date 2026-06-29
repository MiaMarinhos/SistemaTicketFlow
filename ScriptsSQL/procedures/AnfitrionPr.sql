USE `ticket_flow`;

-- ---------------------------------
-- ANFITRION (STORED PROCEDURES)
-- ---------------------------------

-- 1. CREAR ANFITRION
DELIMITER //
DROP PROCEDURE IF EXISTS SP_INSERTAR_ANFITRION //
CREATE PROCEDURE SP_INSERTAR_ANFITRION(
    IN p_idUsuario INT,
    IN p_razon_social VARCHAR(100),
    IN p_ruc VARCHAR(20),
    IN p_cuenta_bancaria VARCHAR(50),
    IN p_idBanco INT
)
BEGIN
    INSERT INTO anfitrion (idAnfitrion, razon_social, ruc, cuenta_bancaria, idBanco)
    VALUES (p_idUsuario, p_razon_social, p_ruc, p_cuenta_bancaria, p_idBanco);
END //
DELIMITER ;

-- 2. LEER ANFITRION (¡El que estaba causando el error nulo!)
DELIMITER //
DROP PROCEDURE IF EXISTS SP_LEER_ANFITRION //
CREATE PROCEDURE SP_LEER_ANFITRION(
    IN p_idAnfitrion INT
)
BEGIN
    SELECT 
        u.idUsuario, u.dni, u.nombre, u.apellido_paterno, u.apellido_materno, 
        u.telefono, u.edad, u.correo_electronico, u.contrasena, 
        u.fecha_registro, u.fecha_nacimiento, u.idDistrito, u.idGenero,
        a.razon_social, a.ruc, a.cuenta_bancaria,
        b.idBanco, b.nombre_largo, b.nombre_corto
    FROM anfitrion a
    INNER JOIN usuario u ON a.idAnfitrion = u.idUsuario
    LEFT JOIN banco b ON a.idBanco = b.idBanco
    WHERE a.idAnfitrion = p_idAnfitrion;
END //
DELIMITER ;

-- 3. ACTUALIZAR ANFITRION
DELIMITER //
DROP PROCEDURE IF EXISTS SP_ACTUALIZAR_ANFITRION //
CREATE PROCEDURE SP_ACTUALIZAR_ANFITRION(
    IN p_idAnfitrion INT,
    IN p_razon_social VARCHAR(100),
    IN p_ruc VARCHAR(20),
    IN p_cuenta_bancaria VARCHAR(50),
    IN p_idBanco INT
)
BEGIN
    UPDATE anfitrion
    SET razon_social = p_razon_social,
        ruc = p_ruc,
        cuenta_bancaria = p_cuenta_bancaria,
        idBanco = p_idBanco
    WHERE idAnfitrion = p_idAnfitrion;
END //
DELIMITER ;

-- 4. ELIMINAR ANFITRION
DELIMITER //
DROP PROCEDURE IF EXISTS SP_ELIMINAR_ANFITRION //
CREATE PROCEDURE SP_ELIMINAR_ANFITRION(
    IN p_idAnfitrion INT
)
BEGIN
    DELETE FROM anfitrion WHERE idAnfitrion = p_idAnfitrion;
END //
DELIMITER ;

-- 5. LISTAR ANFITRIONES
DELIMITER //
DROP PROCEDURE IF EXISTS SP_LISTAR_ANFITRIONES //
CREATE PROCEDURE SP_LISTAR_ANFITRIONES()
BEGIN
    SELECT 
        u.idUsuario, u.dni, u.nombre, u.apellido_paterno, u.apellido_materno, 
        u.telefono, u.edad, u.correo_electronico, u.contrasena, 
        u.fecha_registro, u.fecha_nacimiento, u.idDistrito, u.idGenero,
        a.razon_social, a.ruc, a.cuenta_bancaria,
        b.idBanco, b.nombre_largo, b.nombre_corto
    FROM anfitrion a
    INNER JOIN usuario u ON a.idAnfitrion = u.idUsuario
    LEFT JOIN banco b ON a.idBanco = b.idBanco;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS SP_LISTAR_MIS_EVENTOS ;

DELIMITER //
CREATE PROCEDURE SP_LISTAR_MIS_EVENTOS(in p_idAnfitrio int)
begin
	SELECT
    e.idEvento,
    e.titulo,
    e.descripcion,
    e.capacidad_entradas,
    e.fecha,
    e.hora_inicio,
    e.hora_fin,
    e.ubicacion,
    e.nombre_establecimiento,
    e.img,
    e.precio,
    e.idAnfitrion,
    e.idEstado_publicacion,
    e.idEstado_evento,
    e.idDistrito,

    c.nombre AS categoria,

    ep.estado AS estado_publicacion

	FROM evento e
	INNER JOIN categoria_evento c
		ON e.idCategoria_evento = c.idCategoria_evento
	INNER JOIN estado_publicacion ep
		ON e.idEstado_publicacion = ep.idEstado_publicacion;
end//

DELIMITER ;
