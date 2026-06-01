using System;
using System.ComponentModel.DataAnnotations;

namespace TicketFlowWeb.Models
{
    public class EventoViewModel
    {
        public string Titulo { get; set; } = string.Empty;
        public string Descripcion { get; set; } = string.Empty;
        public int NumeroEntradas { get; set; }
        public decimal Precio { get; set; }
        public string ImagenRuta { get; set; } = string.Empty;
        public DateTime? Fecha { get; set; } = DateTime.Today;
        public string HoraInicio { get; set; } = string.Empty;
        public string HoraFin { get; set; } = string.Empty;
        public string Ubicacion { get; set; } = string.Empty;
        public string SitioEvento { get; set; } = string.Empty;
        public string Distrito { get; set; } = string.Empty;
        public string Region { get; set; } = string.Empty;
    }
}