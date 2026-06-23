using System;

namespace TicketFlowWeb.Models
{
    public class PagoViewModel
    {
        public int idPago { get; set; }

        public DateTime? fechaPago { get; set; }
        public DateTime? fechaLimitePago { get; set; }

        public double totalAPagar { get; set; }
        public string comprobante { get; set; } = string.Empty;

        public int idEstado { get; set; }
        public int idEvento { get; set; }

        public EstadoPagoViewModel? estado { get; set; }
        public EventoViewModel? evento { get; set; }
    }
}