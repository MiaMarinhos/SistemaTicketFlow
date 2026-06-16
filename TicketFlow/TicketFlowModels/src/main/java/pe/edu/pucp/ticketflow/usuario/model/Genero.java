package pe.edu.pucp.ticketflow.usuario.model;

public class Genero {
    public int idGenero;
    public String nombre;

    public Genero() {}

    public Genero(int idGenero, String nombre) {
        this.idGenero = idGenero;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }
}
