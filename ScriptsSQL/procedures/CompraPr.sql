USE `ticket_flow`;
-----------------------------------
-- COMPRA
-----------------------------------

-- Create
------------------------------------------------------
DROP PROCEDURE IF EXISTS SP_REGISTRAR_COMPRA;

DELIMITER //

CREATE PROCEDURE SP_REGISTRAR_COMPRA(
    IN p_idCompra INT,
    IN p_entradasCompradas INT,
    IN p_metodoPago VARCHAR(50),
    IN p_montoParcial DOUBLE,
    IN p_montoTotal DOUBLE,
    IN p_idEstado INT,
    IN p_idpuntoBonus INT,
    IN p_idCliente INT,
    IN p_idEvento INT
)
bloque_compra: BEGIN
    -- Variables locales para el cálculo de puntos
    DECLARE v_puntos_canjeables INT DEFAULT 0;
    DECLARE v_puntos_a_descontar INT DEFAULT 0;

    -- Manejo de errores básico: si algo falla, deshace los cambios (Rollback)
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
    END;

    START TRANSACTION;

    -- 1. Insertar la compra (ignorando la columna ENUM 'estado' ya que usas idEstado)
    INSERT INTO compras (
        idCompras, entradas_compradas, fecha_compra, metodo_pago, 
        hora_compra, monto_parcial, monto_total, 
        idPuntos_bonus, idCliente, idEvento, idEstado
    ) 
    VALUES (
        p_idCompra, p_entradasCompradas, CURDATE(), p_metodoPago, 
        CURTIME(), p_montoParcial, p_montoTotal, 
        p_idpuntoBonus, p_idCliente, p_idEvento, p_idEstado
    );

    -- 2. Disminuir las entradas disponibles del evento (usando 'capacidad_entradas' de tu script)
    UPDATE evento 
    SET capacidad_entradas = capacidad_entradas - p_entradasCompradas
    WHERE idEvento = p_idEvento;

    -- 3. Verificar si el evento se quedó sin stock para cambiar su estado a AGOTADO (ID: 5)
    UPDATE evento
    SET idEstado_evento = 5
    WHERE idEvento = p_idEvento AND capacidad_entradas <= 0;

    -- 4. PROCESAMIENTO DE PUNTOS BONUS
    -- Si el id de puntos bonus es diferente de 4, calculamos el descuento de puntos
    IF p_idpuntoBonus <> 4 THEN
        -- Obtener los puntos canjeables de la tabla de configuración
        SELECT puntos_canjeables INTO v_puntos_canjeables
        FROM puntos_bonus
        WHERE idPuntos_bonus = p_idpuntoBonus;
        
        -- Aplicar tu ecuación para hallar los puntos a descontar
        SET v_puntos_a_descontar = (v_puntos_canjeables * p_entradasCompradas) - ((p_entradasCompradas - 1) * (v_puntos_canjeables / 10));
        
        -- Restar los puntos utilizados al cliente
        UPDATE cliente 
        SET puntos_bonus = puntos_bonus - v_puntos_a_descontar
        WHERE idCliente = p_idCliente;
    END IF;

    -- 5. Sumar los puntos bonus al cliente por su fidelidad (+10) al final de todo
    UPDATE cliente 
    SET puntos_bonus = puntos_bonus + (10*p_entradasCompradas)
    WHERE idCliente = p_idCliente;

    COMMIT;
END //

DELIMITER ;
------------------------------

DELIMITER $$

DROP PROCEDURE IF EXISTS sp_read_compras $$

CREATE PROCEDURE sp_read_compras (
    IN p_idCompras INT
)
BEGIN
    SELECT
        c.idCompras AS idCompra,
        c.entradas_compradas AS entradasCompradas,
        c.fecha_compra AS fechaCompra,
        c.hora_compra AS horaCompra,
        c.metodo_pago AS metodoPago,
        c.monto_parcial AS montoParcial,
        c.monto_total AS montoTotal,
        c.idPuntos_bonus AS idPuntoBonus,

        c.idCliente AS idCliente,
        u.nombre AS nombreCliente,
        u.apellido_paterno AS apellidoPaternoCliente,
        u.apellido_materno AS apellidoMaternoCliente,

        c.idEvento AS idEvento,
        e.titulo AS tituloEvento,

        c.idEstado AS idEstado,
        ec.estado AS estadoCompra,

        c.recordatorio_enviado AS recordatorioEnviado

    FROM compras c
    INNER JOIN cliente cl ON c.idCliente = cl.idCliente
    INNER JOIN usuario u ON cl.idCliente = u.idUsuario
    INNER JOIN evento e ON c.idEvento = e.idEvento
    INNER JOIN estado_compras ec ON c.idEstado = ec.idEstado

    WHERE c.idCompras = p_idCompras;
