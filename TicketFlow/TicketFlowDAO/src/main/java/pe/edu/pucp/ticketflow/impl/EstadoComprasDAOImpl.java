package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IEstadoComprasDAO;
import pe.edu.pucp.ticketflow.compra.model.EstadoCompra;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoComprasDAOImpl implements IEstadoComprasDAO {
    @Override
    public EstadoCompra create(EstadoCompra t){
        String sql = "{CALL sp_create_estado_compras(?,?)}";

        try(Connection con = DBManager.getInstance().getConnection();
            CallableStatement cs = con.prepareCall(sql)){

            cs.registerOutParameter(1, Types.INTEGER); //Si es AUTO_INCREMENT

            cs.setString(2, t.getEstado());

            cs.execute();
            t.setIdEstadoCompra(cs.getInt(1));    //Si es AUTO_INCREMENT
            return t;
        }
        catch (SQLException e){
            throw new RuntimeException("Error al crear EstadoCompra", e);
        }
    }
    @Override
    public EstadoCompra read(Integer id){
        String sql = "{CALL sp_read_estado_compras(?)}";

        try(Connection con = DBManager.getInstance().getConnection();
            CallableStatement cs = con.prepareCall(sql)){

            cs.setInt(1, id);
            try(ResultSet rs = cs.executeQuery()){
                if(rs.next()){
                    EstadoCompra t = new EstadoCompra();
                    mapear(rs, t);
                    return t;
                }
            }
            return null;
        }
        catch (SQLException e){
            throw new RuntimeException("Error al leer EstadoCompra", e);
        }
    }
    @Override
    public EstadoCompra update(EstadoCompra t, Integer id) {
        String sql = "{CALL sp_update_estado_compras(?,?)}";

        try(Connection con = DBManager.getInstance().getConnection();
            CallableStatement cs = con.prepareCall(sql)){

            cs.setInt(1, id);
            cs.setString(2, t.getEstado());

            cs.execute();
            return t;
        }
        catch (SQLException e){
            throw new RuntimeException("Error al actualizar Tipo", e);
        }
    }
    @Override
    public void delete(Integer id){
        String sql = "{CALL sp_delete_estado_compras(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException("Error en eliminar Tipo", e);
        }
    }
    @Override
    public List<EstadoCompra> listAll(){
        List<EstadoCompra> lista = new ArrayList<>();
        String sql = "{CALL sp_listAll_estado_compras()}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while(rs.next()){
                EstadoCompra t = new EstadoCompra();
                mapear(rs, t);
                lista.add(t);
            }
            return lista;
        }
        catch (SQLException e) {
            throw new RuntimeException("Error en listar Tipo", e);
        }
    }

    private void mapear(ResultSet rs, EstadoCompra t) throws SQLException{
        t.setIdEstadoCompra(rs.getInt("idEstado"));
        t.setEstado(rs.getString("estado"));
    }
}
