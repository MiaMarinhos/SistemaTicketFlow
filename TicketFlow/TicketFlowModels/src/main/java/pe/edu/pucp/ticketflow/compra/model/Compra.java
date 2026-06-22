package pe.edu.pucp.ticketflow.compra.model;

import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.puntosBonus.model.PuntosBonus;
import pe.edu.pucp.ticketflow.usuario.model.Cliente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Compra {
    private int idCompra;
    private int entradasCompradas;
    private LocalDate fechaCompra;
    private LocalTime horaCompra;
    private String metodoPago;
    private double montoParcial;
    private double montoTotal;

    private int idEstado;
    private int idpuntoBonus;
    private int idCliente;
    private int idEvento;

    private EstadoCompra estado;
    private PuntosBonus puntosBonus;
    private Cliente cliente;
    private Evento evento;

    private String fechaCompraS;
    private String horaCompraS;

    public String getFechaCompraS() {
        return fechaCompraS;
    }

    public void setFechaCompraS(String fechaCompraS) {
        this.fechaCompraS = fechaCompraS;
        if (fechaCompraS != null && !fechaCompraS.isEmpty()){
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.fechaCompra = LocalDate.parse(fechaCompraS, formato);
        }
    }

    public String getHoraCompraS() {
        return horaCompraS;
    }

    public void setHoraCompraS(String horaCompraS) {
        this.horaCompraS = horaCompraS;
        if (horaCompraS != null && !horaCompraS.isEmpty()){
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
            this.horaCompra = LocalTime.parse(horaCompraS, formato);
        }
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdpuntoBonus() {
        return idpuntoBonus;
    }

    public void setIdpuntoBonus(int idpuntoBonus) {
        this.idpuntoBonus = idpuntoBonus;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getEntradasCompradas() {
        return entradasCompradas;
    }

    public void setEntradasCompradas(int entradasCompradas) {
        this.entradasCompradas = entradasCompradas;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.fechaCompraS = fechaCompra.format(formato);
    }

    public LocalTime getHoraCompra() {
        return horaCompra;
    }

    public void setHoraCompra(LocalTime horaCompra) {
        this.horaCompra = horaCompra;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.horaCompraS = horaCompra.format(formato);
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double getMontoParcial() {
        return montoParcial;
    }

    public void setMontoParcial(double montoParcial) {
        this.montoParcial = montoParcial;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public EstadoCompra getEstado() {
        return estado;
    }

    public void setEstado(EstadoCompra estado) {
        this.estado = estado;
    }

    public PuntosBonus getPuntosBonus() {
        return puntosBonus;
    }

    public void setPuntosBonus(PuntosBonus puntosBonus) {
        this.puntosBonus = puntosBonus;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Compra() {}

    public Compra(int idCompra, int entradasCompradas, LocalDate fechaCompra,
                  LocalTime horaCompra, String metodoPago, double montoParcial,
                  double montoTotal, EstadoCompra estado, PuntosBonus puntosBonus,
                  Cliente cliente, Evento evento){
        this.idCompra = idCompra;
        this.entradasCompradas = entradasCompradas;
        this.fechaCompra = fechaCompra;
        this.horaCompra = horaCompra;
        this.metodoPago = metodoPago;
        this.montoParcial = montoParcial;
        this.montoTotal = montoTotal;
        this.puntosBonus = puntosBonus;
        this.estado = estado;
        this.cliente = cliente;
        this.evento = evento;
    }
}
