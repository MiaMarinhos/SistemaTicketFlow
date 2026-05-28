package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IEstadoPublicacionDAO;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.evento.model.EstadoPublicacion;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class EstadoPublicacionDAOImpl implements IEstadoPublicacionDAO {

    @Override
    public EstadoPublicacion create(EstadoPublicacion ep){

        String sql =
                "{CALL SP_INSERTAR_ESTADO_PUBLICACION(?,?)}";

        try(Connection connection =
                    DBManager.getInstance().getConnection();

            CallableStatement cs =
                    connection.prepareCall(sql)){

            cs.setInt(1,
                    ep.getIdEstado_publicacion());

            cs.setString(2,
                    ep.getEstado());

            cs.execute();

            return ep;

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public EstadoPublicacion read(Integer id){

        String sql =
                "{CALL SP_LEER_ESTADO_PUBLICACION(?)}";

        try(Connection connection =
                    DBManager.getInstance().getConnection();

            CallableStatement cs =
                    connection.prepareCall(sql)){

            cs.setInt(1,id);

            try(ResultSet rs =
                        cs.executeQuery()){

                if(rs.next()){

                    EstadoPublicacion ep =
                            new EstadoPublicacion();

                    ep.setIdEstado_publicacion(
                            rs.getInt(
                                    "idEstado_publicacion"));

                    ep.setEstado(
                            rs.getString("estado"));

                    return ep;
                }
            }

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public EstadoPublicacion update(EstadoPublicacion ep,Integer id){

        String sql =
                "{CALL SP_ACTUALIZAR_ESTADO_PUBLICACION(?,?)}";

        try(Connection connection =
                    DBManager.getInstance().getConnection();

            CallableStatement cs =
                    connection.prepareCall(sql)){

            cs.setInt(1,id);
            cs.setString(2,
                    ep.getEstado());

            cs.executeUpdate();

            return ep;

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id){

        String sql =
                "{CALL SP_ELIMINAR_ESTADO_PUBLICACION(?)}";

        try(Connection connection =
                    DBManager.getInstance().getConnection();

            CallableStatement cs =
                    connection.prepareCall(sql)){

            cs.setInt(1,id);

            cs.executeUpdate();

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EstadoPublicacion> listAll(){

        String sql =
                "{CALL SP_LISTAR_ESTADO_PUBLICACIONES()}";

        try(Connection connection =
                    DBManager.getInstance().getConnection();

            CallableStatement cs =
                    connection.prepareCall(sql);

            ResultSet rs =
                    cs.executeQuery()){

            List<EstadoPublicacion> lista =
                    new ArrayList<>();

            while(rs.next()){

                EstadoPublicacion ep =
                        new EstadoPublicacion();

                ep.setIdEstado_publicacion(
                        rs.getInt(
                                "idEstado_publicacion"));

                ep.setEstado(
                        rs.getString("estado"));

                lista.add(ep);
            }

            return lista;

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
