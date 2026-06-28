package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.base.IBaseDAO;
import pe.edu.pucp.ticketflow.impl.AnfitrionDAOImpl;
import pe.edu.pucp.ticketflow.usuario.model.Anfitrion;

public interface IAnfitrionDAO extends IBaseDAO<Anfitrion, Integer> {
    void actualizarDatosEmpresa(Integer idAnfitrion, String razonSocial, String ruc, String cuentaBancaria, Integer idBanco);
}
