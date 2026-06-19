package pe.edu.pucp.ticketflow.usuario.model;

public class LoginRequest {
    private String correo;
    private String rol;
    private String password;

    public LoginRequest(String correo, String password, String rol) {
        this.correo = correo;
        this.rol = rol;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
