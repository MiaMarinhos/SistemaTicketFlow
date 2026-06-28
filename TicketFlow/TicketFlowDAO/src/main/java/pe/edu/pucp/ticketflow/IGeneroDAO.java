package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.base.IBaseDAO;
import pe.edu.pucp.ticketflow.usuario.model.Genero;

import java.util.List;

public interface IGeneroDAO extends IBaseDAO<Genero, Integer> {
    public List<Genero> listAll();
}
