using System;
using System.Collections.Generic;
using System.Text;

namespace TicketFlowModel.Usuario
{
    public class Genero
    {
        public int idGenero {  get; set; }
        public string nombre { get; set; }

        public Genero() { }

        public Genero(int idGenero, string nombre)
        {
            this.idGenero = idGenero;
            this.nombre = nombre;
        }
    }
}
