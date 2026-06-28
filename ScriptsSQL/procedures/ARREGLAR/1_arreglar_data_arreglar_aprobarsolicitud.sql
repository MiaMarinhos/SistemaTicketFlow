USE ticket_flow;

ALTER TABLE anfitrion
MODIFY cuenta_bancaria VARCHAR(45) NULL;

DROP PROCEDURE IF EXISTS USP_APROBAR_SOLICITUD;

DELIMITER $$

CREATE PROCEDURE USP_APROBAR_SOLICITUD(
    IN p_idSolicitud INT
)
BEGIN
    DECLARE v_idUsuario INT DEFAULT NULL;
    DECLARE v_idBanco INT DEFAULT NULL;

    SELECT s.idUsuario
    INTO v_idUsuario
    FROM solicitudes s
    WHERE s.idSolicitudes = p_idSolicitud
    LIMIT 1;

    SELECT b.idBanco
    INTO v_idBanco
    FROM banco b
    ORDER BY b.idBanco
    LIMIT 1;

    IF v_idUsuario IS NOT NULL THEN

        UPDATE solicitudes
        SET idEstado = 2
        WHERE idSolicitudes = p_idSolicitud;

        INSERT IGNORE INTO usuario_x_tipo (idUsuario, idTipo_usuario)
        VALUES (v_idUsuario, 2);

        IF v_idBanco IS NOT NULL THEN
            INSERT IGNORE INTO anfitrion (
                idAnfitrion,
                razon_social,
                ruc,
                cuenta_bancaria,
                idBanco
            )
            VALUES (
                v_idUsuario,
                NULL,
                NULL,
                NULL,
                v_idBanco
            );
        END IF;

    END IF;
END$$

DELIMITER ;

SET @idBanco := (
    SELECT idBanco
    FROM banco
    ORDER BY idBanco
    LIMIT 1
);

INSERT IGNORE INTO usuario_x_tipo (idUsuario, idTipo_usuario)
SELECT u.idUsuario, 2
FROM usuario u
WHERE u.correo_electronico = 'maria@correo.com';

INSERT INTO anfitrion (
    idAnfitrion,
    razon_social,
    ruc,
    cuenta_bancaria,
    idBanco
)
SELECT
    u.idUsuario,
    'Eventos Lima SAC',
    '20123456789',
    '12345678901234',
    @idBanco
FROM usuario u
WHERE u.correo_electronico = 'maria@correo.com'
  AND @idBanco IS NOT NULL
ON DUPLICATE KEY UPDATE
    razon_social = IFNULL(razon_social, VALUES(razon_social)),
    ruc = IFNULL(ruc, VALUES(ruc)),
    cuenta_bancaria = IFNULL(cuenta_bancaria, VALUES(cuenta_bancaria)),
    idBanco = IFNULL(idBanco, VALUES(idBanco));

INSERT IGNORE INTO usuario_x_tipo (idUsuario, idTipo_usuario)
SELECT u.idUsuario, 1
FROM usuario u
WHERE u.correo_electronico = 'juan@correo.com';

INSERT IGNORE INTO usuario_x_tipo (idUsuario, idTipo_usuario)
SELECT u.idUsuario, 2
FROM usuario u
WHERE u.correo_electronico = 'juan@correo.com';

INSERT IGNORE INTO usuario_x_tipo (idUsuario, idTipo_usuario)
SELECT u.idUsuario, 3
FROM usuario u
WHERE u.correo_electronico = 'juan@correo.com';

INSERT IGNORE INTO anfitrion (
    idAnfitrion,
    razon_social,
    ruc,
    cuenta_bancaria,
    idBanco
)
SELECT
    u.idUsuario,
    NULL,
    NULL,
    NULL,
    @idBanco
FROM usuario u
WHERE u.correo_electronico = 'juan@correo.com'
  AND @idBanco IS NOT NULL;