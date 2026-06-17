namespace TicketFlowWeb.Models.DTO;

public class EventoDTO
{
    public int idEvento { get; set; }
    public string titulo { get; set; } = "";
    public string fecha { get; set; } = "";
    public string hora_inicio { get; set; } = "";
    public string hora_fin { get; set; } = "";
    public double precio { get; set; }
    public string categoria { get; set; } = "";
}
