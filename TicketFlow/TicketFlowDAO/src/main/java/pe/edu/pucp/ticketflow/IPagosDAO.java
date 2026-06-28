package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.base.IBaseDAO;
import pe.edu.pucp.ticketflow.pago.model.Pago;

import java.time.LocalDate;
import java.util.List;

public interface IPagosDAO extends IBaseDAO<Pago, Integer> {
    List<Pago> listarPagosPorAnfitrion(Integer idAnfitrion);
    List<Pago> buscarPorUsuario(String nombre);
    List<Pago> listAllByAdmin();
    List<Pago> filtrarPorEstado(Integer idEstado);
    List<Pago> filtrarPorFecha(LocalDate fecha);
}
