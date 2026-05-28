package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.base.IBaseDAO;
import pe.edu.pucp.ticketflow.ubicacion.model.Distrito;

public interface IDistritoDAO extends IBaseDAO<Distrito, Integer> {
    Distrito buscarPorNombre(String nombre);
}
