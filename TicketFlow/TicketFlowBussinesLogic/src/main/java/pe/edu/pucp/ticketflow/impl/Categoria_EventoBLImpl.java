package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.ICategoria_EventoBL;
import pe.edu.pucp.ticketflow.ICategoriaEventoDAO;
import pe.edu.pucp.ticketflow.evento.model.categoria_evento;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;

import java.util.List;

public class Categoria_EventoBLImpl implements ICategoria_EventoBL {

    // Instanciamos el DAO que se conectará a la BD
    private final ICategoriaEventoDAO categoriaDAO;

    public Categoria_EventoBLImpl() {
        this.categoriaDAO = new CategoriaEventoDAOImpl();
    }

    @Override
    public List<categoria_evento> listarCategorias() throws BusinessLogicException {
        try {
            return categoriaDAO.listAll();
        } catch (Exception ex) {
            throw new BusinessLogicException("Error al obtener la lista de categorías: " + ex.getMessage());
        }
    }
}