package pe.edu.pucp.ticketflow.evento.model;

public class EstadoEvento {
   private int idEstado_evento;
   private String estado;

   public EstadoEvento(){

   }

   public EstadoEvento(int idEstado_evento, String estado) {
      this.idEstado_evento = idEstado_evento;
      this.estado = estado;
   }

   public String getEstado() {
      return estado;
   }

   public void setEstado(String estado) {
      this.estado = estado;
   }

   public int getIdEstado_evento() {
      return idEstado_evento;
   }

   public void setIdEstado_evento(int idEstado_evento) {
      this.idEstado_evento = idEstado_evento;
   }
}
