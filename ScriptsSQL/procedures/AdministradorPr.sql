USE `ticket_flow`;
-- -----------------------------------------------------
-- PROCEDURES PARA CAPA DE ADMINISTRADOR
-- -----------------------------------------------------

DELIMITER //

DROP PROCEDURE IF EXISTS SP_INSERTAR_ADMINISTRADOR //
CREATE PROCEDURE SP_INSERTAR_ADMINISTRADOR(
    IN p_idAdministrador INT,
    IN p_codigo VARCHAR(45),
    IN p_nombre VARCHAR(45),
    IN p_apellido_paterno VARCHAR(45),
    IN p_apellido_materno VARCHAR(45),
    IN p_dni VARCHAR(45),
    IN p_contrasena VARCHAR(45)
)
BEGIN
INSERT INTO administrador(
    idAdministrador,
    codigo,
    nombre,
    apellido_paterno,
    apellido_materno,
    dni,
    contrasena
)
VALUES(
          p_idAdministrador,
          p_codigo,
          p_nombre,
          p_apellido_paterno,
          p_apellido_materno,
          p_dni,
          p_contrasena
      );
END //

DROP PROCEDURE IF EXISTS SP_LEER_ADMINISTRADOR //
CREATE PROCEDURE SP_LEER_ADMINISTRADOR(
    IN p_idAdministrador INT
)
BEGIN
SELECT idAdministrador,
       codigo,
       nombre,
       apellido_paterno,
       apellido_materno,
       dni,
       contrasena
FROM administrador
WHERE idAdministrador = p_idAdministrador;
END //

DROP PROCEDURE IF EXISTS SP_ACTUALIZAR_ADMINISTRADOR //
CREATE PROCEDURE SP_ACTUALIZAR_ADMINISTRADOR(
    IN p_idAdministrador INT,
    IN p_codigo VARCHAR(45),
    IN p_nombre VARCHAR(45),
    IN p_apellido_paterno VARCHAR(45),
    IN p_apellido_materno VARCHAR(45),
    IN p_dni VARCHAR(45),
    IN p_contrasena VARCHAR(45)
)
BEGIN
UPDATE administrador
SET codigo = p_codigo,
    nombre = p_nombre,
    apellido_paterno = p_apellido_paterno,
    apellido_materno = p_apellido_materno,
    dni = p_dni,
    contrasena = p_contrasena
WHERE idAdministrador = p_idAdministrador;
END //

DROP PROCEDURE IF EXISTS SP_ELIMINAR_ADMINISTRADOR //
CREATE PROCEDURE SP_ELIMINAR_ADMINISTRADOR(
    IN p_idAdministrador INT
)
BEGIN
DELETE FROM administrador
WHERE idAdministrador = p_idAdministrador;
END //

DROP PROCEDURE IF EXISTS SP_LISTAR_ADMINISTRADORES //
CREATE PROCEDURE SP_LISTAR_ADMINISTRADORES()
BEGIN
SELECT idAdministrador,
       codigo,
       nombre,
       apellido_paterno,
       apellido_materno,
       dni,
       contrasena
FROM administrador
ORDER BY idAdministrador;
END //

DELIMITER ;