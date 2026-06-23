USE `ticket_flow` ;
-- ---------------------------------
-- PUNTOS BONUS
-- ---------------------------------

-- Create
DELIMITER $$
drop procedure if exists sp_create_puntos_bonus;

CREATE PROCEDURE sp_create_puntos_bonus (
    OUT p_id INT,
    IN p_puntos_canjeables INT,
    IN p_descuento INT
)
BEGIN
INSERT INTO puntos_bonus (
    puntos_canjeables, descuento
)
VALUES (
           p_puntos_canjeables, p_descuento
       );
SET p_id = LAST_INSERT_ID();
END$$
DELIMITER ;

-- Read

drop procedure if exists sp_read_puntos_bonus;

DELIMITER $$
CREATE PROCEDURE sp_read_puntos_bonus (
    IN p_idPuntos_bonus INT
)
BEGIN
SELECT
    idPuntos_bonus, puntos_canjeables, descuento
FROM puntos_bonus
WHERE idPuntos_bonus = p_idPuntos_bonus;
END$$
DELIMITER ;

-- Update
drop procedure if exists sp_update_puntos_bonus;

DELIMITER $$
CREATE PROCEDURE sp_update_puntos_bonus (
    IN p_idPuntos_bonus INT,
    IN p_puntos_canjeables INT,
    IN p_descuento INT
)
BEGIN
UPDATE puntos_bonus
SET
    puntos_canjeables = p_puntos_canjeables,
    descuento = p_descuento
WHERE idPuntos_bonus = p_idPuntos_bonus;
END$$
DELIMITER ;

-- Delete

drop procedure if exists sp_delete_puntos_bonus;

DELIMITER $$
CREATE PROCEDURE sp_delete_puntos_bonus (
    IN p_idPuntos_bonus INT
)
BEGIN
DELETE FROM puntos_bonus
WHERE idPuntos_bonus = p_idPuntos_bonus;
END$$
DELIMITER ;

-- ListAll

drop procedure if exists sp_listAll_puntos_bonus;

DELIMITER $$
CREATE PROCEDURE sp_listAll_puntos_bonus ()
BEGIN
SELECT
    idPuntos_bonus, puntos_canjeables, descuento
FROM puntos_bonus;
END$$
DELIMITER ;
