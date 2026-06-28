package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.compra.model.Compra;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;

import java.util.List;

public interface IComprasBL{

    Compra registrarCompra(Compra compra) throws BusinessLogicException;
    List<Compra> listarComprasPorCliente(Integer idCliente) throws BusinessLogicException;
    List<Compra>ListarComprasDeEvento(int idEvento)throws BusinessLogicException;
    void marcarCompraComoEnviado(int idCompra)throws BusinessLogicException;
    public void marcarCompraComoEnviado2(int idCompra)throws BusinessLogicException;
    void validarIngresoCliente(int idCompra)throws BusinessLogicException;
}
