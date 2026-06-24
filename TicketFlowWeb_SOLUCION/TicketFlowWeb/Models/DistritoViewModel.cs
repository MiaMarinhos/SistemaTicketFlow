namespace TicketFlowWeb.Models
{
    public class DistritoViewModel
    {
            public int IdDistrito { get; set; }

            public string Nombre { get; set; } = "";

            public RegionViewModel? Region { get; set; }
    }
}
