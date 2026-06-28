namespace TicketFlowWeb.Models
{
    public class EstadoEventoViewModel
    {
        public int idEstado_evento { get; set; }
        public string? estado { get; set; }

        public EstadoEventoViewModel()
        {
        }

        public EstadoEventoViewModel(int idEstado_evento, string estado)
        {
            this.idEstado_evento = idEstado_evento;
            this.estado = estado;
        }
    }
}
