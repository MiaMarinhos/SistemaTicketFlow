USE `ticket_flow`;

-----------------------------------
--COMPRA
-----------------------------------

--Create
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_create_compras $$

CREATE PROCEDURE sp_create_compras (
    IN p_idCompras INT,
    IN p_entradas_compradas INT,
    IN p_fecha_compra DATE,
    IN p_metodo_pago VARCHAR(10),
    IN p_hora_compra TIME,
    IN p_monto_parcial DOUBLE,
    IN p_monto_total DOUBLE,
    IN p_idPuntos_bonus INT,
    IN p_idCliente INT,
    IN p_idEvento INT,
    IN p_idEstado INT
)
BEGIN
INSERT INTO compras (
    idCompras, entradas_compradas, fecha_compra, metodo_pago,
    hora_compra, monto_parcial, monto_total, idPuntos_bonus,
    idCliente, idEvento, idEstado
)
VALUES (
           p_idCompras, p_entradas_compradas, p_fecha_compra, p_metodo_pago,
           p_hora_compra, p_monto_parcial, p_monto_total, p_idPuntos_bonus,
           p_idCliente, p_idEvento, p_idEstado
       );
END$$
DELIMITER ;

--Read
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_read_compras $$

CREATE PROCEDURE sp_read_compras (
    IN p_idCompras INT
)
BEGIN
SELECT
    idCompras, entradas_compradas, fecha_compra, metodo_pago,
    hora_compra, monto_parcial, monto_total, idPuntos_bonus,
    idCliente, idEvento, idEstado
FROM compras
WHERE idCompras = p_idCompras;
END$$
DELIMITER ;

--Update
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_update_compras $$

CREATE PROCEDURE sp_update_compras (
    IN p_idCompras INT,
    IN p_entradas_compradas INT,
    IN p_fecha_compra DATE,
    IN p_metodo_pago VARCHAR(10),
    IN p_hora_compra TIME,
    IN p_monto_parcial DOUBLE,
    IN p_monto_total DOUBLE,
    IN p_idPuntos_bonus INT,
    IN p_idCliente INT,
    IN p_idEvento INT,
    IN p_idEstado INT
)
BEGIN
UPDATE compras
SET
    entradas_compradas = p_entradas_compradas,
    fecha_compra = p_fecha_compra,
    metodo_pago = p_metodo_pago,
    hora_compra = p_hora_compra,
    monto_parcial = p_monto_parcial,
    monto_total = p_monto_total,
    idPuntos_bonus = p_idPuntos_bonus,
    idCliente = p_idCliente,
    idEvento = p_idEvento,
    idEstado = p_idEstado
WHERE idCompras = p_idCompras;
END$$
DELIMITER ;

--Delete
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_delete_compras $$

CREATE PROCEDURE sp_delete_compras (
    IN p_idCompras INT
)
BEGIN
DELETE FROM compras
WHERE idCompras = p_idCompras;
END$$
DELIMITER ;

--ListAll
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_listAll_compras $$

CREATE PROCEDURE sp_listAll_compras ()
BEGIN
SELECT
    idCompras, entradas_compradas, fecha_compra, metodo_pago,
    hora_compra, monto_parcial, monto_total, idPuntos_bonus,
    idCliente, idEvento, idEstado
FROM compras;
END$$
DELIMITER ;

--Listar compras por anfitrion
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_listar_compras_por_anfitrion $$

CREATE PROCEDURE sp_listar_compras_por_anfitrion(
    IN p_idAnfitrion INT
)
BEGIN
SELECT c.* FROM compras c
                    INNER JOIN evento e ON c.idEvento = e.idEvento
WHERE e.idAnfitrion = p_idAnfitrion;
END$$
DELIMITER ;

--Buscar Compra por Usuario
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_buscar_compra_usuario $$

CREATE PROCEDURE sp_buscar_compra_usuario(
    IN p_nombre VARCHAR(45)
)
BEGIN

SELECT
    c.*,
    u.nombre,
    e.titulo
FROM compras c
         INNER JOIN usuario u
                    ON c.idCliente = u.idUsuario
         INNER JOIN evento e
                    ON c.idEvento = e.idEvento
WHERE u.nombre LIKE CONCAT('%', p_nombre, '%');

END$$

DELIMITER ;

-- Filtrar Compras Por Estado

DELIMITER $$

DROP PROCEDURE IF EXISTS sp_filtrar_compras_estado $$

CREATE PROCEDURE sp_filtrar_compras_estado(
    IN p_idEstado INT
)
BEGIN
SELECT *
FROM compras
WHERE idEstado = p_idEstado;
END$$

DELIMITER ;

-----------------------------------
--ESTADO COMPRAS
-----------------------------------

--Create
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_create_estado_compras $$

CREATE PROCEDURE sp_create_estado_compras (
    OUT p_id INT,
    IN p_estado VARCHAR(45)
)
BEGIN
INSERT INTO estado_compras (
    estado
)
VALUES (
           p_estado
       );
SET p_id = LAST_INSERT_ID();
END$$
DELIMITER ;

--Read
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_read_estado_compras $$

CREATE PROCEDURE sp_read_estado_compras (
    IN p_idEstado INT
)
BEGIN
SELECT
    idEstado, estado
FROM estado_compras
WHERE idEstado = p_idEstado;
END$$
DELIMITER ;

--Update
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_update_estado_compras $$

CREATE PROCEDURE sp_update_estado_compras (
    IN p_idEstado INT,
    IN p_estado VARCHAR(45)
)
BEGIN
UPDATE estado_compras
SET
    estado = p_estado
WHERE idEstado = p_idEstado;
END$$
DELIMITER ;

--Delete
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_delete_estado_compras $$

CREATE PROCEDURE sp_delete_estado_compras (
    IN p_idEstado INT
)
BEGIN
DELETE FROM estado_compras
WHERE idEstado = p_idEstado;
END$$
DELIMITER ;

--ListAll
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_listAll_estado_compras $$

CREATE PROCEDURE sp_listAll_estado_compras ()
BEGIN
SELECT
    idEstado, estado
FROM estado_compras;
END$$
DELIMITER ;