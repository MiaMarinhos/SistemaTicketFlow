namespace TicketFlowWeb.Models
{
    public class EstadoPublicacion
    {
        public int idEstado_publicacion { get; set; }

        public string estado { get; set; } = string.Empty;

        public EstadoPublicacion()
        {
        }

        public EstadoPublicacion(int idEstado_publicacion, string estado)
        {
            this.idEstado_publicacion = idEstado_publicacion;
            this.estado = estado;
        }
    }
}
