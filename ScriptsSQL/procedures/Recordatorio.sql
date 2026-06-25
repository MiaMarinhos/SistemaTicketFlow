USE `ticket_flow`;

-- Listar eventos que proximos;

drop procedure IF EXISTS SP_LISTAR_EVENTOS_PROXIMOS;

DELIMITER //
create procedure SP_LISTAR_EVENTOS_PROXIMOS()
BEGIN
SELECT *
FROM evento
WHERE recordatorio_enviado = 0
AND TIMESTAMP(fecha, hora_inicio)
    BETWEEN NOW()
    AND DATE_ADD(NOW(), INTERVAL 24 HOUR);
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

CREATE PROCEDURE SP_MARCAR_COMO_ENVIADO(in idCompra int)
BEGIN
update compras
set recordatorio_enviado=true;
end//