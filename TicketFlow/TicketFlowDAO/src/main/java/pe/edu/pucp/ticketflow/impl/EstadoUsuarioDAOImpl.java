package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IEstadoUsuarioDAO;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.usuario.model.EstadoUsuario;
import pe.edu.pucp.ticketflow.usuario.model.TipoUsuario;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EstadoUsuarioDAOImpl implements IEstadoUsuarioDAO {
    @Override
    public EstadoUsuario create(EstadoUsuario t) {
        return null;
    }

    @Override
    public EstadoUsuario read(Integer id) {
        String sql = "{CALL SP_LEER_ESTADO_USUARIO(?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    EstadoUsuario estadoUsuario = new EstadoUsuario();
                    estadoUsuario.setIdEstadoUsuario(rs.getInt("idEstado_usuario"));
                    estadoUsuario.setNombre(rs.getString("estado"));
                    return estadoUsuario;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer estado usuario", e);
        }
        return null;
    }

    @Override
    public EstadoUsuario update(EstadoUsuario t, Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<EstadoUsuario> listAll() {
        return List.of();
    }
}
