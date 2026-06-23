using System;
using System.Collections.Generic;
using System.Text;

namespace Models.compra
{
    public class EstadoCompra
    {
        public int idEstadoCompra { get; set; }
        public String estado { get; set; }

        public EstadoCompra() { }
        public EstadoCompra(int idEstadoCompra, string estado)
        {
            this.idEstadoCompra = idEstadoCompra;
            this.estado = estado;
        }
    }
}
