package pe.edu.pucp.ticketflow.evento.model;

public class categoria_evento {
   private int idCategoria_evento;
   private String nombre;
   private int dias_para_publicacion;

   public categoria_evento() {

   }

   public categoria_evento(int idCategoria_evento, String nombre, int dias_para_publicacion) {
       this.idCategoria_evento = idCategoria_evento;
       this.nombre = nombre;
       this.dias_para_publicacion = dias_para_publicacion;
   }

    public int getIdCategoria_evento() {
        return idCategoria_evento;
    }

    public void setIdCategoria_evento(int idCategoria_evento) {
        this.idCategoria_evento = idCategoria_evento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDias_para_publicacion() {
        return dias_para_publicacion;
    }

    public void setDias_para_publicacion(int dias_para_publicacion) {
        this.dias_para_publicacion = dias_para_publicacion;
    }
}
