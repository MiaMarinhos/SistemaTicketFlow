package pe.edu.pucp.ticketflow.usuario.model;

import pe.edu.pucp.ticketflow.ubicacion.model.Distrito;

import java.sql.Date;

public abstract class Usuario {
    private int idUsuario;
    private String dni;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private int edad;
    private Genero genero;
    private String correoElectronico;
    private String contrasena;
    private Date fechaRegistro;
    private Date fechaNacimiento;
    private EstadoUsuario estado;
    private Distrito distrito;
    private int idDistrito;
    private TipoUsuario tipo;


    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public int getIdDistrito(){return idDistrito;}

    public void setIdDistrito(int idDistrito) {
        this.idDistrito = idDistrito;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public Usuario(){}

    public Usuario(int idUsuario, String dni, String nombre, String apellidoPaterno,
                   String apellidoMaterno, String telefono, int edad, Genero genero,
                   String correoElectronico,
                   String contrasena, Date fechaRegistro, Date fechaNacimiento,
                   EstadoUsuario estado, Distrito distrito, int idDistrito, TipoUsuario tipo){
        this.idUsuario = idUsuario;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.edad = edad;
        this.genero = genero;
        this.fechaRegistro = fechaRegistro;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
        this.distrito = distrito;
        this.idDistrito = idDistrito;
        this.tipo = tipo;
    }
    public Usuario(int idUsuario, String dni, String nombre, String apellidoPaterno,
                   String apellidoMaterno, String telefono, String correoElectronico,
                   String contrasena, Date fechaNacimiento, int idDistrito){
        this.idUsuario = idUsuario;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.fechaNacimiento = fechaNacimiento;
        this.idDistrito = idDistrito;
    }
    public Usuario(int idUsuario, String correoElectronico,
                   String contrasena, TipoUsuario tipo ){
        this.idUsuario = idUsuario;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.tipo = tipo;
    }
}
