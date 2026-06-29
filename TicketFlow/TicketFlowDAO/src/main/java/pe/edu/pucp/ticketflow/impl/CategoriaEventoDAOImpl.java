package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.ICategoriaEventoDAO;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.evento.model.categoria_evento;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class CategoriaEventoDAOImpl implements ICategoriaEventoDAO {

    @Override
    public categoria_evento create(categoria_evento ce){

        String sql =
                "{CALL SP_INSERTAR_CATEGORIA_EVENTO(?,?,?)}";

        try(Connection connection =
                    DBManager.getInstance().getConnection();

            CallableStatement cs =
                    connection.prepareCall(sql)) {

            cs.setInt(1,
                    ce.getIdCategoria_evento());
            cs.setString(2,
                    ce.getNombre());
            cs.setInt(3,
                    ce.getDias_para_publicacion());

            cs.execute();

            return ce;

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public categoria_evento read(Integer id){

        String sql =
                "{CALL SP_LEER_CATEGORIA_EVENTO(?)}";

        try(Connection connection =
                    DBManager.getInstance().getConnection();

            CallableStatement cs =
                    connection.prepareCall(sql)) {

            cs.setInt(1,id);

            try(ResultSet rs =
                        cs.executeQuery()){

                if(rs.next()){

                    categoria_evento ce =
                            new categoria_evento();

                    ce.setIdCategoria_evento(
                            rs.getInt(
                                    "idCategoria_evento"));

                    ce.setNombre(
                            rs.getString("nombre"));

                    ce.setDias_para_publicacion(
                            rs.getInt(
                                    "dias_para_publicacion"));

                    return ce;
                }
            }

        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public categoria_evento update(categoria_evento ce,Integer id){

        String sql =
                "{CALL SP_ACTUALIZAR_CATEGORIA_EVENTO(?,?,?)}";

        try(Connection connection =
                    DBManager.getInstance().getConnection();

            CallableStatement cs =
                    connection.prepareCall(sql)){

            cs.setInt(1,id);
            cs.setString(2,ce.getNombre());
            cs.setInt(3,
                    ce.getDias_para_publicacion());

            cs.executeUpdate();

            return ce;

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id){

        String sql =
                "{CALL SP_ELIMINAR_CATEGORIA_EVENTO(?)}";

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
    public List<categoria_evento> listAll(){

        String sql =
                "{CALL SP_LISTAR_CATEGORIA_EVENTOS()}";

        try(Connection connection =
                    DBManager.getInstance().getConnection();

            CallableStatement cs =
                    connection.prepareCall(sql);

            ResultSet rs =
                    cs.executeQuery()){

            List<categoria_evento> lista =
                    new ArrayList<>();

            while(rs.next()){

                categoria_evento ce =
                        new categoria_evento();

                ce.setIdCategoria_evento(
                        rs.getInt(
                                "idCategoria_evento"));

                ce.setNombre(
                        rs.getString("nombre"));

                ce.setDias_para_publicacion(
                        rs.getInt(
                                "dias_para_publicacion"));

                lista.add(ce);
            }

            return lista;

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<categoria_evento> listarCategorias() throws Exception {
        List<categoria_evento> categorias = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            // Obtenemos la conexión (ajusta esto si tu DBManager funciona distinto)
            con = DBManager.getInstance().getConnection();

            // Llamamos a tu procedimiento almacenado
            cs = con.prepareCall("{call SP_LISTAR_CATEGORIA_EVENTOS()}");
            rs = cs.executeQuery();

            while (rs.next()) {
                categoria_evento categoria = new categoria_evento();

                // Mapeamos las columnas exactas de tu tabla categoria_evento
                categoria.setIdCategoria_evento(rs.getInt("idCategoria_evento"));
                categoria.setNombre(rs.getString("nombre"));
                // categoria.setDiasParaPublicacion(rs.getInt("dias_para_publicacion")); // Opcional si lo necesitas

                categorias.add(categoria);
            }
        } finally {
            // Cerramos las conexiones para evitar fugas de memoria
            if (rs != null) rs.close();
            if (cs != null) cs.close();
            if (con != null) con.close();
        }

        return categorias;
    }
}