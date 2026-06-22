package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;

import java.util.List;

public interface IEventoBL {
    Evento crearEvento(Evento evento) throws BusinessLogicException;
    Evento mostrarEvento(Integer id) throws BusinessLogicException;
    Evento editarEvento(Evento evento, Integer id) throws BusinessLogicException;
    Evento ocultarEvento(Evento evento, Integer id) throws BusinessLogicException;
    List<Evento> verTodosLosEventos() throws BusinessLogicException;
    List<Evento> verTodosLosEventosPorCategoria(String categoria) throws BusinessLogicException;
    Evento verDetalleEvento(Integer id) throws BusinessLogicException;
}
