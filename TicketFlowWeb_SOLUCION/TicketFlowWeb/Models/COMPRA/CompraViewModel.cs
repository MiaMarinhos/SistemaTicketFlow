namespace TicketFlowWeb.Models.COMPRA
{
    public class CompraViewModel
    {
        // Datos principales de la compra
        public int idCompra { get; set; }
        public int entradasCompradas { get; set; }
        public DateOnly? fechaCompra { get; set; }
        public TimeOnly? horaCompra { get; set; }
        public string? fechaCompraS { get; set; } = string.Empty;
        public string? horaCompraS { get; set; } = string.Empty ;
        public string? metodoPago { get; set; }
        public double montoParcial { get; set; }
        public double montoTotal { get; set; }

        // IDs requeridos por las relaciones de la BD
        public int idEstado { get; set; }
        public int idpuntoBonus { get; set; }
        public int idCliente { get; set; }
        public int idEvento { get; set; }

        // Objetos anidados opcionales (por si Java los llena en el futuro)
        public EstadoCompra? estado { get; set; }
        public PuntosBonusViewModel? puntosBonus { get; set; }
        public ClienteViewModel? cliente { get; set; }
        public EventoViewModel? evento { get; set; }
    }

    public class EstadoCompra
    {
        public int idEstadoCompra { get; set; }
        public string? estado { get; set; }
    }

    public class PuntosBonusViewModel
    {
        public int idPuntosBonus { get; set; }
        public int puntosCanheables { get; set; }
        public int descuento { get; set; }
    }
}
