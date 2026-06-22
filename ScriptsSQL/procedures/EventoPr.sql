USE `ticket_flow`;

-- =====================================================
-- EVENTO
-- =====================================================

-- CREATE
DROP PROCEDURE IF EXISTS SP_INSERTAR_EVENTO;
DELIMITER //
CREATE PROCEDURE SP_INSERTAR_EVENTO(
    IN p_id_Evento INT,
    IN p_titulo VARCHAR(100),
    IN p_descripcion VARCHAR(250),
    IN p_capacidad_entradas INT,
    IN p_fecha DATE,
    IN p_hora_inicio TIME,
    IN p_hora_fin TIME,
    IN p_ubicacion VARCHAR(100),
    IN p_nombre_establecimiento VARCHAR(45),
    IN p_img VARCHAR(450),
    IN p_precio DOUBLE,
    IN p_idDistrito INT,
    IN p_idAnfitrion INT,
    IN p_idCategoria_evento INT,
    IN p_idEstado_publicacion INT,
    IN p_idEstado_evento INT
)
BEGIN
    INSERT INTO evento (
        idEvento,
        titulo,
        descripcion,
        capacidad_entradas,
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
    VALUES (
        p_id_Evento,
        p_titulo,
        p_descripcion,
        p_capacidad_entradas,
        p_fecha,
        p_hora_inicio,
        p_hora_fin,
        p_ubicacion,
        p_nombre_establecimiento,
        p_img,
        p_precio,
        p_idDistrito,
        p_idAnfitrion,
        p_idCategoria_evento,
        p_idEstado_publicacion,
        p_idEstado_evento
    );
END //
DELIMITER ;


-- READ
DROP PROCEDURE IF EXISTS SP_LEER_EVENTO;
DELIMITER //

CREATE PROCEDURE SP_LEER_EVENTO(
    IN p_id_Evento INT
)
BEGIN
    SELECT 
        e.idEvento,
        e.titulo,
        e.descripcion,
        e.capacidad_entradas,
        e.fecha,
        e.hora_inicio,
        e.hora_fin,
        e.ubicacion,
        e.nombre_establecimiento,
        e.img,
        e.precio,
        e.idDistrito,
        e.idAnfitrion,
        e.idCategoria_evento,
        -- Llaves foráneas solicitadas
        r.idRegion,
        d.nombre AS nombre_distrito,
        r.nombre AS nombre_region,
        c.nombre AS nombre_categoria,
        u.nombre AS nombre_anfitrion, -- Nombre del usuario que actúa como anfitrión
        a.razon_social, -- Por si también necesitas su razón social
        -- Mantener los IDs originales de los estados (ya que especificaste que sus nombres no son necesarios)
        e.idEstado_publicacion,
        e.idEstado_evento
    FROM evento e
    INNER JOIN distrito d ON e.idDistrito = d.idDistrito
    INNER JOIN region r ON d.idRegion = r.idRegion
    INNER JOIN categoria_evento c ON e.idCategoria_evento = c.idCategoria_evento
    INNER JOIN anfitrion a ON e.idAnfitrion = a.idAnfitrion
    INNER JOIN usuario u ON a.idAnfitrion = u.idUsuario -- El ID de anfitrión hereda del ID de usuario
    WHERE e.idEvento = p_id_Evento;
    
END //
DELIMITER ;

select * from evento;
UPDATE evento
SET img = 'https://lh3.googleusercontent.com/d/1tf5zvdOHO1mmnKpJVx4OaaOMVz1P4V8v'
WHERE idEvento = 1;

-- UPDATE
DROP PROCEDURE IF EXISTS SP_ACTUALIZAR_EVENTO;
DELIMITER //
CREATE PROCEDURE SP_ACTUALIZAR_EVENTO(
    IN p_idEvento INT,
    IN p_titulo VARCHAR(100),
    IN p_descripcion VARCHAR(250),
    IN p_capacidad_entradas INT,
    IN p_fecha DATE,
    IN p_hora_inicio TIME,
    IN p_hora_fin TIME,
    IN p_ubicacion VARCHAR(100),
    IN p_nombre_establecimiento VARCHAR(45),
    IN p_img VARCHAR(450),
    IN p_precio DOUBLE,
    IN p_idDistrito INT,
    IN p_idAnfitrion INT,
    IN p_idCategoria_evento INT,
    IN p_idEstado_publicacion INT,
    IN p_idEstado_evento INT
)
BEGIN
    UPDATE evento
    SET
        titulo = p_titulo,
        descripcion = p_descripcion,
        capacidad_entradas = p_capacidad_entradas,
        fecha = p_fecha,
        hora_inicio = p_hora_inicio,
        hora_fin = p_hora_fin,
        ubicacion = p_ubicacion,
        nombre_establecimiento = p_nombre_establecimiento,
        img = p_img,
        precio = p_precio,
        idDistrito = p_idDistrito,
        idAnfitrion = p_idAnfitrion,
        idCategoria_evento = p_idCategoria_evento,
        idEstado_publicacion = p_idEstado_publicacion,
        idEstado_evento = p_idEstado_evento
    WHERE idEvento = p_idEvento;
END //
DELIMITER ;


-- DELETE FÍSICO/LÓGICO SEGÚN ESTADO
DROP PROCEDURE IF EXISTS SP_ELIMINAR_EVENTO;
DELIMITER //
CREATE PROCEDURE SP_ELIMINAR_EVENTO(
    IN p_idEvento INT
)
BEGIN
    UPDATE evento
    SET idEstado_evento = (
        SELECT idEstado_evento
        FROM estado_evento
        WHERE LOWER(estado) = 'eliminado'
        LIMIT 1
    )
    WHERE idEvento = p_idEvento;
END //
DELIMITER ;


-- LIST ALL
DROP PROCEDURE IF EXISTS SP_LISTAR_EVENTOS;
DELIMITER //

CREATE PROCEDURE SP_LISTAR_EVENTOS()
BEGIN
    SELECT 
        e.idEvento,
        e.titulo,
        e.descripcion,
        e.capacidad_entradas,
        e.fecha,
        e.hora_inicio,
        e.hora_fin,
        e.ubicacion,
        e.nombre_establecimiento,
        e.img,
        e.precio,
        e.idDistrito,
        e.idAnfitrion,
        ce.nombre AS categoria,
        e.idEstado_publicacion,
        e.idEstado_evento
    FROM evento e
    INNER JOIN categoria_evento ce 
        ON e.idCategoria_evento = ce.idCategoria_evento
    ORDER BY e.idEvento;
END //

DELIMITER ;


-- BUSCAR POR TÍTULO
DROP PROCEDURE IF EXISTS SP_BUSCAR_EVENTO_TITULO;
DELIMITER //
CREATE PROCEDURE SP_BUSCAR_EVENTO_TITULO(
    IN p_titulo VARCHAR(100)
)
BEGIN
    SELECT e.*
    FROM evento e
    WHERE e.titulo LIKE CONCAT('%', p_titulo, '%');
END //
DELIMITER ;


-- FILTRAR POR ESTADO DE EVENTO
DROP PROCEDURE IF EXISTS SP_FILTRAR_EVENTO_ESTADO;
DELIMITER //
CREATE PROCEDURE SP_FILTRAR_EVENTO_ESTADO(
    IN p_idEstadoEvento INT
)
BEGIN
    SELECT *
    FROM evento
    WHERE idEstado_evento = p_idEstadoEvento;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS SP_FILTRAR_EVENTO_CATEGORIA;
DELIMITER //
CREATE PROCEDURE SP_FILTRAR_EVENTO_CATEGORIA(
    IN p_categoria VARCHAR(45)
)
BEGIN
    SELECT 
        e.idEvento,
        e.titulo,
        e.descripcion,
        e.capacidad_entradas,
        e.fecha,
        e.hora_inicio,
        e.hora_fin,
        e.ubicacion,
        e.nombre_establecimiento,
        e.img,
        e.precio,
        e.idDistrito,
        e.idAnfitrion,
        e.idCategoria_evento,
       -- ce.nombre AS categoria,
        e.idEstado_publicacion,
        e.idEstado_evento
    FROM evento e, categoria_evento ce
	WHERE ce.nombre = p_categoria and ce.idCategoria_evento = e.idCategoria_evento
    ORDER BY e.idEvento;
END //
DELIMITER ;

-- APROBAR EVENTO
DROP PROCEDURE IF EXISTS SP_APROBAR_EVENTO;
DELIMITER //
CREATE PROCEDURE SP_APROBAR_EVENTO(
    IN p_idEvento INT
)
BEGIN
    UPDATE evento
    SET idEstado_publicacion = 2
    WHERE idEvento = p_idEvento;
END //
DELIMITER ;


-- RECHAZAR EVENTO
DROP PROCEDURE IF EXISTS SP_RECHAZAR_EVENTO;
DELIMITER //
CREATE PROCEDURE SP_RECHAZAR_EVENTO(
    IN p_idEvento INT
)
BEGIN
    UPDATE evento
    SET idEstado_publicacion = 3
    WHERE idEvento = p_idEvento;
END //
DELIMITER ;


-- ELIMINAR EVENTO COMO ESTADO
DROP PROCEDURE IF EXISTS SP_ELIMINAR_EVENTO_ESTADO;
DELIMITER //
CREATE PROCEDURE SP_ELIMINAR_EVENTO_ESTADO(
    IN p_idEvento INT
)
BEGIN
    UPDATE evento
    SET idEstado_evento = 3
    WHERE idEvento = p_idEvento;
END //
DELIMITER ;


-- =====================================================
-- ESTADO EVENTO
-- =====================================================

-- CREATE
DROP PROCEDURE IF EXISTS SP_INSERTAR_ESTADO_EVENTO;
DELIMITER //
CREATE PROCEDURE SP_INSERTAR_ESTADO_EVENTO(
    IN p_idEstado_evento INT,
    IN p_estado VARCHAR(45)
)
BEGIN
    INSERT INTO estado_evento (idEstado_evento, estado)
    VALUES (p_idEstado_evento, p_estado);
END //
DELIMITER ;


-- READ
DROP PROCEDURE IF EXISTS SP_LEER_ESTADO_EVENTO;
DELIMITER //
CREATE PROCEDURE SP_LEER_ESTADO_EVENTO(
    IN p_idEstado_evento INT
)
BEGIN
    SELECT *
    FROM estado_evento
    WHERE idEstado_evento = p_idEstado_evento;
END //
DELIMITER ;


-- UPDATE
DROP PROCEDURE IF EXISTS SP_ACTUALIZAR_ESTADO_EVENTO;
DELIMITER //
CREATE PROCEDURE SP_ACTUALIZAR_ESTADO_EVENTO(
    IN p_idEstado_evento INT,
    IN p_estado VARCHAR(45)
)
BEGIN
    UPDATE estado_evento
    SET estado = p_estado
    WHERE idEstado_evento = p_idEstado_evento;
END //
DELIMITER ;


-- DELETE
DROP PROCEDURE IF EXISTS SP_ELIMINAR_ESTADO_EVENTO;
DELIMITER //
CREATE PROCEDURE SP_ELIMINAR_ESTADO_EVENTO(
    IN p_idEstado_evento INT
)
BEGIN
    DELETE FROM estado_evento
    WHERE idEstado_evento = p_idEstado_evento;
END //
DELIMITER ;


-- LIST ALL
DROP PROCEDURE IF EXISTS SP_LISTAR_ESTADO_EVENTOS;
DELIMITER //
CREATE PROCEDURE SP_LISTAR_ESTADO_EVENTOS()
BEGIN
    SELECT *
    FROM estado_evento
    ORDER BY idEstado_evento;
END //
DELIMITER ;


-- =====================================================
-- CATEGORIA EVENTO
-- =====================================================

-- CREATE
DROP PROCEDURE IF EXISTS SP_INSERTAR_CATEGORIA_EVENTO;
DELIMITER //
CREATE PROCEDURE SP_INSERTAR_CATEGORIA_EVENTO(
    IN p_idCategoria_evento INT,
    IN p_nombre VARCHAR(45),
    IN p_dias_para_publicacion INT
)
BEGIN
    INSERT INTO categoria_evento (
        idCategoria_evento,
        nombre,
        dias_para_publicacion
    )
    VALUES (
        p_idCategoria_evento,
        p_nombre,
        p_dias_para_publicacion
    );
END //
DELIMITER ;


-- READ
DROP PROCEDURE IF EXISTS SP_LEER_CATEGORIA_EVENTO;
DELIMITER //
CREATE PROCEDURE SP_LEER_CATEGORIA_EVENTO(
    IN p_idCategoria_evento INT
)
BEGIN
    SELECT *
    FROM categoria_evento
    WHERE idCategoria_evento = p_idCategoria_evento;
END //
DELIMITER ;


-- UPDATE
DROP PROCEDURE IF EXISTS SP_ACTUALIZAR_CATEGORIA_EVENTO;
DELIMITER //
CREATE PROCEDURE SP_ACTUALIZAR_CATEGORIA_EVENTO(
    IN p_idCategoria_evento INT,
    IN p_nombre VARCHAR(45),
    IN p_dias_para_publicacion INT
)
BEGIN
    UPDATE categoria_evento
    SET
        nombre = p_nombre,
        dias_para_publicacion = p_dias_para_publicacion
    WHERE idCategoria_evento = p_idCategoria_evento;
END //
DELIMITER ;


-- DELETE
DROP PROCEDURE IF EXISTS SP_ELIMINAR_CATEGORIA_EVENTO;
DELIMITER //
CREATE PROCEDURE SP_ELIMINAR_CATEGORIA_EVENTO(
    IN p_idCategoria_evento INT
)
BEGIN
    DELETE FROM categoria_evento
    WHERE idCategoria_evento = p_idCategoria_evento;
END //
DELIMITER ;


-- LIST ALL
DROP PROCEDURE IF EXISTS SP_LISTAR_CATEGORIA_EVENTOS;
DELIMITER //
CREATE PROCEDURE SP_LISTAR_CATEGORIA_EVENTOS()
BEGIN
    SELECT *
    FROM categoria_evento
    ORDER BY idCategoria_evento;
END //
DELIMITER ;


-- =====================================================
-- ESTADO PUBLICACION
-- =====================================================

-- CREATE
DROP PROCEDURE IF EXISTS SP_INSERTAR_ESTADO_PUBLICACION;
DELIMITER //
CREATE PROCEDURE SP_INSERTAR_ESTADO_PUBLICACION(
    IN p_idEstado_publicacion INT,
    IN p_estado VARCHAR(45)
)
BEGIN
    INSERT INTO estado_publicacion (idEstado_publicacion, estado)
    VALUES (p_idEstado_publicacion, p_estado);
END //
DELIMITER ;


-- READ
DROP PROCEDURE IF EXISTS SP_LEER_ESTADO_PUBLICACION;
DELIMITER //
CREATE PROCEDURE SP_LEER_ESTADO_PUBLICACION(
    IN p_idEstado_publicacion INT
)
BEGIN
    SELECT *
    FROM estado_publicacion
    WHERE idEstado_publicacion = p_idEstado_publicacion;
END //
DELIMITER ;


-- UPDATE
DROP PROCEDURE IF EXISTS SP_ACTUALIZAR_ESTADO_PUBLICACION;
DELIMITER //
CREATE PROCEDURE SP_ACTUALIZAR_ESTADO_PUBLICACION(
    IN p_idEstado_publicacion INT,
    IN p_estado VARCHAR(45)
)
BEGIN
    UPDATE estado_publicacion
    SET estado = p_estado
    WHERE idEstado_publicacion = p_idEstado_publicacion;
END //
DELIMITER ;


-- DELETE
DROP PROCEDURE IF EXISTS SP_ELIMINAR_ESTADO_PUBLICACION;
DELIMITER //
CREATE PROCEDURE SP_ELIMINAR_ESTADO_PUBLICACION(
    IN p_idEstado_publicacion INT
)
BEGIN
    DELETE FROM estado_publicacion
    WHERE idEstado_publicacion = p_idEstado_publicacion;
END //
DELIMITER ;


-- LIST ALL
DROP PROCEDURE IF EXISTS SP_LISTAR_ESTADO_PUBLICACIONES;
DELIMITER //
CREATE PROCEDURE SP_LISTAR_ESTADO_PUBLICACIONES()
BEGIN
    SELECT *
    FROM estado_publicacion
    ORDER BY idEstado_publicacion;
END //
DELIMITER ;
