package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.base.IBaseDAO;
import pe.edu.pucp.ticketflow.evento.model.Evento;

import java.util.List;

public interface IEventoDAO extends IBaseDAO<Evento, Integer> {
    List<Evento> buscarPorTitulo(String titulo);
    List<Evento> filtrarPorEstado(Integer idEstadoEvento);
    List<Evento> filtrarPorTipo(String idTipoEvento);
    Evento aprobarEvento(Integer idEvento);
    Evento rechazarEvento(Integer idEvento);
    Evento eliminarEvento(Integer idEvento);
    List<Evento> ListarEventosProximos();
    List<Evento> listarEventosPorAnfitrion(Integer idAnfitrion);
    }
