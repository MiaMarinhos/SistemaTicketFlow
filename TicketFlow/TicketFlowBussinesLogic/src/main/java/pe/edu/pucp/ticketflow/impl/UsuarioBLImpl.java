package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.*;
import pe.edu.pucp.ticketflow.administrador.model.Administrador;
import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.solicitud.model.Solicitud;
import pe.edu.pucp.ticketflow.ubicacion.model.Distrito;
import pe.edu.pucp.ticketflow.usuario.model.*;

import java.util.Date;
import java.util.List;

public class UsuarioBLImpl implements IUsuarioBL {

    private IDistritoDAO distritoDAO = new DistritoDAOImpl();
    private IUsuarioDAO usuarioDAO = new UsuarioDAOImpl();
    private ITipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAOImpl();
    private IEstadoUsuarioDAO estadoUsuarioDAO = new EstadoUsuarioDAOImpl();
    private IClienteDAO clienteDAO = new ClienteDAOImpl();
    private IAnfitrionDAO anfitrionDAO = new AnfitrionDAOImpl();
    private IEventoDAO eventoDAO = new EventoDAOImpl();
    private ISolicitudesDAO solicitudesDAO = new SolicitudesDAOImpl();

    @Override
    public Usuario registrarUsuario(Usuario usuario) throws BusinessLogicException{
        try{

//            Distrito distrito = distritoDAO.read(usuario.getIdDistrito());
//            usuario.setDistrito(distrito);

            String nombreTipo = "";
            if (usuario instanceof Cliente) {
                nombreTipo = "CLIENTE";
            } else if (usuario instanceof Anfitrion) {
                nombreTipo = "ANFITRION";
            }else if (usuario instanceof Administrador) {
                nombreTipo = "ADMIN";
            }

            TipoUsuario tipo = tipoUsuarioDAO.buscarTipoUsuarioPorTipo(nombreTipo);
            usuario.setTipo(tipo);

            /*
            *   1 : Activo
            *   2 : Eliminado
            * */

            EstadoUsuario estadoUsuario = estadoUsuarioDAO.read(1);
            usuario.setEstado(estadoUsuario);

            usuario.setFechaRegistro(java.sql.Date.valueOf(java.time.LocalDate.now()));

            return usuarioDAO.create(usuario);
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
    public String registrarCliente(Cliente cliente) throws BusinessLogicException {
        try {
            if (cliente == null) {
                throw new BusinessLogicException("El cliente no puede ser nulo.");
            }
            if (cliente.getPuntosBonus() < 0) {
                throw new BusinessLogicException("Los puntos bonus no pueden ser negativos.");
            }
            registrarUsuario(cliente);
            cliente.setPuntosBonus(50); //puntos gratis por ser new
            clienteDAO.create(cliente);

            return "Cliente registrado correctamente";


        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Error al registrar cliente: " + e.getMessage());
        }
    }

    @Override
    public Anfitrion registrarAnfitrion(Anfitrion anfitrion) throws BusinessLogicException {
        try {
            registrarUsuario(anfitrion);

            return anfitrionDAO.create(anfitrion);
        }
        catch (Exception ex) {
            if (ex instanceof BusinessLogicException) throw (BusinessLogicException) ex;
            else throw new BusinessLogicException(ex);
        }
    }

    @Override
    public Usuario verPerfil(Integer idUsuario) throws BusinessLogicException {
        try {
            Usuario usuario = usuarioDAO.read(idUsuario);

            if(usuario == null){
                throw new BusinessLogicException("Usuario no encontrado");
            }

            String tipo = usuario.getTipo().getTipoUsuario();
            switch (tipo){
                case "CLIENTE":
                    return clienteDAO.read(idUsuario);
                case "ANFITRION":
                    return anfitrionDAO.read(idUsuario);
                default:
                    throw new BusinessLogicException("Tipo de usuario inválido");
            }

        } catch(Exception ex){

            if(ex instanceof BusinessLogicException)
                throw (BusinessLogicException) ex;

            throw new BusinessLogicException(ex);
        }
    }

    @Override
    public void editarPerfil(Usuario usuario) throws BusinessLogicException {
        try {
            if (usuario == null || usuario.getIdUsuario() <= 0) {
                throw new BusinessLogicException("Datos de usuario inválidos para editar perfil.");
            }
            // Llama al DAO para actualizar los datos base del usuario
            usuarioDAO.update(usuario, usuario.getIdUsuario());

        } catch (Exception e) {
            throw new BusinessLogicException("Error al editar perfil base: " + e.getMessage());
        }
    }

    @Override
    public List<Evento> buscarEventos(String nombreEvento) throws BusinessLogicException {
        try {
            // Validamos que el usuario haya escrito algo para buscar
            if (nombreEvento == null || nombreEvento.trim().isEmpty()) {
                throw new BusinessLogicException("Debe ingresar un texto para buscar eventos.");
            }
            // Llamamos al DAO para que haga la búsqueda en la BD
            return eventoDAO.buscarPorTitulo(nombreEvento);

        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Error al buscar eventos: " + e.getMessage());
        }
    }

    @Override
    public void enviarSolicitud(Solicitud solicitud) throws BusinessLogicException {
        try {
            // Validar que el objeto exista
            if (solicitud == null) {
                throw new BusinessLogicException("La solicitud a enviar no puede estar vacía.");
            }
            if (solicitud.getMotivo() == null || solicitud.getMotivo().trim().isEmpty()) {
                throw new BusinessLogicException("El motivo de la solicitud es obligatorio.");
            }
            if (solicitud.getCorreoContacto() == null || solicitud.getCorreoContacto().trim().isEmpty()) {
                throw new BusinessLogicException("Debe proporcionar un correo de contacto.");
            }
            if (solicitud.getIdCliente() <= 0) {
                throw new BusinessLogicException("La solicitud debe estar asociada a un cliente válido.");
            }
            // Guardamos la solicitud en la base de datos llamando al procedure USP_INSERTAR_SOLICITUD
            solicitudesDAO.create(solicitud);

        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException("Error al enviar la solicitud: " + e.getMessage());
        }
    }

    @Override
    public Usuario iniciarSesion(String correo, String password, String rol) throws BusinessLogicException {
        try{
            //validad existencia del usuario.
            int existencia = usuarioDAO.ComprobarTipoUsuario(correo, rol);
            if(existencia != -1){
                Usuario u = usuarioDAO.ObtenerUsuarioPorRol(existencia, rol);
                if(u == null) return null;

                boolean passwordCorrecto = password.equals(u.getContrasena());
                if(!passwordCorrecto) return null;
                return u;
            }
        }catch (Exception ex){
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException)ex;
            } else {
                throw new BusinessLogicException(ex);
            }
        }
        return null;
    }

}
