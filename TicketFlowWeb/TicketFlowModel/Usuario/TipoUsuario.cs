using System;
using System.Collections.Generic;
using System.Text;

namespace TicketFlowModel.Usuario
{
    public class TipoUsuario
    {
        public int idTipoUsuario {  get; set; }
        public string tipoUsuario { get; set; }

        public TipoUsuario() { }

        public TipoUsuario(int idTipoUsuario, string tipoUsuario)
        {
            this.idTipoUsuario = idTipoUsuario;
            this.tipoUsuario = tipoUsuario;
        }
    }
}
