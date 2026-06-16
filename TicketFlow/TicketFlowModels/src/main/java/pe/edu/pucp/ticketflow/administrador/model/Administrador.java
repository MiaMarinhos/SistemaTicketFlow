package pe.edu.pucp.ticketflow.administrador.model;

import pe.edu.pucp.ticketflow.ubicacion.model.Distrito;
import pe.edu.pucp.ticketflow.usuario.model.EstadoUsuario;
import pe.edu.pucp.ticketflow.usuario.model.Genero;
import pe.edu.pucp.ticketflow.usuario.model.TipoUsuario;
import pe.edu.pucp.ticketflow.usuario.model.Usuario;

import java.sql.Date;

public class Administrador extends Usuario {
    private String img;
    private double monto_total;
    private double monto_neto;
    private double monto_disponible;

    public Administrador(){

    }

    public Administrador(int idUsuario, String dni, String nombre, String apellidoPaterno,
                         String apellidoMaterno, String telefono, int edad, Genero genero, String correoElectronico,
                         String contrasena, Date fechaRegistro, Date fechaNacimiento,
                         EstadoUsuario estado, Distrito distrito, int idDistrito, TipoUsuario tipo,
                         String img, double monto_total, double monto_neto, double monto_disponible) {
        super(idUsuario, dni, nombre, apellidoPaterno, apellidoMaterno, telefono,
                edad, genero, correoElectronico, contrasena, fechaRegistro, fechaNacimiento, estado,
                distrito, idDistrito, tipo);
        this.img = img;
        this.monto_disponible = monto_disponible;
        this.monto_total = monto_total;
        this.monto_neto = monto_neto;
    }
    public Administrador(int idUsuario, String correoElectronico,
                         String contrasena, TipoUsuario tipo,
                         String img, double monto_total, double monto_neto, double monto_disponible) {
        super(idUsuario,correoElectronico, contrasena, tipo);
        this.img = img;
        this.monto_disponible = monto_disponible;
        this.monto_total = monto_total;
        this.monto_neto = monto_neto;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(double monto_total) {
        this.monto_total = monto_total;
    }

    public double getMonto_neto() {
        return monto_neto;
    }

    public void setMonto_neto(double monto_neto) {
        this.monto_neto = monto_neto;
    }

    public double getMonto_disponible() {
        return monto_disponible;
    }

    public void setMonto_disponible(double monto_disponible) {
        this.monto_disponible = monto_disponible;
    }


    /*
    @Override
    public String toString(){
        return idAdministrador + " - " + codigo + " - " + nombre + " "
                + apellidoPaterno + " " + apellidoMaterno + " - " + dni;
    }
     */
}
