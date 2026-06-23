namespace TicketFlowWeb.Models
{
    public class CompraViewModel
    {
        // Datos principales de la compra
        public int idCompra { get; set; }
        public int entradasCompradas { get; set; }
        public string? fechaCompra { get; set; } // Lo leemos como string para evitar choques con Java
        public string? horaCompra { get; set; }  // Lo leemos como string
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
        public object? puntosBonus { get; set; }
        public object? cliente { get; set; }
        public object? evento { get; set; }
    }

    public class EstadoCompra
    {
        public int idEstadoCompra { get; set; }
        public string? estado { get; set; }
    }
}
