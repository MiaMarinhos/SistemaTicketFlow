using System;
using System.Collections.Generic;
using System.Text;

namespace TicketFlowModel.Ubicacion
{
    public class Region
    {
        public int idRegion {  get; set; }
        public string nombre { get; set; }

        public Region() { }

        public Region(int idRegion, string nombre)
        {
            this.idRegion = idRegion;
            this.nombre = nombre;
        }
    }
}
