package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IClienteDAO;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.usuario.model.Cliente;
import pe.edu.pucp.ticketflow.usuario.model.Genero;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements IClienteDAO {

    @Override
    public Cliente create(Cliente t) {
        String sql = "{CALL SP_INSERTAR_CLIENTE(?, ?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, t.getIdUsuario());
            cs.setInt(2, t.getPuntosBonus());

            cs.execute();
            return t;

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear cliente", e);
        }
    }

    @Override
    public Cliente read(Integer id) {
        String sql = "{CALL SP_LEER_CLIENTE(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al leer cliente", e);
        }
    }

    @Override
    public Cliente update(Cliente t, Integer id) {
        String sqlUsuario = "{CALL SP_ACTUALIZAR_USUARIO(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement csUsuario = con.prepareCall(sqlUsuario)) {

            csUsuario.setInt(1, id);
            csUsuario.setString(2, t.getDni());
            csUsuario.setString(3, t.getNombre());
            csUsuario.setString(4, t.getApellidoPaterno());
            csUsuario.setString(5, t.getApellidoMaterno());
            csUsuario.setString(6, t.getTelefono());
            csUsuario.setInt(7, t.getEdad());

            int idGenero = 1;
            if (t.getGenero() != null && t.getGenero().getIdGenero() > 0) {
                idGenero = t.getGenero().getIdGenero();
            }
            csUsuario.setInt(8, idGenero);

            csUsuario.setString(9, t.getCorreoElectronico());
            csUsuario.setString(10, t.getContrasena());

            if (t.getFechaNacimiento() != null) {
                csUsuario.setDate(11, t.getFechaNacimiento());
            } else {
                csUsuario.setNull(11, java.sql.Types.DATE);
            }

            int idDistrito = t.getIdDistrito() > 0 ? t.getIdDistrito() : 1;
            csUsuario.setInt(12, idDistrito);

            // 1 = ACTIVO
            csUsuario.setInt(13, 1);

            csUsuario.executeUpdate();

            t.setIdUsuario(id);
            return t;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar cliente", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sqlCliente = "{CALL SP_ELIMINAR_CLIENTE(?)}";
        String sqlUsuario = "{CALL SP_ELIMINAR_USUARIO(?)}";

        try (Connection con = DBManager.getInstance().getConnection()) {

            con.setAutoCommit(false);

            try (CallableStatement csCliente = con.prepareCall(sqlCliente);
                 CallableStatement csUsuario = con.prepareCall(sqlUsuario)) {

                csCliente.setInt(1, id);
                csCliente.execute();

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
            throw new RuntimeException("Error al eliminar cliente", e);
        }
    }

    @Override
    public List<Cliente> listAll() {
        String sql = "{CALL SP_LISTAR_CLIENTES()}";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }

            return clientes;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar clientes", e);
        }
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();

        cliente.setIdUsuario(rs.getInt("idUsuario"));
        cliente.setDni(rs.getString("dni"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellidoPaterno(rs.getString("apellido_paterno"));
        cliente.setApellidoMaterno(rs.getString("apellido_materno"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setEdad(rs.getInt("edad"));
        cliente.setCorreoElectronico(rs.getString("correo_electronico"));
        cliente.setContrasena(rs.getString("contrasena"));

        Date fechaRegistro = rs.getDate("fecha_registro");
        if (fechaRegistro != null) {
            cliente.setFechaRegistro(fechaRegistro);
        }

        Date fechaNacimiento = rs.getDate("fecha_nacimiento");
        if (fechaNacimiento != null) {
            cliente.setFechaNacimiento(fechaNacimiento);
        }

        cliente.setIdDistrito(rs.getInt("idDistrito"));
        Genero genero = new Genero();
        genero.setIdGenero(rs.getInt("idGenero"));
        cliente.setGenero(genero);

        cliente.setPuntosBonus(rs.getInt("puntos_bonus"));

        return cliente;
    }

    @Override
    public int readPuntos(Integer id) {
        String sql = "{CALL SP_OBTENER_PUNTOS_BONUS(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("puntos_bonus");
                }
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al leer cliente", e);
        }
    }
}
