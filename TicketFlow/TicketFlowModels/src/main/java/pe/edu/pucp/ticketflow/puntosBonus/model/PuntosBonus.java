package pe.edu.pucp.ticketflow.puntosBonus.model;

public class PuntosBonus {
    private int idPuntosBonus;
    private int puntosCanjeables;
    private int descuento;

    public PuntosBonus(){
    }

    public PuntosBonus(int idPuntosBonus, int puntosCanjeables, int descuento){
        this.idPuntosBonus = idPuntosBonus;
        this.puntosCanjeables = puntosCanjeables;
        this.descuento = descuento;
    }

    //GETTERS AND SETTERS

    public int getIdPuntosBonus() {
        return idPuntosBonus;
    }

    public void setIdPuntosBonus(int idPuntosBonus) {
        this.idPuntosBonus = idPuntosBonus;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getPuntosCanjeables() {
        return puntosCanjeables;
    }

    public void setPuntosCanjeables(int puntosCanjeables) {
        this.puntosCanjeables = puntosCanjeables;
    }

}
