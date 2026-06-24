package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.ISolicitudesDAO;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.solicitud.model.EstadoSolicitud;
import pe.edu.pucp.ticketflow.solicitud.model.Solicitud;
import pe.edu.pucp.ticketflow.usuario.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitudesDAOImpl implements ISolicitudesDAO {
    @Override
    public Solicitud create(Solicitud t) {

        String sql = "{CALL USP_INSERTAR_SOLICITUD(?, ?, ?, ?, ?, ?, ?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, Types.INTEGER);

            cs.setString(2, t.getTelefonoContacto());
            cs.setString(3, t.getCorreoContacto());
            cs.setString(4, t.getMotivo());
            cs.setInt(5, t.getIdAdministrador());
            cs.setInt(6, t.getIdCliente());
            cs.setInt(7, t.getIdEstadoSolicitud());

            cs.execute();

            t.setIdSolicitudes(cs.getInt(1));

            return t;

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear solicitud", e);
        }
    }

    @Override
    public Solicitud read(Integer id) {
        String sql = "{CALL USP_LEER_SOLICITUD(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);

            try (ResultSet rs = cs.executeQuery()) {

                if (rs.next()) {
                    Solicitud t = new Solicitud();
                    mapear(rs, t);
                    return t;
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al leer solicitud", e);
        }
    }

    @Override
    public Solicitud update(Solicitud t, Integer id) {
        String sql = "{CALL USP_ACTUALIZAR_SOLICITUD(?, ?, ?, ?, ?, ?, ?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.setString(2, t.getTelefonoContacto());
            cs.setString(3, t.getCorreoContacto());
            cs.setString(4, t.getMotivo());
            cs.setInt(5, t.getIdAdministrador());
            cs.setInt(6, t.getIdCliente());
            cs.setInt(7, t.getIdEstadoSolicitud());

            cs.execute();

            return t;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar solicitud", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "{CALL USP_ELIMINAR_SOLICITUD(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);

            cs.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar solicitud", e);
        }
    }

    @Override
    public List<Solicitud> listAll() {
        List<Solicitud> lista = new ArrayList<>();

        String sql = "{CALL USP_LISTAR_SOLICITUDES()}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {

                Solicitud t = new Solicitud();

                mapear(rs, t);

                lista.add(t);
            }

            return lista;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Error al listar solicitudes: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public List<Solicitud> buscarPorNombre(String nombre) {

        List<Solicitud> lista = new ArrayList<>();

        String sql = "{CALL USP_BUSCAR_SOLICITUD_NOMBRE(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, nombre);

            try (ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {

                    Solicitud t = new Solicitud();
                    mapear(rs, t);
                    lista.add(t);
                }

                return lista;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error al buscar solicitudes",
                    e
            );
        }
    }

    @Override
    public List<Solicitud> filtrarPorEstado(Integer idEstado) {

        List<Solicitud> lista = new ArrayList<>();

        String sql = "{CALL USP_FILTRAR_SOLICITUD_ESTADO(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idEstado);

            try (ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {

                    Solicitud t = new Solicitud();
                    mapear(rs, t);
                    lista.add(t);
                }

                return lista;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al filtrar solicitudes por estado", e);
        }
    }

    @Override
    public Solicitud aprobarSolicitud(Integer idSolicitud) {

        String sql = "{CALL USP_APROBAR_SOLICITUD(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idSolicitud);
            cs.execute();

            return read(idSolicitud);

        } catch (SQLException e) {
            throw new RuntimeException("Error al aprobar solicitud", e);
        }
    }

    @Override
    public Solicitud rechazarSolicitud(Integer idSolicitud) {

        String sql = "{CALL USP_RECHAZAR_SOLICITUD(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idSolicitud);

            cs.execute();

            return read(idSolicitud);

        } catch (SQLException e) {
            throw new RuntimeException("Error al rechazar solicitud", e);
        }
    }

    private void mapear(ResultSet rs, Solicitud t) throws SQLException {

        t.setIdSolicitudes(rs.getInt("idSolicitudes"));
        t.setTelefonoContacto(rs.getString("telefono_contacto"));
        t.setCorreoContacto(rs.getString("correo_contacto"));
        t.setMotivo(rs.getString("motivo"));

        t.setIdAdministrador(rs.getInt("idAdministrador"));
        t.setIdCliente(rs.getInt("idUsuario"));
        t.setIdEstadoSolicitud(rs.getInt("idEstado"));

        Cliente cliente = new Cliente();
        cliente.setIdUsuario(rs.getInt("idUsuario"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellidoPaterno(rs.getString("apellido_paterno"));
        cliente.setApellidoMaterno(rs.getString("apellido_materno"));

        t.setCliente(cliente);

        EstadoSolicitud estado = new EstadoSolicitud();
        estado.setIdEstadoSolicitud(rs.getInt("idEstado"));
        estado.setEstado(rs.getString("estado_solicitud"));

        t.setEstadoSolicitud(estado);
    }
}
