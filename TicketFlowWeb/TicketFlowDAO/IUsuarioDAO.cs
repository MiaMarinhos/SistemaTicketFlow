using System;
using System.Collections.Generic;
using System.Text;
using TicketFlowModel.Usuario;

namespace TicketFlowDAO
{
    public interface IUsuarioDAO
    {
        public Usuario ObtenerPorUsuario(int id, string rol);
        public int ComprobarTipoUsuario(string correo, string rol);
    }
}
