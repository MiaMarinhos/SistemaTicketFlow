using System;
using System.ComponentModel.DataAnnotations;

namespace TicketFlowWeb.Models
{
    public class ClienteViewModel
    {
        // 💡 Quitamos Required porque en un Registro el ID aún no existe (es 0)
        public int idUsuario { get; set; }

        [Required(ErrorMessage = "* Campo obligatorio correo")]
        [EmailAddress(ErrorMessage = "* El formato del correo electrónico no es válido")]
        public string correoElectronico { get; set; } = string.Empty;

        [Required(ErrorMessage = "* Campo contra")]
        [StringLength(45, MinimumLength = 6, ErrorMessage = "* La contraseña debe tener entre 6 y 45 caracteres")]
        public string contrasena { get; set; } = string.Empty;

        [Required(ErrorMessage = "* Campo edad")]
        [Range(18, 120, ErrorMessage = "* Debes ser mayor de 18 años")]
        public int edad { get; set; }

        [Required(ErrorMessage = "* Campo obligatorio dni")]
        [RegularExpression(@"^\d{8}$", ErrorMessage = "* El DNI debe tener exactamente 8 números")]
        public string dni { get; set; } = string.Empty;

        [Required(ErrorMessage = "* Campo obligatorio nombre")]
        public string nombre { get; set; } = string.Empty;

        [Required(ErrorMessage = "* Campo obligatorio paterno")]
        public string apellidoPaterno { get; set; } = string.Empty;

        [Required(ErrorMessage = "* Campo obligatorio materno")]
        public string apellidoMaterno { get; set; } = string.Empty;

        [Required(ErrorMessage = "* Campo obligatorio fecha")]
        public DateOnly fechaNacimiento { get; set; }

        [Required(ErrorMessage = "* Campo obligatorio telefono")]
        [RegularExpression(@"^9\d{8}$", ErrorMessage = "* El teléfono debe empezar con 9 y tener 9 dígitos")]
        public string telefono { get; set; } = string.Empty;

        // Objetos de relaciones (Se quedan exactamente igual)
        public Genero? genero { get; set; }
        public EstadoUsuario? estado { get; set; }
        public Distrito? distrito { get; set; }
        public TipoUsuario? tipo { get; set; }
        public int puntosBonus { get; set; }

        // Propiedades de ayuda para los formularios
        [Range(1, int.MaxValue, ErrorMessage = "* Debe seleccionar un distrito válido")]
        public int idDistritoForm { get; set; }

        [Range(1, int.MaxValue, ErrorMessage = "* Debe seleccionar un género válido")]
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
}