package pe.edu.pucp.ticketflow.solicitud.model;

import pe.edu.pucp.ticketflow.administrador.model.Administrador;
import pe.edu.pucp.ticketflow.usuario.model.Cliente;

public class Solicitud {
    private int idSolicitudes;

    private String telefonoContacto;
    private String correoContacto;
    private String motivo;

    private int idAdministrador;
    private int idCliente;
    private int idEstadoSolicitud;

    private Administrador administrador;
    private Cliente cliente;
    private EstadoSolicitud estadoSolicitud;

    public Solicitud() {
    }

    public Solicitud(int idSolicitudes,
                     String telefonoContacto,
                     String correoContacto,
                     String motivo,
                     int idAdministrador,
                     int idCliente,
                     int idEstadoSolicitud) {

        this.idSolicitudes = idSolicitudes;
        this.telefonoContacto = telefonoContacto;
        this.correoContacto = correoContacto;
        this.motivo = motivo;
        this.idAdministrador = idAdministrador;
        this.idCliente = idCliente;
        this.idEstadoSolicitud = idEstadoSolicitud;
    }

    public int getIdSolicitudes() {
        return idSolicitudes;
    }

    public void setIdSolicitudes(int idSolicitudes) {
        this.idSolicitudes = idSolicitudes;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEstadoSolicitud() {
        return idEstadoSolicitud;
    }

    public void setIdEstadoSolicitud(int idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public EstadoSolicitud getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }
}
