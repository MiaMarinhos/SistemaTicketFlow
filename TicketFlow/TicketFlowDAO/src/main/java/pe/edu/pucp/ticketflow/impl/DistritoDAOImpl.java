package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IDistritoDAO;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.ubicacion.model.Distrito;
import pe.edu.pucp.ticketflow.ubicacion.model.Region;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DistritoDAOImpl implements IDistritoDAO {

    @Override
    public Distrito create(Distrito t) {
        String sql = "{CALL SP_INSERTAR_DISTRITO(?, ?, ?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, t.getNombre());
            cs.setInt(3, t.getRegion().getIdRegion());

            cs.execute();
            t.setIdDistrito(cs.getInt(1));
            return t;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar Distrito", e);
        }
    }

    @Override
    public Distrito read(Integer id) {
        String sql = "{CALL SP_LEER_DISTRITO(?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearDistrito(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer Distrito", e);
        }
        return null;
    }

    @Override
    public Distrito update(Distrito t, Integer id) {
        String sql = "{CALL SP_ACTUALIZAR_DISTRITO(?, ?, ?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.setString(2, t.getNombre());
            cs.setInt(3, t.getRegion().getIdRegion());

            cs.execute();
            return t;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar Distrito", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM distrito WHERE idDistrito = ?";
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar Distrito", e);
        }
    }

    @Override
    public List<Distrito> listAll() {
        List<Distrito> lista = new ArrayList<>();
        String sql = "{CALL SP_LISTAR_DISTRITOS()}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearDistrito(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar Distritos", e);
        }
        return lista;
    }

    @Override
    public Distrito buscarPorNombre(String nombre) {
        String sql = "{CALL SP_BUSCAR_DISTRITO_POR_NOMBRE(?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, nombre);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearDistrito(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar distrito por nombre", e);
        }
        return null;
    }

    private Distrito mapearDistrito(ResultSet rs) throws SQLException {
        Distrito d = new Distrito();
        d.setIdDistrito(rs.getInt("idDistrito"));
        d.setNombre(rs.getString("nombre_distrito"));

        Region r = new Region();
        r.setIdRegion(rs.getInt("idRegion"));
        r.setNombre(rs.getString("nombre_region"));

        d.setRegion(r);
        return d;
    }
}
