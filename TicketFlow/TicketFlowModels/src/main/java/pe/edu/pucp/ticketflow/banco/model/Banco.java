package pe.edu.pucp.ticketflow.banco.model;

public class Banco {
    private int id;
    private String nombre_largo;
    private String nombre_corto;

    public Banco(){};
    public Banco(int id, String nombre_largo, String nombre_corto) {
        this.id = id;
        this.nombre_largo = nombre_largo;
        this.nombre_corto = nombre_corto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_largo() {
        return nombre_largo;
    }

    public void setNombre_largo(String nombre_largo) {
        this.nombre_largo = nombre_largo;
    }

    public String getNombre_corto() {
        return nombre_corto;
    }

    public void setNombre_corto(String nombre_corto) {
        this.nombre_corto = nombre_corto;
    }

    @Override
    public String toString() {
        return id + " - " + nombre_largo + " - " + nombre_corto;
    }

}
