namespace TicketFlowWeb.Models
{
    public class ClientePerfilViewModel
    {
        public int idUsuario { get; set; }
        public string dni { get; set; } = string.Empty;
        public string nombre { get; set; } = string.Empty;
        public string apellidoPaterno { get; set; } = string.Empty;
        public string apellidoMaterno { get; set; } = string.Empty;
        public string telefono { get; set; } = string.Empty;
        public int edad { get; set; }
        public string correoElectronico { get; set; } = string.Empty;
        public string contrasena { get; set; } = string.Empty;
        public int idDistrito { get; set; }
        public int puntosBonus { get; set; }
    }
}
