package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IEstadoSolicitudesDAO;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.solicitud.model.EstadoSolicitud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoSolicitudesDAOImpl implements IEstadoSolicitudesDAO {
    @Override
    public EstadoSolicitud create(EstadoSolicitud t) {
        String sql = "{CALL USP_INSERTAR_ESTADO_SOLICITUD(?, ?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, t.getEstado());
            cs.execute();
            t.setIdEstadoSolicitud(cs.getInt(1));

            return t;

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear estado de solicitud", e);
        }
    }

    @Override
    public EstadoSolicitud read(Integer id) {
        String sql = "{CALL USP_LEER_ESTADO_SOLICITUD(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);

            try (ResultSet rs = cs.executeQuery()) {

                if (rs.next()) {
                    EstadoSolicitud t = new EstadoSolicitud();
                    mapear(rs, t);
                    return t;
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al leer estado de solicitud", e);
        }
    }

    @Override
    public EstadoSolicitud update(EstadoSolicitud t, Integer id) {
        String sql = "{CALL USP_ACTUALIZAR_ESTADO_SOLICITUD(?, ?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.setString(2, t.getEstado());

            cs.execute();

            return t;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar estado de solicitud", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "{CALL USP_ELIMINAR_ESTADO_SOLICITUD(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);

            cs.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar estado de solicitud", e);
        }
    }

    @Override
    public List<EstadoSolicitud> listAll() {
        List<EstadoSolicitud> lista = new ArrayList<>();

        String sql = "{CALL USP_LISTAR_ESTADOS_SOLICITUD()}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                EstadoSolicitud t = new EstadoSolicitud();
                mapear(rs, t);
                lista.add(t);
            }

            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar estados de solicitud", e);
        }
    }

    private void mapear(ResultSet rs, EstadoSolicitud t) throws SQLException {

        t.setIdEstadoSolicitud(rs.getInt("idEstado_solicitudes"));
        t.setEstado(rs.getString("estado"));
    }
}
