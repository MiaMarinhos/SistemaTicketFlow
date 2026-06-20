package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.banco.model.Banco;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;

import java.util.List;

public interface IBancoBL {
    Banco registrarBanco(Banco banco) throws BusinessLogicException;
    Banco buscarBancoPorId(Integer id) throws BusinessLogicException;
    Banco actualizarBanco(Banco banco, Integer id) throws BusinessLogicException;
    void eliminarBanco(Integer id) throws BusinessLogicException;
    List<Banco> listarBancos() throws BusinessLogicException;
}
