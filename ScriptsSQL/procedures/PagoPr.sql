USE `ticket_flow`;
-- ---------------------------------
-- ESTADO PAGO
-- ---------------------------------
DROP PROCEDURE IF EXISTS sp_create_estado_pagos;
-- Create
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

DROP PROCEDURE IF EXISTS sp_read_estado_pagos; 
-- Read
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

DROP PROCEDURE IF EXISTS sp_update_estado_pagos; 
-- Update
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

DROP PROCEDURE IF EXISTS sp_delete_estado_pagos; 

-- Delete
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

DROP PROCEDURE IF EXISTS sp_listAll_estado_pagos $$

CREATE PROCEDURE sp_listAll_estado_pagos ()
BEGIN
SELECT
    idestado_pagos, estado
FROM estado_pagos;
END$$
DELIMITER ;

-- ---------------------------------
-- PAGOS
-- ---------------------------------

DROP PROCEDURE IF EXISTS sp_create_pagos; 

-- Create
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

-- Read

DROP PROCEDURE IF EXISTS sp_read_pagos; 

DELIMITER $$

CREATE PROCEDURE sp_read_pagos(
    IN p_idPagos INT
)
BEGIN
    SELECT
        p.idPagos,
        p.fecha_pago,
        p.fecha_limite_pago,
        p.total_a_pagar,
        p.comprobante,
        p.idEvento,
        p.idEstado,

        e.titulo AS evento,
        e.fecha AS fecha_evento,
        e.ubicacion AS ubicacion_evento,
        e.nombre_establecimiento AS establecimiento_evento,
        e.idEstado_evento AS idEstadoEvento,

        ee.estado AS estado_evento,

        a.idAnfitrion,
        a.razon_social AS usuario,
        a.ruc,
        a.cuenta_bancaria,

        b.idBanco,
        b.nombre_corto AS banco,
        b.nombre_largo AS banco_nombre_largo,

        ep.estado AS estado_pago

    FROM pagos p

    INNER JOIN evento e
        ON p.idEvento = e.idEvento

    INNER JOIN estado_evento ee
        ON e.idEstado_evento = ee.idEstado_evento

    INNER JOIN anfitrion a
        ON e.idAnfitrion = a.idAnfitrion

    LEFT JOIN banco b
        ON a.idBanco = b.idBanco

    INNER JOIN estado_pagos ep
        ON p.idEstado = ep.idestado_pagos

    WHERE p.idPagos = p_idPagos;
END$$

DELIMITER ;

-- Update
DROP PROCEDURE IF EXISTS sp_update_pagos; 

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

-- Delete

DROP PROCEDURE IF EXISTS sp_delete_pagos; 


DELIMITER $$
CREATE PROCEDURE sp_delete_pagos (
    IN p_idPagos INT
)
BEGIN
DELETE FROM pagos
WHERE idPagos = p_idPagos;
END$$
DELIMITER ;

--  ListAll

DROP PROCEDURE IF EXISTS sp_listAll_pagos; 


DELIMITER $$
CREATE PROCEDURE sp_listAll_pagos ()
BEGIN
SELECT
    idPagos, fecha_pago, fecha_limite_pago, total_a_pagar,
    comprobante, idEvento, idEstado
FROM pagos;
END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_listAll_pagos_Admin;

DELIMITER $$

CREATE PROCEDURE sp_listAll_pagos_Admin()
BEGIN
    SELECT
        p.idPagos,
        p.fecha_pago,
        p.fecha_limite_pago,
        p.total_a_pagar,
        p.comprobante,
        p.idEvento,
        p.idEstado,

        e.titulo AS evento,
        e.idEstado_evento AS idEstadoEvento,
        ee.estado AS estado_evento,

        a.razon_social AS usuario,

        b.nombre_corto AS banco,

        ep.estado AS estado_pago

    FROM pagos p

    INNER JOIN evento e
        ON p.idEvento = e.idEvento

    INNER JOIN estado_evento ee
        ON e.idEstado_evento = ee.idEstado_evento

    INNER JOIN anfitrion a
        ON e.idAnfitrion = a.idAnfitrion

    LEFT JOIN banco b
        ON a.idBanco = b.idBanco

    INNER JOIN estado_pagos ep
        ON p.idEstado = ep.idestado_pagos

    ORDER BY p.idPagos ASC;
END$$

DELIMITER ;

DELIMITER ;

-- Listar pagos por anfitrion
DROP PROCEDURE IF EXISTS sp_listar_pagos_por_anfitrion; 


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
DROP PROCEDURE IF EXISTS sp_buscar_pago_usuario; 

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

-- Filtrar Por Estado
DROP PROCEDURE IF EXISTS sp_filtrar_pagos_estado;

DELIMITER $$

