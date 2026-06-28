namespace TicketFlowWeb.Models
{
    public class CategoriaEventoViewModel
    {
        public int idCategoria_evento { get; set; }
        public string? nombre { get; set; }
        public int dias_para_publicacion { get; set; }

        public CategoriaEventoViewModel()
        {
        }

        public CategoriaEventoViewModel(int idCategoria_evento, string nombre, int dias_para_publicacion)
        {
            this.idCategoria_evento = idCategoria_evento;
            this.nombre = nombre;
            this.dias_para_publicacion = dias_para_publicacion;
        }
    }
}
