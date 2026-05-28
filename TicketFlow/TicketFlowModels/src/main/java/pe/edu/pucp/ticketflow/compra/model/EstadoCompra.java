package pe.edu.pucp.ticketflow.compra.model;

public class EstadoCompra {
    private int idEstadoCompra;
    private String estado;

    public int getIdEstadoCompra() {
        return idEstadoCompra;
    }

    public void setIdEstadoCompra(int idEstadoCompra) {
        this.idEstadoCompra = idEstadoCompra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public EstadoCompra(){}

    public EstadoCompra(int idEstadoCompra, String estado) {
        this.idEstadoCompra = idEstadoCompra;
        this.estado = estado;
    }
}
