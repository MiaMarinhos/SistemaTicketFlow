using System;
using System.ComponentModel.DataAnnotations;

namespace TicketFlowWeb.Models
{
    public class EventoViewModel
    {
        public int idEvento { get; set; }
        public string titulo { get; set; } = string.Empty;
        public string descripcion { get; set; } = string.Empty;
        public int capacidad_entradas { get; set; }
        public decimal precio { get; set; }
        public string img { get; set; } = string.Empty;

        public string fecha { get; set; } = string.Empty;
        public string hora_inicio { get; set; } = string.Empty;
        public string hora_fin { get; set; } = string.Empty;

        public string ubicacion { get; set; } = string.Empty;
        public string nombre_establecimiento { get; set; } = string.Empty;

        // Llaves Foráneas (FK) necesarias para el INSERT de Java
        public int FK_idDistrito { get; set; }
        public int idAnfitrion { get; set; }
        public int FK_idCategoria_evento { get; set; }
        public int FK_idEstadoPublicacion { get; set; }
        public int FK_idEstadoEvento { get; set; }

        public Categoria categoria { get; set; }
        public Distrito distrito { get; set; }
        public Anfitrion anfitrion { get; set; }

        public  EstadoEventoViewModel estadoEvento { get; set; }

        public EstadoPublicacion estadoPublicacion { get; set; }
    }

    public class Anfitrion
    {
        public int idUsuario { get; set; }
        public string nombre { get; set; } = string.Empty;
        public string razonSocial { get; set; } = string.Empty;
    }

    public class Categoria
    {
        public int idCategoria_evento { get; set; }
        public string nombre { get; set; } = string.Empty;
    }
}