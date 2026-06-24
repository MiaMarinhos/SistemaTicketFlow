using Newtonsoft.Json;
using System.ComponentModel.DataAnnotations;

namespace TicketFlowWeb.Models
{
    public class SolicitudViewModel
    {
        public int IdSolicitudes { get; set; }
        public int IdAdministrador { get; set; }

        [JsonProperty("telefonoContacto")]
        public string TelefonoContacto { get; set; } = string.Empty;

        [JsonProperty("correoContacto")]
        public string CorreoContacto { get; set; } = string.Empty;

        public int IdCliente { get; set; }
        public int IdEstadoSolicitud { get; set; }

        public AdministratorViewModel? Administrador { get; set; }
        public ClienteViewModel? Cliente { get; set; }
        public EstadoSolicitudViewModel? EstadoSolicitud { get; set; }

        [JsonProperty("motivo")]
        public string Motivo { get; set; } = string.Empty;
    }
}
