package pe.edu.pucp.ticketflow.evento.model;

import pe.edu.pucp.ticketflow.ubicacion.model.Distrito;
import pe.edu.pucp.ticketflow.usuario.model.Anfitrion;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Evento {
    private int idEvento;
    private String titulo;
    private String descripcion;
    private int capacidad_entradas;
    private categoria_evento categoria;
    private int FK_idCategoria_evento;
    private Date fecha;
    private Time hora_inicio;
    private Time hora_fin;

    private java.time.LocalDate fechaModerna;
    private java.time.LocalTime horaInicioModerna;
    private java.time.LocalTime horaFinModerna;

    private String ubicacion;
    private String nombre_establecimiento;
    private String img;
    private double precio;
    private Distrito distrito;
    private int FK_idDistrito;
    private Anfitrion anfitrion;
    private int idAnfitrion;
    private EstadoPublicacion estadoPublicacion;
    private int FK_idEstadoPublicacion;
    private EstadoEvento estadoEvento;
    private int FK_idEstadoEvento;
    private boolean activo;
    public Evento() {

    }
    //Este se usaria si nuestro id se realizara automaticamente
    //se usaria cuando creamos uno nuevo para agregarlo a la base de datos
    //Por eso, activo se pone automaticamente como true
    public Evento(String titulo,String descripcion,int capacidad_entradas,
                  categoria_evento categoria, Date fecha,Time hora_inicio,Time hora_fin,
                  String ubicacion,String nombre_establecimiento,String img,double precio,
                  Distrito distrito,int idAnfitrion,EstadoPublicacion estadoPublicacion, EstadoEvento estadoEvento,
                  Anfitrion anfitrion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.capacidad_entradas = capacidad_entradas;
        this.categoria = categoria;
        this.fecha = fecha;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.ubicacion = ubicacion;
        this.nombre_establecimiento = nombre_establecimiento;
        this.img = img;
        this.precio = precio;
        this.distrito = distrito;
        this.idAnfitrion = idAnfitrion;
        this.estadoPublicacion = estadoPublicacion;
        this.estadoEvento = estadoEvento;
        this.activo = true;
        this.anfitrion = anfitrion;
    }
    //este se usa cuando se lee, por eso pasamos "activo" como parametro
    public Evento(int idEvento,String titulo,String descripcion,int capacidad_entradas,
                  int FK_idCategoria_evento, Date fecha,Time hora_inicio,Time hora_fin,
                  String ubicacion,String nombre_establecimiento,String img,double precio,
                  int FK_idDistrito,int idAnfitrion,int FK_idEstadoPublicacion,int FK_idEstadoEvento,
                  boolean activo, Anfitrion anfitrion) {
        this.idEvento = idEvento;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.capacidad_entradas = capacidad_entradas;
        this.FK_idCategoria_evento = FK_idCategoria_evento;
        this.fecha = fecha;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.ubicacion = ubicacion;
        this.nombre_establecimiento = nombre_establecimiento;
        this.img = img;
        this.precio = precio;
        this.FK_idDistrito = FK_idDistrito;
        this.idAnfitrion = idAnfitrion;
        this.FK_idEstadoPublicacion = FK_idEstadoPublicacion;
        this.FK_idEstadoEvento = FK_idEstadoEvento;
        this.activo=activo;
        this.anfitrion = anfitrion;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCapacidad_entradas() {
        return capacidad_entradas;
    }

    public void setCapacidad_entradas(int capacidad_entradas) {
        this.capacidad_entradas = capacidad_entradas;
    }

    public categoria_evento getCategoria() {
        return categoria;
    }

    public void setCategoria(categoria_evento categoria) {
        this.categoria = categoria;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(Time hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public Time getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(Time hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNombre_establecimiento() {
        return nombre_establecimiento;
    }

    public void setNombre_establecimiento(String nombre_establecimiento) {
        this.nombre_establecimiento = nombre_establecimiento;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public int getIdAnfitrion() {
        return idAnfitrion;
    }

    public void setIdAnfitrion(int idAnfitrion) {
        this.idAnfitrion = idAnfitrion;
    }

    public EstadoPublicacion getEstadoPublicacion() {
        return estadoPublicacion;
    }

    public void setEstadoPublicacion(EstadoPublicacion estadoPublicacion) {
        this.estadoPublicacion = estadoPublicacion;
    }

    public EstadoEvento getEstadoEvento() {
        return estadoEvento;
    }

    public void setEstadoEvento(EstadoEvento estadoEvento) {
        this.estadoEvento = estadoEvento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getFK_idEstadoEvento() {
        return FK_idEstadoEvento;
    }

    public void setFK_idEstadoEvento(int FK_idEstadoEvento) {
        this.FK_idEstadoEvento = FK_idEstadoEvento;
    }

    public int getFK_idEstadoPublicacion() {
        return FK_idEstadoPublicacion;
    }

    public void setFK_idEstadoPublicacion(int FK_idEstadoPublicacion) {
        this.FK_idEstadoPublicacion = FK_idEstadoPublicacion;
    }

    public int getFK_idDistrito() {
        return FK_idDistrito;
    }

    public void setFK_idDistrito(int FK_idDistrito) {
        this.FK_idDistrito = FK_idDistrito;
    }

    public int getFK_idCategoria_evento() {
        return FK_idCategoria_evento;
    }

    public void setFK_idCategoria_evento(int FK_idCategoria_evento) {
        this.FK_idCategoria_evento = FK_idCategoria_evento;
    }

    public Anfitrion getAnfitrion() {
        return anfitrion;
    }

    public void setAnfitrion(Anfitrion anfitrion) {
        this.anfitrion = anfitrion;
    }


    public Evento(int idEvento,String titulo,String descripcion,int capacidad_entradas,
                  Date fecha,Time hora_inicio,Time hora_fin,
                  String ubicacion,String nombre_establecimiento,String img,double precio,
                  Distrito distrito, Anfitrion anfitrion, categoria_evento categoria) {
        this.idEvento = idEvento;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.capacidad_entradas = capacidad_entradas;
        this.fecha = fecha;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.ubicacion = ubicacion;
        this.nombre_establecimiento = nombre_establecimiento;
        this.img = img;
        this.precio = precio;
        this.anfitrion = anfitrion;
        this.distrito = distrito;
        this.categoria = categoria;
    }

    public LocalDate getFechaModerna() {
        return fechaModerna;
    }

    public void setFechaModerna(LocalDate fechaModerna) {
        this.fechaModerna = fechaModerna;
    }

    public LocalTime getHoraInicioModerna() {
        return horaInicioModerna;
    }

    public void setHoraInicioModerna(LocalTime horaInicioModerna) {
        this.horaInicioModerna = horaInicioModerna;
    }

    public LocalTime getHoraFinModerna() {
        return horaFinModerna;
    }

    public void setHoraFinModerna(LocalTime horaFinModerna) {
        this.horaFinModerna = horaFinModerna;
    }
}
