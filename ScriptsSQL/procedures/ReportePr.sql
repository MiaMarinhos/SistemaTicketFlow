-- Reporte Ventas
DELIMITER $$

drop procedure if exists USP_REPORTE_VENTAS;

CREATE PROCEDURE USP_REPORTE_VENTAS(
    IN p_fechaInicio DATE,
    IN p_fechaFin DATE,
    IN p_idCategoria INT
)
BEGIN
SELECT
    ce.nombre AS categoria,
    COUNT(c.idCompras) AS cantidad_ventas,
    SUM(c.monto_total) AS total_ventas
FROM compras c
         INNER JOIN evento e
                    ON c.idEvento = e.idEvento
         INNER JOIN categoria_evento ce
                    ON e.idCategoria_evento = ce.idCategoria_evento
WHERE c.fecha_compra BETWEEN p_fechaInicio AND p_fechaFin
  AND (p_idCategoria = 0 OR e.idCategoria_evento = p_idCategoria)
GROUP BY ce.nombre;
END$$

DELIMITER ;

drop procedure if exists USP_REPORTE_FIDELIZACION;
-- Reporte Fidelizacion
DELIMITER $$

CREATE PROCEDURE USP_REPORTE_FIDELIZACION()
BEGIN
SELECT
    CONCAT(u.nombre, ' ', u.apellido_paterno) AS usuario,
    cl.puntos_bonus AS puntos_acumulados,
    COALESCE(SUM(pb.puntos_canjeables), 0) AS puntos_canjeados
FROM cliente cl
         INNER JOIN usuario u
                    ON cl.idCliente = u.idUsuario
         LEFT JOIN compras c
                   ON cl.idCliente = c.idCliente
         LEFT JOIN puntos_bonus pb
                   ON c.idPuntos_bonus = pb.idPuntos_bonus
GROUP BY
    u.idUsuario,
    u.nombre,
    u.apellido_paterno,
    cl.puntos_bonus
ORDER BY puntos_acumulados DESC;
END$$

DELIMITER ;

drop procedure if exists USP_REPORTE_OCUPACION_EVENTOS;

-- Reporte Ocupacion Eventos
DELIMITER $$

CREATE PROCEDURE USP_REPORTE_OCUPACION_EVENTOS()
BEGIN
SELECT
    e.titulo AS evento,
    e.capacidad_entradas AS capacidad,
    COALESCE(SUM(c.entradas_compradas), 0) AS entradas_vendidas,
    ROUND(
            (COALESCE(SUM(c.entradas_compradas), 0) / e.capacidad_entradas) * 100,
            2
    ) AS porcentaje_ocupacion
FROM evento e
         LEFT JOIN compras c
                   ON e.idEvento = c.idEvento
GROUP BY
    e.idEvento,
    e.titulo,
    e.capacidad_entradas
ORDER BY porcentaje_ocupacion DESC;
END$$

DELIMITER ;