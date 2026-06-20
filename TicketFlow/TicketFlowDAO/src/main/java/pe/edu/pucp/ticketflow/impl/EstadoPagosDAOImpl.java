package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IEstadoPagosDAO;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.pago.model.EstadoPago;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class EstadoPagosDAOImpl implements IEstadoPagosDAO {
    @Override
    public EstadoPago create(EstadoPago t){
        String sql = "{CALL sp_create_estado_pagos(?,?)}";

        try(Connection con = DBManager.getInstance().getConnection();
            CallableStatement cs = con.prepareCall(sql)){

            cs.registerOutParameter(1, Types.INTEGER); //Si es AUTO_INCREMENT

            cs.setString(2,t.getEstado());

            cs.execute();
            t.setIdEstadoPago(cs.getInt(1));    //Si es AUTO_INCREMENT
            return t;
        }
        catch (SQLException e){
            throw new RuntimeException("Error al crear EstadoPago", e);
        }
    }
    @Override
    public EstadoPago read(Integer id){
        String sql = "{CALL sp_read_estado_pagos(?)}";

        try(Connection con = DBManager.getInstance().getConnection();
            CallableStatement cs = con.prepareCall(sql)){

            cs.setInt(1, id);
            try(ResultSet rs = cs.executeQuery()){
                if(rs.next()){
                    EstadoPago t = new EstadoPago();
                    mapear(rs, t);
                    return t;
                }
            }
            return null;
        }
        catch (SQLException e){
            throw new RuntimeException("Error al leer EstadoPago", e);
        }
    }
    @Override
    public EstadoPago update(EstadoPago t, Integer id) {
        String sql = "{CALL sp_update_estado_pagos(?,?)}";

        try(Connection con = DBManager.getInstance().getConnection();
            CallableStatement cs = con.prepareCall(sql)){

            cs.setInt(1, id);
            cs.setString(2,t.getEstado());

            cs.execute();
            return t;
        }
        catch (SQLException e){
            throw new RuntimeException("Error al actualizar EstadoPago", e);
        }
    }
    @Override
    public void delete(Integer id){
        String sql = "{CALL sp_delete_estado_pagos(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException("Error en eliminar EstadoPago", e);
        }
    }
    @Override
    public List<EstadoPago> listAll(){
        List<EstadoPago> lista = new ArrayList<>();
        String sql = "{CALL sp_listAll_estado_pagos()}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while(rs.next()){
                EstadoPago t = new EstadoPago();
                mapear(rs, t);
                lista.add(t);
            }
            return lista;
        }
        catch (SQLException e) {
            throw new RuntimeException("Error en listar EstadoPago", e);
        }
    }

    private void mapear(ResultSet rs, EstadoPago t) throws SQLException{
        t.setIdEstadoPago(rs.getInt("idestado_pagos"));
        t.setEstado(rs.getString("estado"));
    }
}
