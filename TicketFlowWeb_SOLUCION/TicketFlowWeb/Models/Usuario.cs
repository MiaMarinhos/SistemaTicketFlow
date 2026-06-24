
namespace TicketFlowWeb.Models
{
    public class Usuario
    {
        public int idUsuario { get; set; }
        public string correoElectronico { get; set; } = string.Empty;
        public string contrasena { get; set; } = string.Empty;
        public int edad { get; set; }

        // Los campos que vas a agregar en tu SP y DAO de Java:
        public string dni { get; set; } = string.Empty;
        public string nombre { get; set; } = string.Empty;
        public string apellidoPaterno { get; set; } = string.Empty; // Con 'P' mayúscula si en Java se llama apellidoPaterno
        public string apellidoMaterno { get; set; } = string.Empty;
        public string telefono { get; set; } = string.Empty;
        public string genero { get; set; } = string.Empty;
        public EstadoUsuarioViewModel? estado { get; set; }

        // El objeto anidado para el Rol
        public TipoUsuario? tipo { get; set; }
    }
}
