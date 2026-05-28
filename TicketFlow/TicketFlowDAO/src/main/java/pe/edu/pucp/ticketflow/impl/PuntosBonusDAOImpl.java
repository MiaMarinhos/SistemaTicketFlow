package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IPuntosBonusDAO;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.puntosBonus.model.PuntosBonus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PuntosBonusDAOImpl implements IPuntosBonusDAO {
    @Override
    public PuntosBonus create(PuntosBonus t){
        String sql = "{CALL sp_create_puntos_bonus(?,?,?)}";

        try(Connection con = DBManager.getInstance().getConnection();
            CallableStatement cs = con.prepareCall(sql)){

            cs.registerOutParameter(1, Types.INTEGER); //Si es AUTO_INCREMENT

            cs.setInt(2,t.getPuntosCanjeables());
            cs.setInt(3,t.getDescuento());

            cs.execute();
            t.setIdPuntosBonus(cs.getInt(1));    //Si es AUTO_INCREMENT
            return t;
        }
        catch (SQLException e){
            throw new RuntimeException("Error al crear PuntosBonus", e);
        }
    }
    @Override
    public PuntosBonus read(Integer id){
        String sql = "{CALL sp_read_puntos_bonus(?)}";

        try(Connection con = DBManager.getInstance().getConnection();
            CallableStatement cs = con.prepareCall(sql)){

            cs.setInt(1, id);
            try(ResultSet rs = cs.executeQuery()){
                if(rs.next()){
                    PuntosBonus t = new PuntosBonus();
                    mapear(rs, t);
                    return t;
                }
            }
            return null;
        }
        catch (SQLException e){
            throw new RuntimeException("Error al leer PuntosBonus", e);
        }
    }
    @Override
    public PuntosBonus update(PuntosBonus t, Integer id) {
        String sql = "{CALL sp_update_puntos_bonus(?,?,?)}";

        try(Connection con = DBManager.getInstance().getConnection();
            CallableStatement cs = con.prepareCall(sql)){

            cs.setInt(1, id);
            cs.setInt(2,t.getPuntosCanjeables());
            cs.setInt(3,t.getDescuento());

            cs.execute();
            return t;
        }
        catch (SQLException e){
            throw new RuntimeException("Error al actualizar PuntosBonus", e);
        }
    }
    @Override
    public void delete(Integer id){
        String sql = "{CALL sp_delete_puntos_bonus(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException("Error en eliminar PuntosBonus", e);
        }
    }
    @Override
    public List<PuntosBonus> listAll(){
        List<PuntosBonus> lista = new ArrayList<>();
        String sql = "{CALL sp_listAll_puntos_bonus()}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while(rs.next()){
                PuntosBonus t = new PuntosBonus();
                mapear(rs, t);
                lista.add(t);
            }
            return lista;
        }
        catch (SQLException e) {
            throw new RuntimeException("Error en listar PuntosBonus", e);
        }
    }

    private void mapear(ResultSet rs, PuntosBonus t) throws SQLException {
        t.setIdPuntosBonus(rs.getInt("idPuntos_bonus"));
        t.setPuntosCanjeables(rs.getInt("puntos_canjeables"));
        t.setDescuento(rs.getInt("descuento"));
    }
}
