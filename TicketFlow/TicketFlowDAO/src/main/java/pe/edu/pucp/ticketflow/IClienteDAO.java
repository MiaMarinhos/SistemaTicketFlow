package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.base.IBaseDAO;
import pe.edu.pucp.ticketflow.usuario.model.Cliente;

public interface IClienteDAO extends IBaseDAO <Cliente, Integer>{
    int readPuntos(Integer idCliente);


}
