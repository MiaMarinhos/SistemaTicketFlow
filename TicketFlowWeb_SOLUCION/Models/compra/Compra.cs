using System;
using System.Collections.Generic;
using System.Text;

namespace Models.compra
{
    public class Compra
    {
        public int idCompra { get; set; }
        public int entradasCompradas { get; set; }
        //public LocalDate fechaCompra;
        //public LocalTime horaCompra;
        public String metodoPago { get; set; }
        public double montoParcial { get; set; }
        public double montoTotal { get; set; }

        public int idEstado { get; set; }
        public int idpuntoBonus { get; set; }
        public int idCliente { get; set; }
        public int idEvento { get; set; }

        //public EstadoCompra estado;
        //public PuntosBonus puntosBonus;
        //public Cliente cliente;
        //public Evento evento;

        public String fechaCompraS { get; set; }
        public String horaCompraS { get; set; }

        public Compra() { }
        public Compra(int idCompra, int entradasCompradas, String metodoPago, double montoParcial, double montoTotal, int idEstado, int idpuntoBonus, int idCliente, int idEvento)
        {
            this.idCompra = idCompra;
            this.entradasCompradas = entradasCompradas;
            this.metodoPago = metodoPago;
            this.montoParcial = montoParcial;
            this.montoTotal = montoTotal;
            this.idEstado = idEstado;
            this.idpuntoBonus = idpuntoBonus;
            this.idCliente = idCliente;
            this.idEvento = idEvento;
        }
    }
}
