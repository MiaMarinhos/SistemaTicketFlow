package Administrador;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.ticketflow.IAdministradorBL;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.impl.AdministradorBLImpl;
import pe.edu.pucp.ticketflow.usuario.model.Usuario;

import java.util.List;
import java.util.Map;

@Path("AdminUsuarios")
@Produces(MediaType.APPLICATION_JSON)
public class AdminUsuariosService {

    private final IAdministradorBL administradorBL;

    public AdminUsuariosService() {
        this.administradorBL = new AdministradorBLImpl();
    }

    @GET
    @Path("listar")
    public Response listarUsuarios() {
        try {
            List<Usuario> usuarios = administradorBL.listarUsuarios();

            return Response.ok(usuarios).build();
        }
        catch (BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Ocurrió un error inesperado: " + e.getMessage()))
                    .build();
        }
    }
}