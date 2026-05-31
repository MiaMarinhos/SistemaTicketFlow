using System;
using System.Collections.Generic;
using System.Text;

namespace TicketFlowModel.Ubicacion
{
    public class Distrito
    {
        public int idDistrito {  get; set; }
        public string nombre {  get; set; }
        public Region region { get; set; }

        public Distrito() { }

        public Distrito(int idDistrito, string nombre, Region region)
        {
            this.idDistrito = idDistrito;
            this.nombre = nombre;
            this.region = region;
        }



    }
}
