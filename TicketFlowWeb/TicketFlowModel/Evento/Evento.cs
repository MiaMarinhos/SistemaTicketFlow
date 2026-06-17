using System;
using System.Collections.Generic;
using System.Text;


namespace TicketFlowModel.Evento
{
    public class Evento
    {
        public int idEvento { get; set; }

        public string titulo { get; set; }

        public string descripcion { get; set; }

        public double precio { get; set; }

        public DateTime fecha { get; set; }

        public TimeSpan hora_inicio { get; set; }

        public string img { get; set; }

        public CategoriaEvento categoria { get; set; }
    }
}
