using System;
using System.Collections.Generic;
using System.Text;
using TicketFlowModel.Usuario;

namespace TicketFlowBL
{
    public interface IUsuarioBL
    {
        Usuario ValidarAcceso(string correo, string password, string rol);
    }
}