CREATE PROCEDURE sp_filtrar_pagos_estado(
    IN p_idEstado INT
)
BEGIN
    SELECT
        p.idPagos,
        p.fecha_pago,
        p.fecha_limite_pago,
        p.total_a_pagar,
        p.comprobante,
        p.idEvento,
        p.idEstado,

        e.titulo AS evento,
        e.idEstado_evento AS idEstadoEvento,
        ee.estado AS estado_evento,

        a.razon_social AS usuario,

        b.nombre_corto AS banco,

        ep.estado AS estado_pago

    FROM pagos p

    INNER JOIN evento e
        ON p.idEvento = e.idEvento

    INNER JOIN estado_evento ee
        ON e.idEstado_evento = ee.idEstado_evento

    INNER JOIN anfitrion a
        ON e.idAnfitrion = a.idAnfitrion

    LEFT JOIN banco b
        ON a.idBanco = b.idBanco

    INNER JOIN estado_pagos ep
        ON p.idEstado = ep.idestado_pagos

    WHERE p.idEstado = p_idEstado

    ORDER BY p.idPagos ASC;
END$$

DELIMITER ;
DROP PROCEDURE IF EXISTS sp_filtrar_pagos_fecha;

DELIMITER $$

CREATE PROCEDURE sp_filtrar_pagos_fecha(
    IN p_fecha DATE
)
BEGIN
    SELECT
        p.idPagos,
        p.fecha_pago,
        p.fecha_limite_pago,
        p.total_a_pagar,
        p.comprobante,
        p.idEvento,
        p.idEstado,

        e.titulo AS evento,
        e.idEstado_evento AS idEstadoEvento,
        ee.estado AS estado_evento,

        a.razon_social AS usuario,

        b.nombre_corto AS banco,

        ep.estado AS estado_pago

    FROM pagos p

    INNER JOIN evento e
        ON p.idEvento = e.idEvento

    INNER JOIN estado_evento ee
        ON e.idEstado_evento = ee.idEstado_evento

    INNER JOIN anfitrion a
        ON e.idAnfitrion = a.idAnfitrion

    LEFT JOIN banco b
        ON a.idBanco = b.idBanco

    INNER JOIN estado_pagos ep
        ON p.idEstado = ep.idestado_pagos

    WHERE p.fecha_pago = p_fecha

    ORDER BY p.idPagos ASC;
END$$


DELIMITER ;


-- Confirmar Transferencia a Anfitrión
ALTER TABLE pagos
MODIFY comprobante VARCHAR(450) NOT NULL;

DROP PROCEDURE IF EXISTS sp_confirmar_transferencia_pago;

DELIMITER $$

CREATE PROCEDURE sp_confirmar_transferencia_pago(
    IN p_idPagos INT,
    IN p_comprobante VARCHAR(450)
)
BEGIN
    DECLARE v_existePago INT DEFAULT 0;
    DECLARE v_idEstadoPagado INT DEFAULT NULL;
    DECLARE v_estadoEvento VARCHAR(45);
    DECLARE v_estadoPagoActual VARCHAR(45);

    SELECT COUNT(*)
    INTO v_existePago
    FROM pagos
    WHERE idPagos = p_idPagos;

    IF v_existePago = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se encontró el pago seleccionado.';
    END IF;

    SELECT idestado_pagos
    INTO v_idEstadoPagado
    FROM estado_pagos
    WHERE UPPER(estado) = 'PAGADO'
    LIMIT 1;

    IF v_idEstadoPagado IS NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No existe el estado PAGADO en estado_pagos.';
    END IF;

    SELECT
        ee.estado,
        ep.estado
    INTO
        v_estadoEvento,
        v_estadoPagoActual
    FROM pagos p
    INNER JOIN evento e
        ON p.idEvento = e.idEvento
    INNER JOIN estado_evento ee
        ON e.idEstado_evento = ee.idEstado_evento
    INNER JOIN estado_pagos ep
        ON p.idEstado = ep.idestado_pagos
    WHERE p.idPagos = p_idPagos;

    IF UPPER(v_estadoPagoActual) = 'PAGADO' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Este pago ya fue confirmado.';
    END IF;

    IF UPPER(v_estadoEvento) <> 'FINALIZADO' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Solo se puede confirmar la transferencia de eventos finalizados.';
    END IF;

    UPDATE pagos
    SET
        idEstado = v_idEstadoPagado,
        fecha_pago = CURDATE(),
        comprobante = p_comprobante
    WHERE idPagos = p_idPagos;
END$$

DELIMITER ;

INSERT INTO pagos(
    idPagos,
    fecha_pago,
    fecha_limite_pago,
    total_a_pagar,
    comprobante,
    idEvento,
    idEstado
)
VALUES
(
    4,
    '2026-06-14',
    '2026-06-28',
    350.00,
    'COMP-003.pdf',
    8,
    3
);

