USE `ticket_flow`;
-----------------------------------
--ESTADO PAGO
-----------------------------------

--Create
DELIMITER $$
CREATE PROCEDURE sp_create_estado_pagos (
    OUT p_id INT,
    IN p_estado VARCHAR(45)
)
BEGIN
INSERT INTO estado_pagos (
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
CREATE PROCEDURE sp_read_estado_pagos (
    IN p_idestado_pagos INT
)
BEGIN
SELECT
    idestado_pagos, estado
FROM estado_pagos
WHERE idestado_pagos = p_idestado_pagos;
END$$
DELIMITER ;

--Update
DELIMITER $$
CREATE PROCEDURE sp_update_estado_pagos (
    IN p_idestado_pagos INT,
    IN p_estado VARCHAR(45)
)
BEGIN
UPDATE estado_pagos
SET
    estado = p_estado
WHERE idestado_pagos = p_idestado_pagos;
END$$
DELIMITER ;

--Delete
DELIMITER $$
CREATE PROCEDURE sp_delete_estado_pagos (
    IN p_idestado_pagos INT
)
BEGIN
DELETE FROM estado_pagos
WHERE idestado_pagos = p_idestado_pagos;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE sp_listAll_estado_pagos ()
BEGIN
SELECT
    idestado_pagos, estado
FROM estado_pagos;
END$$
DELIMITER ;

-----------------------------------
--PAGOS
-----------------------------------

--Create
DELIMITER $$
CREATE PROCEDURE sp_create_pagos (
    IN p_idPagos INT,
    IN p_fecha_pago DATE,
    IN p_fecha_limite_pago DATE,
    IN p_total_a_pagar DOUBLE,
    IN p_comprobante VARCHAR(45),
    IN p_idEvento INT,
    IN p_idEstado INT
)
BEGIN
INSERT INTO pagos (
    idPagos, fecha_pago, fecha_limite_pago, total_a_pagar,
    comprobante, idEvento, idEstado
)
VALUES (
           p_idPagos, p_fecha_pago, p_fecha_limite_pago, p_total_a_pagar,
           p_comprobante, p_idEvento, p_idEstado
       );
END$$
DELIMITER ;

--Read
DELIMITER $$
CREATE PROCEDURE sp_read_pagos (
    IN p_idPagos INT
)
BEGIN
SELECT
    idPagos, fecha_pago, fecha_limite_pago, total_a_pagar,
    comprobante, idEvento, idEstado
FROM pagos
WHERE idPagos = p_idPagos;
END$$
DELIMITER ;

--Update
DELIMITER $$
CREATE PROCEDURE sp_update_pagos (
    IN p_idPagos INT,
    IN p_fecha_pago DATE,
    IN p_fecha_limite_pago DATE,
    IN p_total_a_pagar DOUBLE,
    IN p_comprobante VARCHAR(45),
    IN p_idEvento INT,
    IN p_idEstado INT
)
BEGIN
UPDATE pagos
SET
    fecha_pago = p_fecha_pago,
    fecha_limite_pago = p_fecha_limite_pago,
    total_a_pagar = p_total_a_pagar,
    comprobante = p_comprobante,
    idEvento = p_idEvento,
    idEstado = p_idEstado
WHERE idPagos = p_idPagos;
END$$
DELIMITER ;

--Delete
DELIMITER $$
CREATE PROCEDURE sp_delete_pagos (
    IN p_idPagos INT
)
BEGIN
DELETE FROM pagos
WHERE idPagos = p_idPagos;
END$$
DELIMITER ;

--ListAll
DELIMITER $$
CREATE PROCEDURE sp_listAll_pagos ()
BEGIN
SELECT
    idPagos, fecha_pago, fecha_limite_pago, total_a_pagar,
    comprobante, idEvento, idEstado
FROM pagos;
END$$
DELIMITER ;

--Listar pagos por anfitrion
DELIMITER $$

CREATE PROCEDURE sp_listar_pagos_por_anfitrion(
    IN p_idAnfitrion INT
)
BEGIN
    -- Seleccionamos todos los campos de pago y hacemos join con evento
SELECT p.* FROM pagos p
                    INNER JOIN evento e ON p.idEvento = e.idEvento
WHERE e.idAnfitrion = p_idAnfitrion;
END$$

DELIMITER ;

-- Buscar Por Nombre
DELIMITER $$

CREATE PROCEDURE sp_buscar_pago_usuario(
    IN p_nombre VARCHAR(45)
)
BEGIN

SELECT
    p.*,
    u.nombre,
    e.titulo
FROM pagos p
         INNER JOIN compra c
                    ON p.idCompra = c.idCompra
         INNER JOIN usuario u
                    ON c.idUsuario = u.idUsuario
         INNER JOIN evento e
                    ON p.idEvento = e.idEvento
WHERE u.nombre LIKE CONCAT('%', p_nombre, '%');

END$$

DELIMITER ;

--Filtrar Por Estado
DELIMITER $$

CREATE PROCEDURE sp_filtrar_pagos_estado(
    IN p_idEstado INT
)
BEGIN
SELECT *
FROM pagos
WHERE idEstado = p_idEstado;
END$$

DELIMITER ;

