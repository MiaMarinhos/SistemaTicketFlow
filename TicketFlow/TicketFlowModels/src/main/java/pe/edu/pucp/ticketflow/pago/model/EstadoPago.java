package pe.edu.pucp.ticketflow.pago.model;

public class EstadoPago {
    private int idEstadoPago;
    private String estado;

    public int getIdEstadoPago() {
        return idEstadoPago;
    }

    public void setIdEstadoPago(int idEstadoPago) {
        this.idEstadoPago = idEstadoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public EstadoPago(){}

    public EstadoPago(int idEstadoPago, String estado) {
        this.idEstadoPago = idEstadoPago;
        this.estado = estado;
    }
}
