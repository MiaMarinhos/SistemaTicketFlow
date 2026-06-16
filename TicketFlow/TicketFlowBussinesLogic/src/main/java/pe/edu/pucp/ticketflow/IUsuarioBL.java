package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.solicitud.model.Solicitud;
import pe.edu.pucp.ticketflow.usuario.model.Anfitrion;
import pe.edu.pucp.ticketflow.usuario.model.Cliente;
import pe.edu.pucp.ticketflow.usuario.model.Usuario;

import java.util.List;

public interface IUsuarioBL {
    public Usuario registrarUsuario(Usuario usuario) throws BusinessLogicException;

    public Cliente registrarCliente(Cliente cliente) throws BusinessLogicException;

    public Anfitrion registrarAnfitrion(Anfitrion anfitrion) throws BusinessLogicException;


    void editarPerfil(Usuario usuario) throws BusinessLogicException;
    Usuario verPerfil(Integer idUsuario) throws BusinessLogicException;
    List<Evento> buscarEventos(String nombreEvento) throws BusinessLogicException;
    void enviarSolicitud(Solicitud solicitud) throws BusinessLogicException;

    //void iniciarSesion();
    public Usuario iniciarSesion(String correo, String rol) throws BusinessLogicException;


}
