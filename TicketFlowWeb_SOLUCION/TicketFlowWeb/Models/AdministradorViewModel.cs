using static TicketFlowWeb.Models.DistritoViewModel;

namespace TicketFlowWeb.Models
{
    public class AdministratorViewModel
    {
        public int IdUsuario { get; set; }

        public string Nombre { get; set; } = "";

        public string ApellidoPaterno { get; set; } = "";

        public string ApellidoMaterno { get; set; } = "";

        public string CorreoElectronico { get; set; } = "";

        public string Dni { get; set; } = "";

        public string Telefono { get; set; } = "";

        public string Img { get; set; } = "";

        public int Edad { get; set; }

        public string Contrasena { get; set; } = "";

        public string? FechaNacimiento { get; set; }

        public int IdDistrito { get; set; }

        public DistritoViewModel? Distrito { get; set; }

        public GeneroViewModel? Genero { get; set; }

        public double Monto_total { get; set; }

        public double Monto_neto { get; set; }

        public double Monto_disponible { get; set; }
    }
}