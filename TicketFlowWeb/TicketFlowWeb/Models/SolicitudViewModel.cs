using System.ComponentModel.DataAnnotations;

namespace TicketFlowWeb.Models
{
    public class SolicitudViewModel
    {
        public string RazonSocial { get; set; } = string.Empty;
        public string Ruc { get; set; } = string.Empty;
        public string Telefono { get; set; } = string.Empty;
        public string Correo { get; set; } = string.Empty;
        public string Motivo { get; set; } = string.Empty;
    }
}
