package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.usuario.model.Cliente;

import java.util.List;

public interface IClienteBL extends IUsuarioBL{
    Cliente registrarCliente(Cliente cliente) throws BusinessLogicException;

    Cliente buscarClientePorId(Integer idCliente) throws BusinessLogicException;

    Cliente actualizarCliente(Cliente cliente, Integer idCliente) throws BusinessLogicException;

    void eliminarCliente(Integer idCliente) throws BusinessLogicException;

    List<Cliente> listarClientes() throws BusinessLogicException;

    void comprarEntradas() throws BusinessLogicException;

    void pagarEntrada() throws BusinessLogicException;

    void descargarEntradas() throws BusinessLogicException;

    void verPerfil() throws BusinessLogicException;

    void editarPerfil() throws BusinessLogicException;

    void solicitarSerAnfitrion() throws BusinessLogicException;
}
