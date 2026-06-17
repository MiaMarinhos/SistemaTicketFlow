package Evento;
//Para evitar conflictos con las clases Time y Date debo manejar esta clase
public class EventoDTO {
    public int idEvento;
    public String titulo;
    public String descripcion;
    public int capacidad_entradas;
    public String fecha;
    public String hora_inicio;
    public String hora_fin;
    public String ubicacion;
    public String nombre_establecimiento;
    public String categoria;
    public double precio;

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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
