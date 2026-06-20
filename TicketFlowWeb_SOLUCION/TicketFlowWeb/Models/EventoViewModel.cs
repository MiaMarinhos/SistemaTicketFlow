using System;
using System.ComponentModel.DataAnnotations;

namespace TicketFlowWeb.Models
{
    public class EventoViewModel
    {
        public string titulo { get; set; } = string.Empty;
        public string descripcion { get; set; } = string.Empty;
        public int capacidad_entradas { get; set; }
        public decimal precio { get; set; }
        public string img { get; set; } = string.Empty;
        public DateTime? fechaModerna { get; set; } = DateTime.Today;
        public string horaInicioModerna { get; set; } = string.Empty;
        public string horaFinModerna { get; set; } = string.Empty;
        public string ubicacion { get; set; } = string.Empty;
        public string nombre_establecimiento { get; set; } = string.Empty;
        public Categoria categoria { get; set; }
        public Distrito distrito { get; set; }
        //public Region region { get; set; } = string.Empty;
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