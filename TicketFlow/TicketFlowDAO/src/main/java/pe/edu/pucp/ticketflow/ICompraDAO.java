package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.base.IBaseDAO;
import pe.edu.pucp.ticketflow.compra.model.Compra;

import java.util.List;

public interface ICompraDAO extends IBaseDAO<Compra, Integer> {
    List<Compra> listarComprasPorAnfitrion(Integer idAnfitrion);
    List<Compra> buscarPorUsuario(String nombre);
    List<Compra> filtrarPorEstado(Integer idEstado);
    List<Compra> listarComprasPorCliente(Integer idCliente);

}
