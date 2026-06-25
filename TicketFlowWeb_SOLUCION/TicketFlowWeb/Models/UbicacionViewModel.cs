using System.ComponentModel.DataAnnotations;

namespace TicketFlowWeb.Models
{
    public class Distrito
    {
        [Range(1, int.MaxValue, ErrorMessage = "* Debe seleccionar un distrito válido")]
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