END$$

DELIMITER ;

-- Update
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

-- Delete
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

-- ListAll
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_listAll_compras_admin $$

CREATE PROCEDURE sp_listAll_compras_admin()
BEGIN
    SELECT
        c.idCompras AS idCompra,
        c.entradas_compradas AS entradasCompradas,
        c.fecha_compra AS fechaCompra,
        c.hora_compra AS horaCompra,
        c.metodo_pago AS metodoPago,
        c.monto_parcial AS montoParcial,
        c.monto_total AS montoTotal,
        c.idPuntos_bonus AS idPuntoBonus,

        c.idCliente AS idCliente,
        u.nombre AS nombreCliente,
        u.apellido_paterno AS apellidoPaternoCliente,
        u.apellido_materno AS apellidoMaternoCliente,

        c.idEvento AS idEvento,
        e.titulo AS tituloEvento,

        c.idEstado AS idEstado,
        ec.estado AS estadoCompra,

        c.recordatorio_enviado AS recordatorioEnviado

    FROM compras c
    INNER JOIN cliente cl ON c.idCliente = cl.idCliente
    INNER JOIN usuario u ON cl.idCliente = u.idUsuario
    INNER JOIN evento e ON c.idEvento = e.idEvento
    INNER JOIN estado_compras ec ON c.idEstado = ec.idEstado

    ORDER BY c.idCompras ASC;
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

--Listar compras por cliente
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_listar_compras_por_cliente $$

CREATE PROCEDURE sp_listar_compras_por_cliente(
    IN p_idCliente INT
)
BEGIN
SELECT c.* FROM compras c
WHERE c.idCliente = p_idCliente;
END$$
DELIMITER ;



-- Buscar Compra por Usuario
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
    SELECT
        c.idCompras AS idCompra,
        c.entradas_compradas AS entradasCompradas,
        c.fecha_compra AS fechaCompra,
        c.hora_compra AS horaCompra,
        c.metodo_pago AS metodoPago,
        c.monto_parcial AS montoParcial,
        c.monto_total AS montoTotal,
        c.idPuntos_bonus AS idPuntoBonus,

        c.idCliente AS idCliente,
        u.nombre AS nombreCliente,
        u.apellido_paterno AS apellidoPaternoCliente,
        u.apellido_materno AS apellidoMaternoCliente,

        c.idEvento AS idEvento,
        e.titulo AS tituloEvento,

        c.idEstado AS idEstado,
        ec.estado AS estadoCompra,

        c.recordatorio_enviado AS recordatorioEnviado

    FROM compras c
    INNER JOIN cliente cl ON c.idCliente = cl.idCliente
    INNER JOIN usuario u ON cl.idCliente = u.idUsuario
    INNER JOIN evento e ON c.idEvento = e.idEvento
    INNER JOIN estado_compras ec ON c.idEstado = ec.idEstado

    WHERE c.idEstado = p_idEstado

    ORDER BY c.idCompras ASC;
END$$

DELIMITER ;

-- ---------------------------------
-- ESTADO COMPRAS
-- ---------------------------------

-- Create
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

-- Update
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

-- Delete
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

-- ListAll
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_listAll_estado_compras $$

CREATE PROCEDURE sp_listAll_estado_compras ()
BEGIN
SELECT
    idEstado, estado
FROM estado_compras;
END$$
DELIMITER ;

-- verificar si el cliente ingreso al evento o no.
DROP PROCEDURE IF EXISTS sp_validar_ingreso_cliente;
DELIMITER //

CREATE PROCEDURE sp_validar_ingreso_cliente(
    IN p_idCompras INT
)
bloque_proceso: BEGIN
    DECLARE v_id_estado_actual INT DEFAULT NULL;

    SELECT idEstado INTO v_id_estado_actual
    FROM compras
    WHERE idCompras = p_idCompras;

    IF v_id_estado_actual IS NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: El código de ticket de compra no existe en el sistema.';
    END IF;

    IF v_id_estado_actual = 2 THEN
        UPDATE compras
        SET idEstado = 4
        WHERE idCompras = p_idCompras;
        
        SELECT '¡Ingreso autorizado con éxito! Entrada válida.' AS mensaje;

    ELSEIF v_id_estado_actual = 4 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Acceso Denegado: Esta entrada ya fue utilizada para ingresar.';

    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Acceso Denegado: La compra no se encuentra en estado CONFIRMADA.';
    END IF;

END //

DELIMITER ;

SELECT idEstado FROM compras WHERE idCompras = 1;
