USE `ticket_flow` ;
-- ============================
-- REGION
-- ============================

-- CREATE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_INSERTAR_REGION //

CREATE PROCEDURE SP_INSERTAR_REGION(
    OUT p_idRegion INT,
    IN p_nombre VARCHAR(45)
)
BEGIN
INSERT INTO region (nombre)
VALUES (p_nombre);

SET p_idRegion = LAST_INSERT_ID();
END //

DELIMITER ;


-- READ
DELIMITER //

DROP PROCEDURE IF EXISTS SP_LEER_REGION //

CREATE PROCEDURE SP_LEER_REGION(
    IN p_idRegion INT
)
BEGIN
SELECT
    idRegion,
    nombre
FROM region
WHERE idRegion = p_idRegion;
END //

DELIMITER ;


-- UPDATE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_ACTUALIZAR_REGION //

CREATE PROCEDURE SP_ACTUALIZAR_REGION(
    IN p_idRegion INT,
    IN p_nombre VARCHAR(45)
)
BEGIN
UPDATE region
SET nombre = p_nombre
WHERE idRegion = p_idRegion;
END //

DELIMITER ;


-- DELETE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_ELIMINAR_REGION //

CREATE PROCEDURE SP_ELIMINAR_REGION(
    IN p_idRegion INT
)
BEGIN
DELETE FROM region
WHERE idRegion = p_idRegion;
END //

DELIMITER ;


-- LIST ALL
DELIMITER //

DROP PROCEDURE IF EXISTS SP_LISTAR_REGIONES //

CREATE PROCEDURE SP_LISTAR_REGIONES()
BEGIN
SELECT
    idRegion,
    nombre
FROM region;
END //

DELIMITER ;


-- ============================
-- DISTRITO
-- ============================

-- CREATE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_INSERTAR_DISTRITO //

CREATE PROCEDURE SP_INSERTAR_DISTRITO(
    OUT p_idDistrito INT,
    IN p_nombre VARCHAR(45),
    IN p_idRegion INT
)
BEGIN
INSERT INTO distrito (
    nombre,
    idRegion
)
VALUES (
           p_nombre,
           p_idRegion
       );

SET p_idDistrito = LAST_INSERT_ID();
END //

DELIMITER ;


-- READ
DELIMITER //

DROP PROCEDURE IF EXISTS SP_LEER_DISTRITO //

CREATE PROCEDURE SP_LEER_DISTRITO(
    IN p_idDistrito INT
)
BEGIN
SELECT
    d.idDistrito,
    d.nombre AS nombre_distrito,
    r.idRegion,
    r.nombre AS nombre_region
FROM distrito d
         INNER JOIN region r
                    ON d.idRegion = r.idRegion
WHERE d.idDistrito = p_idDistrito;
END //

DELIMITER ;


-- READ
DELIMITER //

DROP PROCEDURE IF EXISTS SP_BUSCAR_DISTRITO_POR_NOMBRE //

CREATE PROCEDURE SP_BUSCAR_DISTRITO_POR_NOMBRE(
    IN p_nombre VARCHAR(45)
)
BEGIN
SELECT
    d.idDistrito,
    d.nombre AS nombre_distrito,
    r.idRegion,
    r.nombre AS nombre_region
FROM distrito d
         INNER JOIN region r
                    ON d.idRegion = r.idRegion
WHERE d.nombre = p_nombre
    LIMIT 1;
END //

DELIMITER ;


-- LIST ALL
DELIMITER //

DROP PROCEDURE IF EXISTS SP_LISTAR_DISTRITOS //

CREATE PROCEDURE SP_LISTAR_DISTRITOS()
BEGIN
SELECT
    d.idDistrito,
    d.nombre AS nombre_distrito,
    r.idRegion,
    r.nombre AS nombre_region
FROM distrito d
         INNER JOIN region r
                    ON d.idRegion = r.idRegion;
END //

DELIMITER ;


-- UPDATE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_ACTUALIZAR_DISTRITO //

CREATE PROCEDURE SP_ACTUALIZAR_DISTRITO(
    IN p_idDistrito INT,
    IN p_nombre VARCHAR(45),
    IN p_idRegion INT
)
BEGIN
UPDATE distrito
SET
    nombre = p_nombre,
    idRegion = p_idRegion
WHERE idDistrito = p_idDistrito;
END //

DELIMITER ;


-- DELETE
DELIMITER //

DROP PROCEDURE IF EXISTS SP_ELIMINAR_DISTRITO //

CREATE PROCEDURE SP_ELIMINAR_DISTRITO(
    IN p_idDistrito INT
)
BEGIN
DELETE FROM distrito
WHERE idDistrito = p_idDistrito;
END //

DELIMITER ;