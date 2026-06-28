use ticket_flow;

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
END$$

DELIMITER ;