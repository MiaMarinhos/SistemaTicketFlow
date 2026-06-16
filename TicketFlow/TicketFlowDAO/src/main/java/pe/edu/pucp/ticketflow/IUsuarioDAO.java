package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.base.IBaseDAO;
import pe.edu.pucp.ticketflow.usuario.model.Usuario;

import java.util.List;

public interface IUsuarioDAO extends IBaseDAO <Usuario, Integer >{
    public List<Usuario> buscarPorNombre(String nombre);
    List<Usuario> filtrarPorTipo(Integer idTipoUsuario);
    List<Usuario> filtrarPorEstado(Integer idEstado);
    Usuario bloquearUsuario(Integer idUsuario);
    Usuario desbloquearUsuario(Integer idUsuario);
    public Usuario ObtenerUsuarioPorRol(Integer idUsuario, String rol);
    public int ComprobarTipoUsuario(String correo, String rol);
}
