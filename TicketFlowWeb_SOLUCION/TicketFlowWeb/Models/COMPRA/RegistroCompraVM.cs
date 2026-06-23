namespace TicketFlowWeb.Models.COMPRA
{
    public class RegistroCompraVM
    {
        public int entradasCompradas { get; set; }
        public string? metodoPago { get; set; }
        public double montoParcial { get; set; }
        public double montoTotal { get; set; }
        public int idpuntoBonus { get; set; }
        public int idCliente { get; set; }
        public int idEvento { get; set; }
    }

}
