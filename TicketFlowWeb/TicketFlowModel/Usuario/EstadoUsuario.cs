using System;
using System.Collections.Generic;
using System.Text;

namespace TicketFlowModel.Usuario
{
    public class EstadoUsuario
    {
        public int idEstadoUsuario {  get; set; }
        public string estado {  get; set; }

        public EstadoUsuario() { }

        public EstadoUsuario(int idEstadoUsuario, string estado)    
        {
            this.idEstadoUsuario = idEstadoUsuario;
            this.estado = estado;
        }
    }
}
