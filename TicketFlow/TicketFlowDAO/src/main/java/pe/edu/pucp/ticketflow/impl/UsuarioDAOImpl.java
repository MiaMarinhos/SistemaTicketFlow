package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IDistritoDAO;
import pe.edu.pucp.ticketflow.IUsuarioDAO;
import pe.edu.pucp.ticketflow.administrador.model.Administrador;
import pe.edu.pucp.ticketflow.ubicacion.model.Distrito;
import pe.edu.pucp.ticketflow.usuario.model.*;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements IUsuarioDAO {

    @Override
    public Usuario create(Usuario t) {
        String sql = "{CALL SP_INSERTAR_USUARIO(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, t.getDni());
            cs.setString(3, t.getNombre());
            cs.setString(4, t.getApellidoPaterno());
            cs.setString(5, t.getApellidoMaterno());
            cs.setString(6, t.getTelefono());
            cs.setString(7, t.getCorreoElectronico());
            cs.setString(8, t.getContrasena());
            cs.setDate(9, t.getFechaRegistro());
            cs.setDate(10, t.getFechaNacimiento());
            cs.setInt(11, t.getDistrito().getIdDistrito());
            cs.setInt(12, t.getEstado().getIdEstadoUsuario());
            cs.setInt(13, t.getTipo().getIdTipoUsuario());

            cs.execute();
            t.setIdUsuario(cs.getInt(1));
            return t;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear usuario", e);
        }
    }

    @Override
    public Usuario read(Integer id) {
        String sql = "{CALL SP_LEER_USUARIO(?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    Usuario u;
                    int tipo = rs.getInt("idTipo_usuario");

                    switch (tipo) {
                        case 1: u = new Cliente(); break;
                        case 2: u = new Anfitrion(); break;
                        default: u = new Cliente();
                    }

                    mapearUsuario(rs, u);
                    return u;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer usuario", e);
        }
        return null;
    }

    @Override
    public Usuario update(Usuario t, Integer id) {
        String sql = "{CALL SP_ACTUALIZAR_USUARIO(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.setString(2, t.getDni());
            cs.setString(3, t.getNombre());
            cs.setString(4, t.getApellidoPaterno());
            cs.setString(5, t.getApellidoMaterno());
            cs.setString(6, t.getTelefono());
            cs.setString(7, t.getCorreoElectronico());
            cs.setString(8, t.getContrasena());
            cs.setDate(9, t.getFechaNacimiento());
            cs.setInt(10, t.getDistrito().getIdDistrito());
            cs.setInt(11, t.getEstado().getIdEstadoUsuario());

            cs.execute();
            return t;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "{CALL SP_ELIMINAR_USUARIO(?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setInt(1, id);
            cs.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error en eliminar al usuario", e);
        }
    }

    @Override
    public List<Usuario> listAll() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "{CALL SP_LISTAR_USUARIOS()}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Usuario u;
                int tipo = rs.getInt("idTipo_usuario");

                switch (tipo) {
                    case 1: u = new Cliente(); break;
                    case 2: u = new Anfitrion(); break;
                    default: u = new Cliente();
                }
                mapearUsuario(rs, u);
                lista.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios", e);
        }
        return lista;
    }

    @Override
    public List<Usuario> buscarPorNombre(String nombre) {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "{CALL SP_BUSCAR_USUARIO_NOMBRE(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, nombre);

            try (ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {

                    Usuario u;
                    int tipo = rs.getInt("idTipo_usuario");
                    switch (tipo) {
                        case 1:
                            u = new Cliente();
                            break;

                        case 2:
                            u = new Anfitrion();
                            break;

                        default:
                            u = new Cliente();
                            break;
                    }

                    mapearUsuario(rs, u);
                    usuarios.add(u);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuarios por nombre", e);
        }
        return usuarios;
    }

    @Override
    public List<Usuario> filtrarPorTipo(Integer idTipoUsuario) {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "{CALL SP_FILTRAR_USUARIO_TIPO(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idTipoUsuario);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Usuario u;
                    int tipo = rs.getInt("idTipo_usuario");
                    switch (tipo) {
                        case 1:
                            u = new Cliente();
                            break;
                        case 2:
                            u = new Anfitrion();
                            break;
                        default:
                            u = new Cliente();
                            break;
                    }
                    mapearUsuario(rs, u);
                    usuarios.add(u);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al filtrar usuarios por tipo", e);
        }
        return usuarios;
    }

    @Override
    public List<Usuario> filtrarPorEstado(Integer idEstado) {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "{CALL SP_FILTRAR_USUARIO_ESTADO(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idEstado);

            try (ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {

                    Usuario u;
                    int tipo = rs.getInt("idTipo_usuario");

                    switch (tipo) {
                        case 1:
                            u = new Cliente();
                            break;
                        case 2:
                            u = new Anfitrion();
                            break;

                        default:
                            u = new Cliente();
                            break;
                    }
                    mapearUsuario(rs, u);
                    usuarios.add(u);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al filtrar usuarios por estado", e);
        }
        return usuarios;
    }

    @Override
    public Usuario bloquearUsuario(Integer idUsuario) {

        String sql = "{CALL SP_BLOQUEAR_USUARIO(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idUsuario);

            cs.execute();

            return read(idUsuario);

        } catch (SQLException e) {
            throw new RuntimeException("Error al bloquear usuario", e);
        }
    }

    @Override
    public Usuario desbloquearUsuario(Integer idUsuario) {

        String sql = "{CALL SP_DESBLOQUEAR_USUARIO(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idUsuario);

            cs.execute();

            return read(idUsuario);

        } catch (SQLException e) {
            throw new RuntimeException("Error al desbloquear usuario", e);
        }
    }

    @Override
    public Usuario ObtenerUsuarioPorRol(Integer id, String rol) {
        String sql = "{CALL SP_LEER_USUARIO_POR_ROL(?, ?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.setString(2, rol);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    Usuario u;
                    int tipo = rs.getInt("idTipo_usuario");

                    switch (tipo) {
                        case 1: u = new Cliente(); break;
                        case 2: u = new Anfitrion(); break;
                        default: u = new Administrador();
                    }

                    u.setIdUsuario(rs.getInt("idUsuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellidoPaterno(rs.getString("apellido_paterno"));
                    u.setApellidoMaterno(rs.getString("apellido_materno"));
                    u.setCorreoElectronico(rs.getString("correo_electronico"));
                    u.setContrasena(rs.getString("contrasena"));
                    TipoUsuario tipoUsuario = new TipoUsuario();
                    tipoUsuario.setIdTipoUsuario(rs.getInt("idTipo_usuario"));
                    tipoUsuario.setTipoUsuario(rs.getString("nombre_rol"));
                    u.setTipo(tipoUsuario);

                    return u;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer usuario", e);
        }
        return null;
    }

    @Override
    public int ComprobarTipoUsuario(String correo, String rol) {
        String sql = "{CALL SP_COMPROBAR_USUARIO(?, ?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, correo);
            cs.setString(2, rol);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idUsuario");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer usuario", e);
        }
        return -1;
    }

    private void mapearUsuario(ResultSet rs, Usuario u) throws SQLException {
        u.setIdUsuario(rs.getInt("idUsuario"));
        u.setDni(rs.getString("dni"));
        u.setNombre(rs.getString("nombre"));
        u.setApellidoPaterno(rs.getString("apellido_paterno"));
        u.setApellidoMaterno(rs.getString("apellido_materno"));
        u.setTelefono(rs.getString("telefono"));
        u.setCorreoElectronico(rs.getString("correo_electronico"));
        u.setContrasena(rs.getString("contrasena"));
        u.setFechaRegistro(rs.getDate("fecha_registro"));
        u.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
        u.setIdDistrito(rs.getInt("idDistrito"));

        EstadoUsuario e = new EstadoUsuario();
        e.setIdEstadoUsuario(rs.getInt("idEstado_usuario"));
        e.setNombre(rs.getString("nombre_estado"));
        u.setEstado(e);

        TipoUsuario t = new TipoUsuario();
        t.setIdTipoUsuario(rs.getInt("idTipo_usuario"));
        t.setTipoUsuario(rs.getString("nombre_tipo_usuario"));
        u.setTipo(t);
    }
}