package pe.edu.pucp.ticketflow.usuario.model;

import pe.edu.pucp.ticketflow.banco.model.Banco;
import pe.edu.pucp.ticketflow.ubicacion.model.Distrito;

import java.sql.Date;

public class Anfitrion extends Usuario{
    private String razonSocial;
    private String ruc;
    private String cuentaBancaria;
    private Banco banco;

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Anfitrion(){}

    public Anfitrion(int idUsuario, String dni, String nombre, String apellidoPaterno,
                     String apellidoMaterno, String telefono, int edad, Genero genero, String correoElectronico,
                     String contrasena, Date fechaRegistro, Date fechaNacimiento,
                     EstadoUsuario estado, Distrito distrito, int idDistrito, TipoUsuario tipo, String razonSocial, String ruc,
                     String cuentaBancaria, Banco banco) {
        super(idUsuario, dni, nombre, apellidoPaterno, apellidoMaterno, telefono,
                edad, genero, correoElectronico, contrasena, fechaRegistro, fechaNacimiento, estado,
                distrito, idDistrito, tipo);
        this.razonSocial = razonSocial;
        this.ruc = ruc;
        this.cuentaBancaria = cuentaBancaria;
        this.banco = banco;
    }

    public Anfitrion(String razonSocial, String ruc, String cuentaBancaria, Banco banco) {
        this.razonSocial = razonSocial;
        this.ruc = ruc;
        this.cuentaBancaria = cuentaBancaria;
        this.banco = banco;
    }
}
