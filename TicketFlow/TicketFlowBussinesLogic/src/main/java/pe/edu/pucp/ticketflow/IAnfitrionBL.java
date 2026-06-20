package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.compra.model.Compra;
import pe.edu.pucp.ticketflow.pago.model.Pago;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.usuario.model.Anfitrion;

import java.util.List;

public interface IAnfitrionBL extends IUsuarioBL{
    Anfitrion registrarAnfitrion(Anfitrion anfitrion) throws BusinessLogicException;

    Anfitrion buscarAnfitrionPorId(Integer idAnfitrion) throws BusinessLogicException;

    Anfitrion actualizarAnfitrion(Anfitrion anfitrion, Integer idAnfitrion) throws BusinessLogicException;

    void eliminarAnfitrion(Integer idAnfitrion) throws BusinessLogicException;

    List<Anfitrion> listarAnfitriones() throws BusinessLogicException;

    Evento crearEvento(Evento evento) throws BusinessLogicException;

    Evento mostrarEvento(Integer idEvento) throws BusinessLogicException;

    Evento actualizarEvento(Evento evento, Integer idEvento) throws BusinessLogicException;

    void eliminarEvento(Integer idEvento) throws BusinessLogicException;

    List<Evento> verTodosLosEventos() throws BusinessLogicException;

    void editarPerfilAnfitrion(Anfitrion anfitrion) throws BusinessLogicException;

    List<Compra> verComprasDeSusEventos(Integer idAnfitrion) throws BusinessLogicException;

    List<Pago> verPagosDeSusEventos(Integer idAnfitrion) throws BusinessLogicException ;

}
