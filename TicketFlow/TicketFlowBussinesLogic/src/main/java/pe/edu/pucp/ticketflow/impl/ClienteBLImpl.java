package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.IClienteBL;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.usuario.model.Cliente;

import java.util.List;

public class ClienteBLImpl extends UsuarioBLImpl implements IClienteBL {

    private final ClienteDAOImpl clienteDAO;

    public ClienteBLImpl() {
        this.clienteDAO = new ClienteDAOImpl();
    }


    @Override
    public Cliente buscarClientePorId(Integer idCliente) throws BusinessLogicException {
        try {
            if (idCliente == null || idCliente <= 0) {
                throw new BusinessLogicException("El ID del cliente debe ser válido.");
            }

            Cliente cliente = clienteDAO.read(idCliente);

            if (cliente == null) {
                throw new BusinessLogicException("No se encontró un cliente con ID: " + idCliente);
            }

            return cliente;

        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Error al buscar cliente: " + e.getMessage());
        }
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente, Integer idCliente) throws BusinessLogicException {
        try {
            if (cliente == null) {
                throw new BusinessLogicException("El cliente no puede ser nulo.");
            }

            if (idCliente == null || idCliente <= 0) {
                throw new BusinessLogicException("El ID del cliente debe ser válido.");
            }

            if (cliente.getPuntosBonus() < 0) {
                throw new BusinessLogicException("Los puntos bonus no pueden ser negativos.");
            }

            return clienteDAO.update(cliente, idCliente);

        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Error al actualizar cliente: " + e.getMessage());
        }
    }

    @Override
    public void eliminarCliente(Integer idCliente) throws BusinessLogicException {
        try {
            if (idCliente == null || idCliente <= 0) {
                throw new BusinessLogicException("El ID del cliente debe ser válido.");
            }

            clienteDAO.delete(idCliente);

        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Error al eliminar cliente: " + e.getMessage());
        }
    }

    @Override
    public List<Cliente> listarClientes() throws BusinessLogicException {
        try {
            return clienteDAO.listAll();

        } catch (Exception e) {
            throw new BusinessLogicException("Error al listar clientes: " + e.getMessage());
        }
    }

    @Override
    public void comprarEntradas() throws BusinessLogicException {
        System.out.println("Cliente está generando una compra de entradas.");
    }

    @Override
    public void pagarEntrada() throws BusinessLogicException {
        System.out.println("Cliente está realizando el pago de la entrada.");
    }

    @Override
    public void descargarEntradas() throws BusinessLogicException {
        System.out.println("Cliente está descargando sus entradas digitales.");
    }

    @Override
    public void verPerfil() throws BusinessLogicException {
        System.out.println("Cliente está visualizando su perfil.");
    }

    @Override
    public void editarPerfil() throws BusinessLogicException {
        System.out.println("Cliente está editando su perfil.");
    }

    @Override
    public void solicitarSerAnfitrion() throws BusinessLogicException {
        System.out.println("Cliente está solicitando convertirse en anfitrión.");
    }

}
