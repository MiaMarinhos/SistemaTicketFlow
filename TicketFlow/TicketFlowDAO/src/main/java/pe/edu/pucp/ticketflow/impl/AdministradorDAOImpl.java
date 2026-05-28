package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IAdministradorDAO;
import pe.edu.pucp.ticketflow.administrador.model.Administrador;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAOImpl implements IAdministradorDAO {
    @Override
    public Administrador create(Administrador administrador) {

        String sql = "{CALL SP_INSERTAR_ADMINISTRADOR(?, ?, ?, ?, ?, ?, ?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, administrador.getIdAdministrador());
            cs.setString(2, administrador.getCodigo());
            cs.setString(3, administrador.getNombre());
            cs.setString(4, administrador.getApellidoPaterno());
            cs.setString(5, administrador.getApellidoMaterno());
            cs.setString(6, administrador.getDni());
            cs.setString(7, administrador.getContrasena());

            cs.execute();

            return administrador;

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear administrador", e);
        }
    }

    @Override
    public Administrador read(Integer id) {
        String sql = "{CALL SP_LEER_ADMINISTRADOR(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearAdministrador(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al leer administrador", e);
        }
    }

    @Override
    public Administrador update(Administrador administrador, Integer id) {
        String sql = "{CALL SP_ACTUALIZAR_ADMINISTRADOR(?, ?, ?, ?, ?, ?, ?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.setString(2, administrador.getCodigo());
            cs.setString(3, administrador.getNombre());
            cs.setString(4, administrador.getApellidoPaterno());
            cs.setString(5, administrador.getApellidoMaterno());
            cs.setString(6, administrador.getDni());
            cs.setString(7, administrador.getContrasena());

            cs.execute();

            administrador.setIdAdministrador(id);
            return administrador;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar administrador", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "{CALL SP_ELIMINAR_ADMINISTRADOR(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar administrador", e);
        }
    }

    @Override
    public List<Administrador> listAll() {
        List<Administrador> administradores = new ArrayList<>();
        String sql = "{CALL SP_LISTAR_ADMINISTRADORES()}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                administradores.add(mapearAdministrador(rs));
            }

            return administradores;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar administradores", e);
        }
    }

    private Administrador mapearAdministrador(ResultSet rs) throws SQLException {
        Administrador administrador = new Administrador();
        administrador.setIdAdministrador(rs.getInt("idAdministrador"));
        administrador.setCodigo(rs.getString("codigo"));
        administrador.setNombre(rs.getString("nombre"));
        administrador.setApellidoPaterno(rs.getString("apellido_paterno"));
        administrador.setApellidoMaterno(rs.getString("apellido_materno"));
        administrador.setDni(rs.getString("dni"));
        administrador.setContrasena(rs.getString("contrasena"));
        return administrador;
    }
}


