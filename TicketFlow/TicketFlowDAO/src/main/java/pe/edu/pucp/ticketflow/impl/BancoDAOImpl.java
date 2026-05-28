package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IBancoDAO;
import pe.edu.pucp.ticketflow.banco.model.Banco;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class BancoDAOImpl implements IBancoDAO {

    @Override
    public Banco create(Banco banco) {
        String sql = "{CALL SP_INSERTAR_BANCO(?, ?, ?)}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, banco.getNombre_largo());
            cs.setString(3, banco.getNombre_corto());

            cs.execute();

            banco.setId(cs.getInt(1));
            return banco;

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear banco", e);
        }
    }

    @Override
    public Banco read(Integer id) {
        String sql = "{CALL SP_LEER_BANCO(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearBanco(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al leer banco", e);
        }

        return null;
    }

    @Override
    public Banco update(Banco banco, Integer id) {
        String sql = "{CALL SP_ACTUALIZAR_BANCO(?, ?, ?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.setString(2, banco.getNombre_largo());
            cs.setString(3, banco.getNombre_corto());

            cs.execute();

            banco.setId(id);
            return banco;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar banco", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "{CALL SP_ELIMINAR_BANCO(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar banco", e);
        }
    }

    @Override
    public List<Banco> listAll() {
        List<Banco> bancos = new ArrayList<>();
        String sql = "{CALL SP_LISTAR_BANCOS()}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Banco banco = mapearBanco(rs);
                bancos.add(banco);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar bancos", e);
        }

        return bancos;
    }

    private Banco mapearBanco(ResultSet rs) throws SQLException {
        Banco banco = new Banco();
        banco.setId(rs.getInt("idBanco"));
        banco.setNombre_largo(rs.getString("nombre_largo"));
        banco.setNombre_corto(rs.getString("nombre_corto"));
        return banco;
    }

}
