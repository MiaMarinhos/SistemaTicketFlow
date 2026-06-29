package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.evento.model.categoria_evento; // Asegúrate de importar tu modelo Categoria
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;

import java.util.List;

public interface ICategoria_EventoBL {
    List<categoria_evento> listarCategorias() throws BusinessLogicException;
}
