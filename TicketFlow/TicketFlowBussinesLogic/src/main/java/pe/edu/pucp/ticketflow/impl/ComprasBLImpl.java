package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.ICompraDAO;
import pe.edu.pucp.ticketflow.IComprasBL;
import pe.edu.pucp.ticketflow.compra.model.Compra;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;

public class ComprasBLImpl implements IComprasBL {

    private ICompraDAO compraDAO = new CompraDAOImpl();

    @Override
    public Compra registrarCompra(Compra compra) throws BusinessLogicException {
        try {
            compraDAO.create(compra);
        }
        catch (Exception ex){
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException)ex;
            } else {
                throw new BusinessLogicException(ex);
            }
        }
    }
}
