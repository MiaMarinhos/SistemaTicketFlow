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
            throw new RuntimeException("Error al leer cliente", e);
        }
    }

    @Override
    public Cliente update(Cliente t, Integer id) {
        String sqlUsuario = "{CALL SP_ACTUALIZAR_USUARIO(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        String sqlCliente = "{CALL SP_ACTUALIZAR_CLIENTE(?, ?)}";

        try (Connection con = DBManager.getInstance().getConnection()) {

            con.setAutoCommit(false);

            try (CallableStatement csUsuario = con.prepareCall(sqlUsuario);
                 CallableStatement csCliente = con.prepareCall(sqlCliente)) {

                csUsuario.setInt(1, id);
                csUsuario.setString(2, t.getDni());
                csUsuario.setString(3, t.getNombre());
                csUsuario.setString(4, t.getApellidoPaterno());
                csUsuario.setString(5, t.getApellidoMaterno());
                csUsuario.setString(6, t.getTelefono());
                csUsuario.setString(7, t.getCorreoElectronico());
                csUsuario.setString(8, t.getContrasena());

                if (t.getFechaNacimiento() != null) {
                    csUsuario.setDate(9, t.getFechaNacimiento());
                } else {
                    csUsuario.setDate(9, null);
                }

                csUsuario.setInt(10, t.getIdDistrito());

                // Como tu Usuario no tiene getIdEstado(), usamos 1 = ACTIVO.
                // Esto depende de que hayas insertado ACTIVO como primer estado.
                csUsuario.setInt(11, 1);

                csUsuario.execute();

                csCliente.setInt(1, id);
                csCliente.setInt(2, t.getPuntosBonus());
                csCliente.execute();

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

}
