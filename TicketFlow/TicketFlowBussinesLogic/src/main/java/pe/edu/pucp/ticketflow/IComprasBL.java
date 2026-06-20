package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.compra.model.Compra;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;

public interface IComprasBL{

    Compra registrarCompra(Compra compra) throws BusinessLogicException;

}
