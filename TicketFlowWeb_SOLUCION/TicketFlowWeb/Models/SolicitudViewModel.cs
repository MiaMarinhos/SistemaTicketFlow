using System.ComponentModel.DataAnnotations;

namespace TicketFlowWeb.Models
{
    public class SolicitudViewModel
    {
        public int IdSolicitudes { get; set; }

        public int IdAdministrador { get; set; }
        public string TelefonoContacto { get; set; } = string.Empty;

        public string CorreoContacto { get; set; } = string.Empty;
        public int IdCliente { get; set; }

        public int IdEstadoSolicitud { get; set; }

        public AdministratorViewModel? Administrador { get; set; }

        public ClienteViewModel? Cliente { get; set; }

        public EstadoSolicitudViewModel? EstadoSolicitud { get; set; }
        public string RazonSocial { get; set; } = string.Empty;
        public string Ruc { get; set; } = string.Empty;
        public string Telefono { get; set; } = string.Empty;
        public string Correo { get; set; } = string.Empty;
        public string Motivo { get; set; } = string.Empty;

    }
}
