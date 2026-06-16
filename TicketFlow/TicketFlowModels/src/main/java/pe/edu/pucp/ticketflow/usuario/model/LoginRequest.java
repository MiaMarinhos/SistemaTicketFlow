package pe.edu.pucp.ticketflow.usuario.model;

public class LoginRequest {
    private String correo;
    private String rol;

    public LoginRequest(String correo, String rol) {
        this.correo = correo;
        this.rol = rol;
    }
    public LoginRequest() {}

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
