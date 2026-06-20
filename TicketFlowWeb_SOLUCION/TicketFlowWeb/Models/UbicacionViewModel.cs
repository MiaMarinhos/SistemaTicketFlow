namespace TicketFlowWeb.Models
{
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
