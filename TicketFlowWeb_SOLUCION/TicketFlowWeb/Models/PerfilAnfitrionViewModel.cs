using System;

namespace TicketFlowWeb.Models
{
    public class PerfilAnfitrionViewModel
    {
        public string Codigo { get; set; } = string.Empty;
        public string Nombre { get; set; } = string.Empty;
        public string ApellidoPaterno { get; set; } = string.Empty;
        public string ApellidoMaterno { get; set; } = string.Empty;
        public string Dni { get; set; } = string.Empty;
        public string CorreoElectronico { get; set; } = string.Empty;
        public string Telefono { get; set; } = string.Empty;
        public string RazonSocial { get; set; } = string.Empty;
        public string Ruc { get; set; } = string.Empty;
        public string CuentaBancaria { get; set; } = string.Empty;
    }
}