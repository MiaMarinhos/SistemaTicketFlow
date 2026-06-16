package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.banco.model.Banco;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.ubicacion.model.Distrito;

import java.util.List;

public interface IUbicacionBL {
    List<Distrito> listarDistritos() throws BusinessLogicException;
}
