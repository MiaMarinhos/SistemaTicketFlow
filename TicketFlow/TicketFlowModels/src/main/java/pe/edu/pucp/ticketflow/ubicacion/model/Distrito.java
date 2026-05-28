package pe.edu.pucp.ticketflow.ubicacion.model;

public class Distrito {
    private int idDistrito;
    private String nombre;
    private Region region;

    public Distrito(){

    }
    public Distrito(int idDistrito, String nombre, Region region) {
        this.idDistrito = idDistrito;
        this.nombre = nombre;
        this.region = region;
    }

    public int getIdDistrito() {
        return idDistrito;
    }
    public void setIdDistrito(int idDistrito) {
        this.idDistrito = idDistrito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
