namespace TicketFlowWeb.Models
{
    public class DistritoViewModel
    {
        public class DistritoDTO
        {
            public int IdDistrito { get; set; }

            public string Nombre { get; set; } = "";

            public RegionViewModel? Region { get; set; }
        }
    }
}
