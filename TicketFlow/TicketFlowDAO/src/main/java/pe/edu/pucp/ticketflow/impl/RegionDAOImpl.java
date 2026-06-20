package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IRegionDAO;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.ubicacion.model.Region;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegionDAOImpl implements IRegionDAO {
    @Override
    public Region create(Region t) {
        String sql = "{CALL SP_INSERTAR_REGION(?, ?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, t.getNombre());
            cs.execute();
            t.setIdRegion(cs.getInt(1));
            return t;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear región", e);
        }
    }

    @Override
    public Region read(Integer id) {
        String sql = "{CALL SP_LEER_REGION(?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    Region r = new Region();
                    r.setIdRegion(rs.getInt("idRegion"));
                    r.setNombre(rs.getString("nombre"));
                    return r;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer Region", e);
        }
        return null;
    }

    @Override
    public Region update(Region t, Integer id) {
        String sql = "{CALL SP_ACTUALIZAR_REGION(?, ?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.setString(2, t.getNombre());

            int affectedRows = cs.executeUpdate(); // Aunque es execute(), update devuelve filas afectadas
            return t;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar Region", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "{CALL SP_ELIMINAR_REGION(?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar Region", e);
        }
    }

    @Override
    public List<Region> listAll() {
        List<Region> lista = new ArrayList<>();
        String sql = "{CALL SP_LISTAR_REGIONES()}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Region r = new Region();
                r.setIdRegion(rs.getInt("idRegion"));
                r.setNombre(rs.getString("nombre"));
                lista.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar Regiones", e);
        }
        return lista;
    }
}
