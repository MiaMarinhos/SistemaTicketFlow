package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IAdministradorDAO;
import pe.edu.pucp.ticketflow.administrador.model.Administrador;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.usuario.model.Genero;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAOImpl implements IAdministradorDAO {
    @Override
    public Administrador create(Administrador administrador) {

        String sql = "{CALL SP_INSERTAR_ADMINISTRADOR(?, ?, ?, ?, ?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, administrador.getIdUsuario());
            cs.setString(2, administrador.getImg());
            cs.setDouble(3, administrador.getMonto_total());
            cs.setDouble(4, administrador.getMonto_neto());
            cs.setDouble(5, administrador.getMonto_disponible());

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
    public Administrador update(Administrador t, Integer id) {
        String sqlUsuario = "{CALL SP_ACTUALIZAR_USUARIO(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        String sqlAdmin = "{CALL SP_ACTUALIZAR_ADMINISTRADOR(?, ?, ?, ?, ?)}";
        try (Connection con = DBManager.getInstance().getConnection()) {
            con.setAutoCommit(false);
            try (CallableStatement csUsuario = con.prepareCall(sqlUsuario);
                 CallableStatement csAdmin = con.prepareCall(sqlAdmin)) {
                csUsuario.setInt(1, id);
                csUsuario.setString(2, t.getDni());
                csUsuario.setString(3, t.getNombre());
                csUsuario.setString(4, t.getApellidoPaterno());
                csUsuario.setString(5, t.getApellidoMaterno());
                csUsuario.setString(6, t.getTelefono());
                csUsuario.setString(7, t.getCorreoElectronico());
                csUsuario.setString(8, t.getContrasena());
                csUsuario.setDate(9, t.getFechaNacimiento());
                csUsuario.setInt(10, t.getIdDistrito());

                // 1 = ACTIVO
                csUsuario.setInt(11, 1);

                csUsuario.execute();

                csAdmin.setInt(1, id);
                csAdmin.setString(2, t.getImg());
                csAdmin.setDouble(3, t.getMonto_total());
                csAdmin.setDouble(4, t.getMonto_neto());
                csAdmin.setDouble(5, t.getMonto_disponible());

                csAdmin.execute();

                con.commit();

                t.setIdUsuario(id);
                return t;

            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar anfitrión", e);
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
        administrador.setIdUsuario(rs.getInt("idUsuario"));
        administrador.setDni(rs.getString("dni"));
        administrador.setNombre(rs.getString("nombre"));
        administrador.setApellidoPaterno(rs.getString("apellido_paterno"));
        administrador.setApellidoMaterno(rs.getString("apellido_materno"));
        administrador.setTelefono(rs.getString("telefono"));
        administrador.setEdad(rs.getInt("edad"));
        administrador.setCorreoElectronico(rs.getString("correo_electronico"));
        administrador.setContrasena(rs.getString("contrasena"));

        Date fechaRegistro = rs.getDate("fecha_registro");
        if (fechaRegistro != null) {
            administrador.setFechaRegistro(fechaRegistro);
        }

        Date fechaNacimiento = rs.getDate("fecha_nacimiento");
        if (fechaNacimiento != null) {
            administrador.setFechaNacimiento(fechaNacimiento);
        }

        administrador.setIdDistrito(rs.getInt("idDistrito"));
        Genero genero = new Genero();
        genero.setIdGenero(rs.getInt("idGenero"));
        administrador.setGenero(genero);

        //lo del admin
        administrador.setImg(rs.getString("img_qr"));
        administrador.setMonto_total(rs.getDouble("monto_total"));
        administrador.setMonto_neto(rs.getDouble("monto_neto"));
        administrador.setMonto_disponible(rs.getDouble("monto_disponible"));
        return administrador;
    }
}


