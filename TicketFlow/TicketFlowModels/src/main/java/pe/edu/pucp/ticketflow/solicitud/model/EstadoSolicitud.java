package pe.edu.pucp.ticketflow.solicitud.model;

public class EstadoSolicitud {
    private int idEstadoSolicitud;
    private String estado;

    public EstadoSolicitud() {
    }

    public EstadoSolicitud(int idEstadoSolicitud, String estado) {
        this.idEstadoSolicitud = idEstadoSolicitud;
        this.estado = estado;
    }

    public int getIdEstadoSolicitud() {
        return idEstadoSolicitud;
    }

    public void setIdEstadoSolicitud(int idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
