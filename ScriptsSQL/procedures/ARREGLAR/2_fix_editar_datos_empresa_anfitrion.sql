USE ticket_flow;

DROP PROCEDURE IF EXISTS SP_ACTUALIZAR_DATOS_EMPRESA_ANFITRION;

DELIMITER $$

CREATE PROCEDURE SP_ACTUALIZAR_DATOS_EMPRESA_ANFITRION(
    IN p_idAnfitrion INT,
    IN p_razon_social VARCHAR(45),
    IN p_ruc VARCHAR(45),
    IN p_cuenta_bancaria VARCHAR(45),
    IN p_idBanco INT
)
BEGIN
    UPDATE anfitrion
    SET
        razon_social = p_razon_social,
        ruc = p_ruc,
        cuenta_bancaria = p_cuenta_bancaria,
        idBanco = p_idBanco
    WHERE idAnfitrion = p_idAnfitrion;
END$$

DELIMITER ;