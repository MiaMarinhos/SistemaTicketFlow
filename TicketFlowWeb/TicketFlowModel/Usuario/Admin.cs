using System;
using System.Collections.Generic;
using System.Text;

namespace TicketFlowModel.Usuario
{
    public class Admin : Usuario
    {
        public double monto_total { get; set; }
        public double monto_neto { get; set; }
        public double monto_disponible { get; set; }

        public Admin() : base() { }

        public Admin(double monto_total, double monto_neto,
            double monto_disponible)
        {
            this.monto_total = monto_total;
            this.monto_neto = monto_neto;
            this.monto_disponible = monto_disponible;
        }
    }
}
