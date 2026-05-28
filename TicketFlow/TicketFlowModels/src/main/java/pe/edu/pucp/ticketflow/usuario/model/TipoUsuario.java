package pe.edu.pucp.ticketflow.usuario.model;

public class TipoUsuario {
    private int idTipoUsuario;
    private String tipoUsuario;

    public TipoUsuario() {}
    public TipoUsuario(int idTipoUsuario, String tipoUsuario) {}

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
