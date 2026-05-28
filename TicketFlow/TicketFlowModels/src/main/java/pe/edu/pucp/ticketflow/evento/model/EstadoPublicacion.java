package pe.edu.pucp.ticketflow.evento.model;

import pe.edu.pucp.ticketflow.ubicacion.model.Distrito;

public class EstadoPublicacion {
    private int idEstado_publicacion;
    private String estado;

    public EstadoPublicacion(){

    }
    public EstadoPublicacion(int idEstado_publicacion, String estado) {
        this.idEstado_publicacion = idEstado_publicacion;
        this.estado = estado;
    }

    public int getIdEstado_publicacion() {
        return idEstado_publicacion;
    }

    public void setIdEstado_publicacion(int idEstado_publicacion) {
        this.idEstado_publicacion = idEstado_publicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
