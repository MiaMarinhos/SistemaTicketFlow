
using System.ComponentModel.DataAnnotations;
using TicketFlowModel.Ubicacion;

namespace TicketFlowWeb.Models
{
    public class ClienteViewModel
    {
        [Required(ErrorMessage = "* Campo obligatorio Usuario")]
        public int idUsuario { get; set; }
        [Required(ErrorMessage = "* Campo obligatorio correo")]
        public string correoElectronico { get; set; } = string.Empty;
        [Required(ErrorMessage = "* Campo contra")]
        public string contrasena { get; set; } = string.Empty;
        [Required(ErrorMessage = "* Campo edad")]
        public int edad { get; set; }
        [Required(ErrorMessage = "* Campo obligatorio dni")]
        public string dni { get; set; } = string.Empty;
        [Required(ErrorMessage = "* Campo obligatorio nombre")]
        public string nombre { get; set; } = string.Empty;
        [Required(ErrorMessage = "* Campo obligatorio paterno")]
        public string apellidoPaterno { get; set; } = string.Empty; // Con 'P' mayúscula si en Java se llama apellidoPaterno
        [Required(ErrorMessage = "* Campo obligatorio materno")]
        public string apellidoMaterno { get; set; } = string.Empty;
        [Required(ErrorMessage = "* Campo obligatorio fecha")]
        public DateOnly fechaNacimiento { get; set; }
        [Required(ErrorMessage = "* Campo obligatorio telefono")]
        public string telefono { get; set; } = string.Empty;
        public Genero? genero { get; set; }
        public EstadoUsuario? estado { get; set; }
        public Distrito? distrito { get; set; }

        // El objeto anidado para el Rol
        public TipoUsuario? tipo { get; set; }

        public int puntosBonus;

        //para formularios
        public int idDistritoForm { get; set; }
        public int idGeneroForm { get; set; }
    }

    public class Genero
    {
        public int idGenero { get; set; }
        public string nombre { get; set; } = string.Empty;
    }
    public class EstadoUsuario
    {
        public int idEstadoUsuario { get; set; }
        public string nombre { get; set; } = string.Empty;
    }

    public class Distrito
    {
        public int idDistrito { get; set; }
        public string nombre { get; set; } = string.Empty;

        public Region region { get; set; }
    }
    public class Region
    {
        public int idRegion { get; set; }
        public string nombre { get; set; } = string.Empty;
    }
}
