using System.IO.IsolatedStorage;
using TicketFlowDAO;
using TicketFlowModel.Usuario;

namespace TicketFlowBL
{
    public class UsuarioBL : IUsuarioBL
    {
        private readonly IUsuarioDAO usuarioDAO;

        public UsuarioBL()
        {
            usuarioDAO = new UsuarioDAOImpl();
        }
        public Usuario ValidarAcceso(string correo, string password, string rol)
        {
            int exitencia = usuarioDAO.ComprobarTipoUsuario(correo, rol);
            if (exitencia != -1)
            {
                Usuario usuario = usuarioDAO.ObtenerPorUsuario(exitencia, rol);
                if (usuario == null)
                    return null;

                /*bool passwordCorrecto = BCrypt.Net.BCrypt.Verify(
                password,
                usuario.PasswordHash
                );*/

                bool passwordCorrecto = password == usuario.contrasena;

                if (!passwordCorrecto)
                    return null;

                return usuario;
            }
            else return null;
            

            
        }
    }
}
