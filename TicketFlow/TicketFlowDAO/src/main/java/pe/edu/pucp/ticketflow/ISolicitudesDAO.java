package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.base.IBaseDAO;
import pe.edu.pucp.ticketflow.solicitud.model.Solicitud;

import java.util.List;

public interface ISolicitudesDAO extends IBaseDAO<Solicitud,Integer> {
    List<Solicitud> buscarPorNombre(String nombre);
    List<Solicitud> filtrarPorEstado(Integer idEstado);
    Solicitud aprobarSolicitud(Integer idSolicitud);
    Solicitud rechazarSolicitud(Integer idSolicitud);
}
