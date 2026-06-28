package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IGeneroDAO;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.usuario.model.Genero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GeneroDAOImpl implements IGeneroDAO {
    @Override
    public Genero create(Genero t) {
        return null;
    }

    @Override
    public Genero read(Integer integer) {
        return null;
    }

    @Override
    public Genero update(Genero t, Integer integer) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }

    public List<Genero> listAll() {
        List<Genero> generos = new ArrayList<>();

        String sql = "SELECT idGenero, genero FROM genero ORDER BY genero";

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Genero genero = new Genero();

                genero.setIdGenero(rs.getInt("idGenero"));
                genero.setNombre(rs.getString("genero"));

                generos.add(genero);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar géneros", e);
        }

        return generos;
    }
}
