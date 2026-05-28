package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.ITipoUsuarioDAO;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.usuario.model.Anfitrion;
import pe.edu.pucp.ticketflow.usuario.model.Cliente;
import pe.edu.pucp.ticketflow.usuario.model.TipoUsuario;
import pe.edu.pucp.ticketflow.usuario.model.Usuario;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TipoUsuarioDAOImpl implements ITipoUsuarioDAO {
    @Override
    public TipoUsuario create(TipoUsuario t) {
        return null;
    }

    @Override
    public TipoUsuario read(Integer id) {
        String sql = "{CALL SP_LEER_TIPO_USUARIO(?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    TipoUsuario tipoUsuario = new TipoUsuario();
                    tipoUsuario.setIdTipoUsuario(rs.getInt("idTipo_usuario"));
                    tipoUsuario.setTipoUsuario(rs.getString("nombre"));
                    return tipoUsuario;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer tipo de usuario", e);
        }
        return null;
    }

    @Override
    public TipoUsuario update(TipoUsuario t, Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<TipoUsuario> listAll() {
        return List.of();
    }


    @Override
    public TipoUsuario buscarTipoUsuarioPorTipo(String tipo) {
        String sql = "{CALL SP_LEER_TIPO_USUARIO_X_TIPO(?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, tipo);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    TipoUsuario tipoUsuario = new TipoUsuario();
                    tipoUsuario.setIdTipoUsuario(rs.getInt("idTipo_usuario"));
                    tipoUsuario.setTipoUsuario(rs.getString("nombre"));
                    return tipoUsuario;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer tipo de usuario", e);
        }
        return null;
    }
}
