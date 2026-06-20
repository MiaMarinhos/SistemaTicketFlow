USE `ticket_flow`;
-----------------------------------
--BANCO
-----------------------------------
-- CREATE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_INSERTAR_BANCO //

CREATE PROCEDURE SP_INSERTAR_BANCO(
    OUT p_idBanco INT,
    IN p_nombre_largo VARCHAR(45),
    IN p_nombre_corto VARCHAR(45)
)
BEGIN
INSERT INTO banco (
    nombre_largo,
    nombre_corto
)
VALUES (
           p_nombre_largo,
           p_nombre_corto
       );

SET p_idBanco = LAST_INSERT_ID();
END //

DELIMITER ;


-- READ
DELIMITER //

DROP PROCEDURE IF EXISTS SP_LEER_BANCO //

CREATE PROCEDURE SP_LEER_BANCO(
    IN p_idBanco INT
)
BEGIN
SELECT
    idBanco,
    nombre_largo,
    nombre_corto
FROM banco
WHERE idBanco = p_idBanco;
END //

DELIMITER ;


-- UPDATE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_ACTUALIZAR_BANCO //

CREATE PROCEDURE SP_ACTUALIZAR_BANCO(
    IN p_idBanco INT,
    IN p_nombre_largo VARCHAR(45),
    IN p_nombre_corto VARCHAR(45)
)
BEGIN
UPDATE banco
SET
    nombre_largo = p_nombre_largo,
    nombre_corto = p_nombre_corto
WHERE idBanco = p_idBanco;
END //

DELIMITER ;


-- DELETE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_ELIMINAR_BANCO //

CREATE PROCEDURE SP_ELIMINAR_BANCO(
    IN p_idBanco INT
)
BEGIN
DELETE FROM banco
WHERE idBanco = p_idBanco;
END //

DELIMITER ;


-- LIST ALL
DELIMITER //

DROP PROCEDURE IF EXISTS SP_LISTAR_BANCOS //

CREATE PROCEDURE SP_LISTAR_BANCOS()
BEGIN
SELECT
    idBanco,
    nombre_largo,
    nombre_corto
FROM banco
ORDER BY idBanco;
END //

DELIMITER ;