package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IBancoDAO;
import pe.edu.pucp.ticketflow.IDistritoDAO;
import pe.edu.pucp.ticketflow.IUbicacionBL;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.ubicacion.model.Distrito;

import java.util.List;

public class UbicacionBLImpl implements IUbicacionBL {
    private final IDistritoDAO distritoDAO;

    public UbicacionBLImpl() {
        this.distritoDAO = new DistritoDAOImpl();
    }


    @Override
    public List<Distrito> listarDistritos() throws BusinessLogicException {
        try {
            return distritoDAO.listAll();
        } catch (Exception ex) {
            throw new BusinessLogicException(ex);
        }
    }
}
