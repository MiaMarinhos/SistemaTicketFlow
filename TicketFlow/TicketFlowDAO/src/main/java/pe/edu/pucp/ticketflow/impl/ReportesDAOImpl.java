package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IReporteDAO;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ReportesDAOImpl implements IReporteDAO {

    @Override
    public List<Object[]> reporteVentas(Date fechaInicio, Date fechaFin, Integer idCategoria) {

        List<Object[]> lista = new ArrayList<>();
        String sql = "{CALL USP_REPORTE_VENTAS(?, ?, ?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setDate(1, (java.sql.Date) fechaInicio);
            cs.setDate(2, (java.sql.Date) fechaFin);
            cs.setInt(3, idCategoria);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[3];

                    fila[0] = rs.getString("categoria");
                    fila[1] = rs.getInt("cantidad_ventas");
                    fila[2] = rs.getDouble("total_ventas");

                    lista.add(fila);
                }
            }

            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Error al generar reporte de ventas", e);
        }
    }

    @Override
    public List<Object[]> reporteFidelizacion() {

        List<Object[]> lista = new ArrayList<>();
        String sql = "{CALL USP_REPORTE_FIDELIZACION()}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[3];

                fila[0] = rs.getString("usuario");
                fila[1] = rs.getInt("puntos_acumulados");
                fila[2] = rs.getInt("puntos_canjeados");

                lista.add(fila);
            }

            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Error al generar reporte de fidelización", e);
        }
    }

    @Override
    public List<Object[]> reporteOcupacionEventos() {

        List<Object[]> lista = new ArrayList<>();
        String sql = "{CALL USP_REPORTE_OCUPACION_EVENTOS()}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[4];

                fila[0] = rs.getString("evento");
                fila[1] = rs.getInt("capacidad");
                fila[2] = rs.getInt("entradas_vendidas");
                fila[3] = rs.getDouble("porcentaje_ocupacion");

                lista.add(fila);
            }

            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Error al generar reporte de ocupación", e);
        }
    }
}