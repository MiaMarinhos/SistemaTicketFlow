package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.*;
import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;

import java.util.ArrayList;
import java.util.List;

public class EventoBLImpl implements IEventoBL {
    private IEventoDAO eventoDAO = new EventoDAOImpl();
    private IDistritoDAO distritoDAO = new DistritoDAOImpl();
    private IAnfitrionDAO anfitrionDAO = new AnfitrionDAOImpl();
    private ICategoriaEventoDAO categoriaEventoDAO = new CategoriaEventoDAOImpl();
    private IEstadoPublicacionDAO estadoPublicacionDAO = new EstadoPublicacionDAOImpl();
    private IEstadoEventoDAO estadoEventoDAO = new EstadoEventoDAOImpl();

    @Override
    public Evento crearEvento(Evento evento) throws BusinessLogicException {
        try {
            eventoDAO.create(evento);
            return mostrarEvento(evento.getIdEvento());
        }
        catch (Exception ex){
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException)ex;
            } else {
                throw new BusinessLogicException(ex);
            }
        }
    }

    @Override
    public Evento mostrarEvento(Integer id) throws BusinessLogicException {
        try {
            Evento evento = new Evento();
            evento = eventoDAO.read(id);
            evento.setDistrito(distritoDAO.read(evento.getFK_idDistrito()));
            //evento.setAnfitrion(anfitrionDAO.read(evento.getIdAnfitrion()));
            evento.setCategoria(categoriaEventoDAO.read(evento.getFK_idCategoria_evento()));
            evento.setEstadoPublicacion(estadoPublicacionDAO.read(evento.getFK_idEstadoPublicacion()));
            evento.setEstadoEvento(estadoEventoDAO.read(evento.getFK_idEstadoEvento()));
            return evento;
        }
        catch (Exception ex){
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException)ex;
            } else {
                throw new BusinessLogicException(ex);
            }
        }
    }

    @Override
    public Evento editarEvento(Evento evento, Integer id) throws BusinessLogicException {
        try {
            eventoDAO.update(evento, id);
            return mostrarEvento(id);
        }
        catch (Exception ex){
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException)ex;
            } else {
                throw new BusinessLogicException(ex);
            }
        }
    }

    @Override
    public Evento ocultarEvento(Integer id) throws BusinessLogicException {
        try {
            Evento evento=this.verDetalleEvento(id);
            evento.setActivo(false);
            return editarEvento(evento, id);
        }
        catch (Exception ex){
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException)ex;
            } else {
                throw new BusinessLogicException(ex);
            }
        }
    }

    @Override
    public List<Evento> verTodosLosEventos() throws BusinessLogicException {
        try {
            return eventoDAO.listAllActivosAprobados();
        }
        catch (Exception ex){
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException)ex;
            } else {
                throw new BusinessLogicException(ex);
            }
        }
    }
    @Override
    public List<Evento> verTodosLosEventosPorCategoria(String categoria) throws BusinessLogicException{
        try {
            return eventoDAO.filtrarPorTipo(categoria);
        }
        catch (Exception ex){
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException)ex;
            } else {
                throw new BusinessLogicException(ex);
            }
        }
    }

    @Override
    public Evento verDetalleEvento(Integer id) throws BusinessLogicException {
        try {
            Evento evento = new Evento();
            evento = eventoDAO.read(id);
            return evento;
        }
        catch (Exception ex){
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException)ex;
            } else {
                throw new BusinessLogicException(ex);
            }
        }
    }
    @Override
    public List<Evento> buscarEventoPorNombre(String nombre) throws BusinessLogicException{
        try {
            return eventoDAO.buscarPorTitulo(nombre);
        }
        catch (Exception ex){
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException)ex;
            } else {
                throw new BusinessLogicException(ex);
            }
        }
    }
    @Override
    public List<Evento> ListarEventosProximos()throws BusinessLogicException{
        try {
            return eventoDAO.ListarEventosProximos();
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
