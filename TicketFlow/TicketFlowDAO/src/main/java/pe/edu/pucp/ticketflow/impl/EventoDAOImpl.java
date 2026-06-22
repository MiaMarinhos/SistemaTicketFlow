package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IEventoDAO;
import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.evento.model.categoria_evento;
import pe.edu.pucp.ticketflow.ubicacion.model.Distrito;
import pe.edu.pucp.ticketflow.ubicacion.model.Region;
import pe.edu.pucp.ticketflow.usuario.model.Anfitrion;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class EventoDAOImpl implements IEventoDAO {
    @Override
    public Evento create(Evento eve){
        // INSERT
        String sql = "{CALL SP_INSERTAR_EVENTO (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try(Connection connection = DBManager.getInstance().getConnection();

            CallableStatement cs = connection.prepareCall(sql)) {

            cs.setInt(1,eve.getIdEvento());

            cs.setString(2, eve.getTitulo());
            cs.setString(3, eve.getDescripcion());
            cs.setInt(4, eve.getCapacidad_entradas());
            cs.setDate(5, new java.sql.Date(eve.getFecha().getTime()));
            cs.setTime(6, eve.getHora_inicio());
            cs.setTime(7, eve.getHora_fin());
            cs.setString(8, eve.getUbicacion());
            cs.setString(9, eve.getNombre_establecimiento());
            cs.setString(10, eve.getImg());
            cs.setDouble(11, eve.getPrecio());
            cs.setInt(12, eve.getFK_idDistrito());
            cs.setInt(13, eve.getIdAnfitrion());
            cs.setInt(14, eve.getFK_idCategoria_evento());
            cs.setInt(15, eve.getFK_idEstadoPublicacion());
            cs.setInt(16, eve.getFK_idEstadoEvento());

            cs.execute();

            return eve;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Evento read(Integer id) {

        String sql = "{CALL SP_LEER_EVENTO(?)}";

        try (Connection connection = DBManager.getInstance().getConnection();
             CallableStatement cs = connection.prepareCall(sql)) {

            cs.setInt(1, id);

            try (ResultSet rs = cs.executeQuery()) {

                if (rs.next()) {

                    Evento evento = new Evento();
                    mapearEvento(rs, evento);
                    return evento;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Evento update(Evento evento, Integer id){
        // UPDATE
        String sql =
                "{CALL SP_ACTUALIZAR_EVENTO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try(Connection connection = DBManager.getInstance().getConnection();
            CallableStatement cs = connection.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.setString(2, evento.getTitulo());
            cs.setString(3, evento.getDescripcion());
            cs.setInt(4, evento.getCapacidad_entradas());
            cs.setDate(5, new java.sql.Date(evento.getFecha().getTime()));
            cs.setTime(6, evento.getHora_inicio());
            cs.setTime(7, evento.getHora_fin());
            cs.setString(8, evento.getUbicacion());
            cs.setString(9, evento.getNombre_establecimiento());
            cs.setString(10, evento.getImg());
            cs.setDouble(11, evento.getPrecio());

            cs.setInt(12, evento.getFK_idDistrito());
            cs.setInt(13, evento.getIdAnfitrion());
            cs.setInt(14, evento.getFK_idCategoria_evento());
            cs.setInt(15, evento.getFK_idEstadoPublicacion());
            cs.setInt(16, evento.getFK_idEstadoEvento());

            cs.executeUpdate();

            return evento;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id){
        String sql =
                "{CALL SP_ELIMINAR_EVENTO(?)}";
        try(Connection connection = DBManager.getInstance().getConnection();
            CallableStatement cs = connection.prepareCall(sql)) {
            cs.setInt(1,id);

            cs.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Evento> listAll() {

        String sql = "{CALL SP_LISTAR_EVENTOS()}";

        try (Connection connection = DBManager.getInstance().getConnection();
             CallableStatement cs = connection.prepareCall(sql)) {

            try (ResultSet rs = cs.executeQuery()) {

                List<Evento> eventos = new ArrayList<>();

                while (rs.next()) {

                    Evento evento = new Evento();
                    mapearEventoListar(rs, evento);
                    eventos.add(evento);
                }

                return eventos;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Evento> buscarPorTitulo(String titulo) {

        List<Evento> eventos = new ArrayList<>();

        String sql = "{CALL SP_BUSCAR_EVENTO_TITULO(?)}";

        try (Connection connection = DBManager.getInstance().getConnection();
             CallableStatement cs = connection.prepareCall(sql)) {

            cs.setString(1, titulo);

            try (ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {

                    Evento evento = new Evento();
                    mapearEvento(rs, evento);
                    eventos.add(evento);
                }

                return eventos;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar eventos por título", e);
        }
    }

    @Override
    public List<Evento> filtrarPorEstado(Integer idEstadoEvento) {

        List<Evento> eventos = new ArrayList<>();

        String sql = "{CALL SP_FILTRAR_EVENTO_ESTADO(?)}";

        try (Connection connection = DBManager.getInstance().getConnection();
             CallableStatement cs = connection.prepareCall(sql)) {

            cs.setInt(1, idEstadoEvento);

            try (ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {

                    Evento evento = new Evento();
                    mapearEvento(rs, evento);
                    eventos.add(evento);
                }

                return eventos;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al filtrar eventos por estado", e);
        }
    }
    @Override
    public List<Evento> filtrarPorTipo(String Tipo){
        List<Evento> eventos = new ArrayList<>();

        String sql = "{CALL SP_FILTRAR_EVENTO_CATEGORIA(?)}";

        try (Connection connection = DBManager.getInstance().getConnection();
             CallableStatement cs = connection.prepareCall(sql)) {

            cs.setString(1, Tipo);

            try (ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {

                    Evento evento = new Evento();
                    evento.setIdEvento(rs.getInt("idEvento"));
                    evento.setTitulo(rs.getString("titulo"));
                    evento.setDescripcion(rs.getString("descripcion"));
                    evento.setCapacidad_entradas(rs.getInt("capacidad_entradas"));
                    evento.setFecha(rs.getDate("fecha"));
                    evento.setHora_inicio(rs.getTime("hora_inicio"));
                    evento.setHora_fin(rs.getTime("hora_fin"));
                    evento.setUbicacion(rs.getString("ubicacion"));
                    evento.setNombre_establecimiento(
                            rs.getString("nombre_establecimiento")
                    );
                    evento.setImg(rs.getString("img"));
                    evento.setPrecio(rs.getDouble("precio"));

                    categoria_evento cat = new categoria_evento();
                    cat.setIdCategoria_evento(rs.getInt("idCategoria_evento"));
                    evento.setCategoria(cat);

                    eventos.add(evento);
                }

                return eventos;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al filtrar eventos por categoria", e);
        }
    }

    @Override
    public Evento aprobarEvento(Integer idEvento) {

        String sql = "{CALL SP_APROBAR_EVENTO(?)}";

        try (Connection connection = DBManager.getInstance().getConnection();
             CallableStatement cs = connection.prepareCall(sql)) {

            cs.setInt(1, idEvento);

            cs.execute();

            return read(idEvento);

        } catch (SQLException e) {
            throw new RuntimeException("Error al aprobar evento", e);
        }
    }

    @Override
    public Evento rechazarEvento(Integer idEvento) {

        String sql = "{CALL SP_RECHAZAR_EVENTO(?)}";

        try (Connection connection = DBManager.getInstance().getConnection();
             CallableStatement cs = connection.prepareCall(sql)) {

            cs.setInt(1, idEvento);

            cs.execute();

            return read(idEvento);

        } catch (SQLException e) {
            throw new RuntimeException("Error al rechazar evento", e);
        }
    }



    @Override
    public Evento eliminarEvento(Integer idEvento) {

        String sql = "{CALL SP_ELIMINAR_EVENTO_ESTADO(?)}";

        try (Connection connection = DBManager.getInstance().getConnection();
             CallableStatement cs = connection.prepareCall(sql)) {

            cs.setInt(1, idEvento);

            cs.execute();

            return read(idEvento);

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar evento", e);
        }
    }

    private void mapearEventoListar(ResultSet rs, Evento evento) throws SQLException {

        evento.setIdEvento(rs.getInt("idEvento"));
        evento.setTitulo(rs.getString("titulo"));
        evento.setDescripcion(rs.getString("descripcion"));
        evento.setCapacidad_entradas(rs.getInt("capacidad_entradas"));
        evento.setFecha(rs.getDate("fecha"));
        evento.setHora_inicio(rs.getTime("hora_inicio"));
        evento.setHora_fin(rs.getTime("hora_fin"));
        evento.setUbicacion(rs.getString("ubicacion"));
        evento.setNombre_establecimiento(
                rs.getString("nombre_establecimiento")
        );
        evento.setImg(rs.getString("img"));
        evento.setPrecio(rs.getDouble("precio"));
        evento.setIdAnfitrion(rs.getInt("idAnfitrion"));

        categoria_evento cat = new categoria_evento();
        cat.setNombre(rs.getString("categoria"));
        evento.setCategoria(cat);

        evento.setFK_idEstadoPublicacion(
                rs.getInt("idEstado_publicacion")
        );

        evento.setFK_idEstadoEvento(
                rs.getInt("idEstado_evento")
        );

        evento.setFK_idDistrito(
                rs.getInt("idDistrito")
        );
    }
    private void mapearEvento(ResultSet rs, Evento evento) throws SQLException {

        evento.setIdEvento(rs.getInt("idEvento"));
        evento.setTitulo(rs.getString("titulo"));
        evento.setDescripcion(rs.getString("descripcion"));
        evento.setCapacidad_entradas(rs.getInt("capacidad_entradas"));
        evento.setFecha(rs.getDate("fecha"));
        evento.setHora_inicio(rs.getTime("hora_inicio"));
        evento.setHora_fin(rs.getTime("hora_fin"));
        evento.setUbicacion(rs.getString("ubicacion"));
        evento.setNombre_establecimiento(
                rs.getString("nombre_establecimiento")
        );
        evento.setImg(rs.getString("img"));
        evento.setPrecio(rs.getDouble("precio"));

        Region region = new Region();
        region.setIdRegion(rs.getInt("idRegion"));
        region.setNombre(rs.getString("nombre_region"));

        Distrito distrito = new Distrito();
        distrito.setIdDistrito(rs.getInt("idDistrito"));
        distrito.setNombre(rs.getString("nombre_distrito"));
        distrito.setRegion(region);
        evento.setDistrito(distrito);

        evento.setIdAnfitrion(rs.getInt("idAnfitrion"));
        Anfitrion anfitrion = new Anfitrion();
        anfitrion.setIdUsuario(rs.getInt("idAnfitrion"));
        anfitrion.setRazonSocial(rs.getString("razon_social"));
        anfitrion.setNombre(rs.getString("nombre_anfitrion"));
        evento.setAnfitrion(anfitrion);

        categoria_evento cat = new categoria_evento();
        cat.setIdCategoria_evento(rs.getInt("idCategoria_evento"));
        cat.setNombre(rs.getString("nombre_categoria"));
        evento.setCategoria(cat);

    }
}
