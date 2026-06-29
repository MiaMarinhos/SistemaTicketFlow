
-- Modificacion Pagos
ALTER TABLE pagos
MODIFY fecha_pago DATE NULL,
MODIFY comprobante VARCHAR(450) NULL;

ALTER TABLE pagos
MODIFY idPagos INT NOT NULL AUTO_INCREMENT;

ALTER TABLE pagos
ADD UNIQUE KEY uq_pagos_evento (idEvento);

DROP PROCEDURE IF EXISTS sp_generar_pagos_eventos_finalizados;

DELIMITER $$

CREATE PROCEDURE sp_generar_pagos_eventos_finalizados()
BEGIN
    DECLARE v_idEstadoPendiente INT DEFAULT NULL;

    SELECT idestado_pagos
    INTO v_idEstadoPendiente
    FROM estado_pagos
    WHERE UPPER(estado) = 'PENDIENTE'
    LIMIT 1;

    IF v_idEstadoPendiente IS NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No existe el estado PENDIENTE en estado_pagos.';
    END IF;

    INSERT INTO pagos (
        fecha_pago,
        fecha_limite_pago,
        total_a_pagar,
        comprobante,
        idEvento,
        idEstado
    )
    SELECT
        NULL AS fecha_pago,
        DATE_ADD(e.fecha, INTERVAL 7 DAY) AS fecha_limite_pago,
        COALESCE(
            SUM(
                CASE
                    WHEN UPPER(ec.estado) = 'CONFIRMADO'
                    THEN c.monto_total
                    ELSE 0
                END
            ),
            0
        ) AS total_a_pagar,
        NULL AS comprobante,
        e.idEvento,
        v_idEstadoPendiente AS idEstado
    FROM evento e
    INNER JOIN estado_evento ee
        ON e.idEstado_evento = ee.idEstado_evento
    LEFT JOIN compras c
        ON e.idEvento = c.idEvento
    LEFT JOIN estado_compras ec
        ON c.idEstado = ec.idEstado
    LEFT JOIN pagos p
        ON e.idEvento = p.idEvento
    WHERE UPPER(ee.estado) = 'FINALIZADO'
      AND p.idPagos IS NULL
    GROUP BY
        e.idEvento,
        e.fecha;
END$$

DELIMITER ;

SELECT * FROM evento;
SELECT * FROM estado_evento;
SELECT * FROM pagos;

SET @idEstadoFinalizado = (
    SELECT idEstado_evento
    FROM estado_evento
    WHERE UPPER(estado) = 'FINALIZADO'
    LIMIT 1
);

INSERT INTO evento(
    idEvento,
    titulo,
    descripcion,
    capacidad_entradas,
    entradas_disponibles,
    fecha,
    hora_inicio,
    hora_fin,
    ubicacion,
    nombre_establecimiento,
    img,
    precio,
    idDistrito,
    idAnfitrion,
    idCategoria_evento,
    idEstado_publicacion,
    idEstado_evento
)
VALUES
(37, 'Festival Rock Lima Finalizado',
 'Evento musical finalizado para prueba de pagos a anfitriones',
 500, 90, '2026-05-10', '19:00:00', '22:00:00',
 'Av. Javier Prado 123', 'Arena Lima', 'festival_rock_finalizado.jpg',
 80.00, 1, 2, 1, 1, @idEstadoFinalizado),

(38, 'Expo Tecnologia Finalizada',
 'Feria tecnologica finalizada para generar pago pendiente',
 300, 80, '2026-05-15', '10:00:00', '18:00:00',
 'Av. La Marina 456', 'Centro de Convenciones Lima', 'expo_tecnologia_finalizada.jpg',
 50.00, 1, 2, 1, 1, @idEstadoFinalizado),

(39, 'Obra Hamlet Finalizada',
 'Presentacion teatral finalizada para prueba de liquidacion',
 200, 70, '2026-05-20', '20:00:00', '22:00:00',
 'Jr. Ica 789', 'Teatro Municipal', 'hamlet_finalizado.jpg',
 65.00, 1, 2, 1, 1, @idEstadoFinalizado),

(40, 'Maraton Lima Finalizada',
 'Evento deportivo finalizado para generar saldo neto',
 800, 60, '2026-05-25', '07:00:00', '12:00:00',
 'Costa Verde', 'Circuito Costa Verde', 'maraton_finalizada.jpg',
 40.00, 1, 2, 1, 1, @idEstadoFinalizado),

(41, 'Feria Gastronomica Finalizada',
 'Feria gastronomica finalizada para prueba de pagos',
 400, 70, '2026-05-30', '12:00:00', '20:00:00',
 'Parque de la Exposicion', 'Parque de la Exposicion', 'feria_gastronomica_finalizada.jpg',
 35.00, 1, 2, 1, 1, @idEstadoFinalizado);


