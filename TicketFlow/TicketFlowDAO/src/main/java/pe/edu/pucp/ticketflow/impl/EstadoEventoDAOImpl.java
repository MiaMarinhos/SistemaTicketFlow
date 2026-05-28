package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IEstadoEventoDAO;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.evento.model.EstadoEvento;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class EstadoEventoDAOImpl implements IEstadoEventoDAO {

    @Override
    public EstadoEvento create(EstadoEvento ee){

        String sql =
                "{CALL SP_INSERTAR_ESTADO_EVENTO(?,?)}";

        try(Connection connection =
                    DBManager.getInstance().getConnection();

            CallableStatement cs =
                    connection.prepareCall(sql)) {

            cs.setInt(1, ee.getIdEstado_evento());
            cs.setString(2, ee.getEstado());

            cs.execute();

            return ee;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EstadoEvento read(Integer id){

        String sql =
                "{CALL SP_LEER_ESTADO_EVENTO(?)}";

        try(Connection connection =
                    DBManager.getInstance().getConnection();

            CallableStatement cs =
                    connection.prepareCall(sql)) {

            cs.setInt(1, id);

            try(ResultSet rs =
                        cs.executeQuery()) {

                if(rs.next()) {

                    EstadoEvento ee =
                            new EstadoEvento();

                    ee.setIdEstado_evento(
                            rs.getInt("idEstado_evento")
                    );

                    ee.setEstado(
                            rs.getString("estado")
                    );

                    return ee;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public EstadoEvento update(
            EstadoEvento ee,
            Integer id
    ){

        String sql =
                "{CALL SP_ACTUALIZAR_ESTADO_EVENTO(?,?)}";

        try(Connection connection =
                    DBManager.getInstance().getConnection();

            CallableStatement cs =
                    connection.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.setString(2, ee.getEstado());

            cs.executeUpdate();

            return ee;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id){

        String sql =
                "{CALL SP_ELIMINAR_ESTADO_EVENTO(?)}";

        try(Connection connection =
                    DBManager.getInstance().getConnection();

            CallableStatement cs =
                    connection.prepareCall(sql)) {

            cs.setInt(1, id);

            cs.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EstadoEvento> listAll(){

        String sql =
                "{CALL SP_LISTAR_ESTADO_EVENTOS()}";

        try(Connection connection =
                    DBManager.getInstance().getConnection();

            CallableStatement cs =
                    connection.prepareCall(sql);

            ResultSet rs =
                    cs.executeQuery()) {

            List<EstadoEvento> estados =
                    new ArrayList<>();

            while(rs.next()){

                EstadoEvento ee =
                        new EstadoEvento();

                ee.setIdEstado_evento(
                        rs.getInt("idEstado_evento")
                );

                ee.setEstado(
                        rs.getString("estado")
                );

                estados.add(ee);
            }

            return estados;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}