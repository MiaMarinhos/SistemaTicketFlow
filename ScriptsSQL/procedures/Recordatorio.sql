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

CREATE PROCEDURE SP_MARCAR_COMO_ENVIADO(in idCompra int)
BEGIN
update compras
set recordatorio_enviado=true;
end//

DELIMITER ;

DROP PROCEDURE IF EXISTS SP_MARCAR_COMO_ENVIADO_2;

DELIMITER //

CREATE PROCEDURE SP_MARCAR_COMO_ENVIADO_2(in idCompra int)
BEGIN
update compras
set recordatorio2_enviado=true;
end//


