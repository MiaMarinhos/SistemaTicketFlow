package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IAnfitrionDAO;
import pe.edu.pucp.ticketflow.banco.model.Banco;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.usuario.model.Anfitrion;
import pe.edu.pucp.ticketflow.usuario.model.Genero;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnfitrionDAOImpl implements IAnfitrionDAO {

    @Override
    public Anfitrion create(Anfitrion t) {
        String sql = "{CALL SP_INSERTAR_ANFITRION(?, ?, ?, ?, ?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, t.getIdUsuario());
            cs.setString(2, t.getRazonSocial());
            cs.setString(3, t.getRuc());
            cs.setString(4, t.getCuentaBancaria());
            cs.setInt(5, t.getBanco().getId());

            cs.execute();
            return t;

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear anfitrión", e);
        }
    }

    @Override
    public Anfitrion read(Integer id) {
        String sql = "{CALL SP_LEER_ANFITRION(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearAnfitrion(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al leer anfitrión", e);
        }
    }

    @Override
    public Anfitrion update(Anfitrion t, Integer id) {
        String sqlUsuario = "{CALL SP_ACTUALIZAR_USUARIO(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        String sqlAnfitrion = "{CALL SP_ACTUALIZAR_ANFITRION(?, ?, ?, ?, ?)}";

        try (Connection con = DBManager.getInstance().getConnection()) {

            con.setAutoCommit(false);

            try (CallableStatement csUsuario = con.prepareCall(sqlUsuario);
                 CallableStatement csAnfitrion = con.prepareCall(sqlAnfitrion)) {

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

                csAnfitrion.setInt(1, id);
                csAnfitrion.setString(2, t.getRazonSocial());
                csAnfitrion.setString(3, t.getRuc());
                csAnfitrion.setString(4, t.getCuentaBancaria());
                csAnfitrion.setInt(5, t.getBanco().getId());

                csAnfitrion.execute();

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
        String sqlAnfitrion = "{CALL SP_ELIMINAR_ANFITRION(?)}";
        String sqlUsuario = "{CALL SP_ELIMINAR_USUARIO(?)}";

        try (Connection con = DBManager.getInstance().getConnection()) {

            con.setAutoCommit(false);

            try (CallableStatement csAnfitrion = con.prepareCall(sqlAnfitrion);
                 CallableStatement csUsuario = con.prepareCall(sqlUsuario)) {

                csAnfitrion.setInt(1, id);
                csAnfitrion.execute();

                csUsuario.setInt(1, id);
                csUsuario.execute();

                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar anfitrión", e);
        }
    }

    @Override
    public List<Anfitrion> listAll() {
        String sql = "{CALL SP_LISTAR_ANFITRIONES()}";
        List<Anfitrion> anfitriones = new ArrayList<>();

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                anfitriones.add(mapearAnfitrion(rs));
            }

            return anfitriones;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar anfitriones", e);
        }
    }

    private Anfitrion mapearAnfitrion(ResultSet rs) throws SQLException {
        Anfitrion anfitrion = new Anfitrion();

        anfitrion.setIdUsuario(rs.getInt("idUsuario"));
        anfitrion.setDni(rs.getString("dni"));
        anfitrion.setNombre(rs.getString("nombre"));
        anfitrion.setApellidoPaterno(rs.getString("apellido_paterno"));
        anfitrion.setApellidoMaterno(rs.getString("apellido_materno"));
        anfitrion.setTelefono(rs.getString("telefono"));
        anfitrion.setEdad(rs.getInt("edad"));
        anfitrion.setCorreoElectronico(rs.getString("correo_electronico"));
        anfitrion.setContrasena(rs.getString("contrasena"));

        Date fechaRegistro = rs.getDate("fecha_registro");
        if (fechaRegistro != null) {
            anfitrion.setFechaRegistro(fechaRegistro);
        }

        Date fechaNacimiento = rs.getDate("fecha_nacimiento");
        if (fechaNacimiento != null) {
            anfitrion.setFechaNacimiento(fechaNacimiento);
        }

        anfitrion.setIdDistrito(rs.getInt("idDistrito"));
        Genero genero = new Genero();
        genero.setIdGenero(rs.getInt("idGenero"));
        anfitrion.setGenero(genero);

        anfitrion.setRazonSocial(rs.getString("razon_social"));
        anfitrion.setRuc(rs.getString("ruc"));
        anfitrion.setCuentaBancaria(rs.getString("cuenta_bancaria"));

        Banco banco = new Banco();
        banco.setId(rs.getInt("idBanco"));
        banco.setNombre_largo(rs.getString("nombre_largo"));
        banco.setNombre_corto(rs.getString("nombre_corto"));
        anfitrion.setBanco(banco);

        return anfitrion;
    }
}