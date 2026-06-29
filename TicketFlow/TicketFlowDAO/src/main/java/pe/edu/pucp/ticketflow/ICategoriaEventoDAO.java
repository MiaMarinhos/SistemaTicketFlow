package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.base.IBaseDAO;
import pe.edu.pucp.ticketflow.evento.model.categoria_evento;

import java.util.List;

public interface ICategoriaEventoDAO extends IBaseDAO<categoria_evento,Integer> {
    List<categoria_evento> listarCategorias() throws Exception;
}
