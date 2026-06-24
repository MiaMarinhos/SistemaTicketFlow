USE ticket_flow;

DELIMITER //

-- =====================================
-- INSERTAR ADMINISTRADOR
-- =====================================
DROP PROCEDURE IF EXISTS SP_INSERTAR_ADMINISTRADOR //

CREATE PROCEDURE SP_INSERTAR_ADMINISTRADOR(
    IN p_idAdmin INT,
    IN p_img_qr VARCHAR(255),
    IN p_monto_total DOUBLE,
    IN p_monto_neto DOUBLE,
    IN p_monto_disponible DOUBLE
)
BEGIN
    INSERT INTO administrador(
        idAdmin,
        img_qr,
        monto_total,
        monto_neto,
        monto_disponible
    )
    VALUES(
        p_idAdmin,
        p_img_qr,
        p_monto_total,
        p_monto_neto,
        p_monto_disponible
    );
END //

-- =====================================
-- LEER ADMINISTRADOR
-- =====================================

DROP PROCEDURE IF EXISTS SP_LEER_ADMINISTRADOR_NUEVO //
CREATE PROCEDURE SP_LEER_ADMINISTRADOR_NUEVO(
    IN p_idAdmin INT
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
        u.correo_electronico,
        u.contrasena,
        u.fecha_registro,
        u.fecha_nacimiento,
        u.idGenero,
        u.idDistrito,
        a.img_qr,
        a.monto_total,
        a.monto_neto,
        a.monto_disponible
    FROM administrador a
    INNER JOIN usuario u
        ON a.idAdmin = u.idUsuario
    WHERE a.idAdmin = p_idAdmin;
END //

-- =====================================
-- ACTUALIZAR ADMINISTRADOR
-- =====================================

DROP PROCEDURE IF EXISTS SP_ACTUALIZAR_ADMINISTRADOR //

CREATE PROCEDURE SP_ACTUALIZAR_ADMINISTRADOR(
    IN p_idAdmin INT,
    IN p_img_qr VARCHAR(255),
    IN p_monto_total DOUBLE,
    IN p_monto_neto DOUBLE,
    IN p_monto_disponible DOUBLE
)
BEGIN
    UPDATE administrador
    SET
        img_qr = p_img_qr,
        monto_total = p_monto_total,
        monto_neto = p_monto_neto,
        monto_disponible = p_monto_disponible
    WHERE idAdmin = p_idAdmin;
END //

-- =====================================
-- ELIMINAR ADMINISTRADOR
-- =====================================

DROP PROCEDURE IF EXISTS SP_ELIMINAR_ADMINISTRADOR //

CREATE PROCEDURE SP_ELIMINAR_ADMINISTRADOR(
    IN p_idAdmin INT
)
BEGIN
    DELETE FROM administrador
    WHERE idAdmin = p_idAdmin;
END //

-- =====================================
-- LISTAR ADMINISTRADORES
-- =====================================

DROP PROCEDURE IF EXISTS SP_LISTAR_ADMINISTRADORES //

CREATE PROCEDURE SP_LISTAR_ADMINISTRADORES()
BEGIN
    SELECT
        u.idUsuario,
        u.dni,
        u.nombre,
        u.apellido_paterno,
        u.apellido_materno,
        u.telefono,
        u.edad,
        u.correo_electronico,
        u.contrasena,
        u.fecha_registro,
        u.fecha_nacimiento,
        u.idGenero,
        u.idDistrito,
        a.img_qr,
        a.monto_total,
        a.monto_neto,
        a.monto_disponible
    FROM administrador a
    INNER JOIN usuario u
        ON a.idAdmin = u.idUsuario
    ORDER BY u.idUsuario;
END //

DELIMITER ;