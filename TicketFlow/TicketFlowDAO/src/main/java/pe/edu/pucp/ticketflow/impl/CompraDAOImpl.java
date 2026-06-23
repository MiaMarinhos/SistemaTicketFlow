package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.*;
import pe.edu.pucp.ticketflow.compra.model.Compra;
import pe.edu.pucp.ticketflow.compra.model.EstadoCompra;
import pe.edu.pucp.ticketflow.dao.manager.DBManager;
import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.puntosBonus.model.PuntosBonus;
import pe.edu.pucp.ticketflow.usuario.model.Cliente;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class CompraDAOImpl implements ICompraDAO{
    @Override
    public Compra create(Compra compra){
        String sql = "{CALL SP_REGISTRAR_COMPRA(?,?,?,?,?,?,?,?,?)}";

        try (Connection connection = DBManager.getInstance().getConnection();
             CallableStatement cs = connection.prepareCall(sql)) {

            cs.setInt(1, compra.getIdCompra());
            cs.setInt(2, compra.getEntradasCompradas());
            cs.setString(3, compra.getMetodoPago());
            cs.setDouble(4, compra.getMontoParcial());
            cs.setDouble(5, compra.getMontoTotal());
            cs.setInt(6, compra.getIdEstado());
            cs.setInt(7, compra.getIdpuntoBonus());
            cs.setInt(8, compra.getIdCliente());
            cs.setInt(9, compra.getIdEvento());

            cs.executeUpdate();
            return compra;

        } catch (SQLException e) {
            throw new RuntimeException("Error en DAO al registrar compra: " + e.getMessage(), e);
        }
    }
    @Override
    public Compra read(Integer id){
        String sql = "{CALL sp_read_compras(?)}";

        try(Connection con = DBManager.getInstance().getConnection();
            CallableStatement cs = con.prepareCall(sql)){

            cs.setInt(1, id);
            try(ResultSet rs = cs.executeQuery()){
                if(rs.next()){
                    Compra t = new Compra();
                    mapear(rs, t);
                    return t;
                }
            }
            return null;
        }
        catch (SQLException e){
            throw new RuntimeException("Error al leer Compra", e);
        }
    }
    @Override
    public Compra update(Compra t, Integer id) {
        String sql = "{CALL sp_update_compras(?,?,?,?,?,?,?,?,?,?,?)}";

        try(Connection con = DBManager.getInstance().getConnection();
            CallableStatement cs = con.prepareCall(sql)){

            cs.setInt(1, id);
            cs.setInt(2,t.getEntradasCompradas());
            cs.setDate(3,Date.valueOf(t.getFechaCompra()));
            cs.setString(4,t.getMetodoPago());
            cs.setTime(5,Time.valueOf(t.getHoraCompra()));
            cs.setDouble(6,t.getMontoParcial());
            cs.setDouble(7,t.getMontoTotal());
            cs.setInt(8,t.getPuntosBonus().getIdPuntosBonus());
            cs.setInt(9,t.getCliente().getIdUsuario());
            cs.setInt(10,t.getEvento().getIdEvento());
            cs.setInt(11,t.getEstado().getIdEstadoCompra());

            cs.execute();
            return t;
        }
        catch (SQLException e){
            throw new RuntimeException("Error al actualizar Compra", e);
        }
    }
    @Override
    public void delete(Integer id){
        String sql = "{CALL sp_delete_compras(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException("Error en eliminar Compra", e);
        }
    }
    @Override
    public List<Compra> listAll(){
        List<Compra> lista = new ArrayList<>();
        String sql = "{CALL sp_listAll_compras()}";
        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while(rs.next()){
                Compra t = new Compra();
                mapear(rs, t);
                lista.add(t);
            }
            return lista;
        }
        catch (SQLException e) {
            throw new RuntimeException("Error en listar Compra", e);
        }
    }

    @Override
    public List<Compra> buscarPorUsuario(String nombre) {

        List<Compra> lista = new ArrayList<>();

        String sql = "{CALL sp_buscar_compra_usuario(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, nombre);

            try (ResultSet rs = cs.executeQuery()) {

                while(rs.next()) {

                    Compra t = new Compra();
                    mapear(rs, t);
                    lista.add(t);
                }

                return lista;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar compras", e);
        }
    }

    @Override
    public List<Compra> filtrarPorEstado(Integer idEstado) {

        List<Compra> lista = new ArrayList<>();

        String sql = "{CALL sp_filtrar_compras_estado(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idEstado);

            try (ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {
                    Compra t = new Compra();
                    mapear(rs, t);
                    lista.add(t);
                }

                return lista;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al filtrar compras por estado", e);
        }
    }

    private void mapear(ResultSet rs, Compra t) throws SQLException{
        t.setIdCompra(rs.getInt("idCompras"));
        t.setEntradasCompradas(rs.getInt("entradas_compradas"));
        t.setFechaCompra(rs.getDate("fecha_compra").toLocalDate());
        t.setMetodoPago(rs.getString("metodo_pago"));
        t.setHoraCompra(rs.getTime("hora_compra").toLocalTime());
        t.setMontoParcial(rs.getDouble("monto_parcial"));
        t.setMontoTotal(rs.getDouble("monto_total"));
        t.setIdpuntoBonus(rs.getInt("idPuntos_bonus"));
        t.setIdCliente(rs.getInt("idCliente"));
        t.setIdEvento(rs.getInt("idEvento"));
        t.setIdEstado(rs.getInt("idEstado"));
    }

    public List<Compra> listarComprasPorAnfitrion(Integer idAnfitrion) {
        List<Compra> lista = new ArrayList<>();
        // El procedure debe hacer un JOIN entre tabla_compras y tabla_eventos
        // donde el id_anfitrion coincida con el parámetro enviado.
        String sql = "{CALL sp_listar_compras_por_anfitrion(?)}";

        try (Connection con = DBManager.getInstance().getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            // Seteamos el ID del anfitrión que recibimos desde el BL
            cs.setInt(1, idAnfitrion);

            try (ResultSet rs = cs.executeQuery()) {
                while(rs.next()){
                    Compra t = new Compra();
                    // Reutilizamos tu método mapear para llenar el objeto
                    mapear(rs, t);
                    lista.add(t);
                }
            }
            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Error en listar Compras por Anfitrión", e);
        }
    }
}
