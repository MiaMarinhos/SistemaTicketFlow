package Usuario;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.ticketflow.IClienteBL;
import pe.edu.pucp.ticketflow.IUsuarioBL;
import pe.edu.pucp.ticketflow.impl.ClienteBLImpl;
import pe.edu.pucp.ticketflow.impl.UsuarioBLImpl;
import pe.edu.pucp.ticketflow.usuario.model.LoginRequest;
import pe.edu.pucp.ticketflow.usuario.model.Usuario;

import java.util.Map;

@Path("UsuarioRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioService {
    private final IUsuarioBL usuarioBL;
    public UsuarioService() {
        this.usuarioBL = new UsuarioBLImpl();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response IniciarSesion(LoginRequest request) { // <--- Recibe el objeto completo
        try {
            // Sacamos los datos de adentro del objeto request
            Usuario user = usuarioBL.iniciarSesion(request.getCorreo(), request.getPassword(), request.getRol());

            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("mensaje", "Usuario no encontrado."))
                        .build();
            }
            return Response.ok(user).build();
        } catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", "Error interno.")).build();
        }
    }

}
