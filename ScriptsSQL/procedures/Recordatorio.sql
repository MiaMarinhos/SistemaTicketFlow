USE `ticket_flow`;

-- Listar eventos que proximos;

drop procedure IF EXISTS SP_LISTAR_EVENTOS_PROXIMOS;

DELIMITER //

CREATE PROCEDURE SP_LISTAR_EVENTOS_PROXIMOS()
BEGIN

SELECT *
FROM evento
WHERE TIMESTAMP(fecha, hora_inicio)
BETWEEN CONVERT_TZ(NOW(), '+00:00', '-05:00')
AND DATE_ADD(CONVERT_TZ(NOW(), '+00:00', '-05:00'), INTERVAL 24 HOUR);

END//

DELIMITER ;
drop procedure IF EXISTS SP_LISTAR_COMPRAS_A_RECORDAR;

DELIMITER //

create procedure SP_LISTAR_COMPRAS_A_RECORDAR(IN p_idEvento INT)
BEGIN
select * from compras 
where idEvento=p_idEvento;
END//

DELIMITER ;

-- Marcar como enviado

DROP PROCEDURE IF EXISTS SP_MARCAR_COMO_ENVIADO;

DELIMITER //

CREATE PROCEDURE SP_MARCAR_COMO_ENVIADO(IN p_idCompra INT)
BEGIN
    SELECT p_idCompra;

    UPDATE compras
    SET recordatorio_enviado = TRUE
    WHERE idCompras = p_idCompra;
END//

DELIMITER ;

DROP PROCEDURE IF EXISTS SP_MARCAR_COMO_ENVIADO_2;

DELIMITER //

CREATE PROCEDURE SP_MARCAR_COMO_ENVIADO_2(IN p_idCompra INT)
BEGIN
    UPDATE compras
    SET recordatorio2_enviado = TRUE
    WHERE idCompras = p_idCompra;
END//

DELIMITER ;

