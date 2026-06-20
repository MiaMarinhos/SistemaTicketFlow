package pe.edu.pucp.ticketflow;

import pe.edu.pucp.ticketflow.base.IBaseDAO;
import pe.edu.pucp.ticketflow.usuario.model.TipoUsuario;
import pe.edu.pucp.ticketflow.usuario.model.Usuario;

public interface ITipoUsuarioDAO extends IBaseDAO<TipoUsuario, Integer > {
    public TipoUsuario buscarTipoUsuarioPorTipo(String tipo);
}
