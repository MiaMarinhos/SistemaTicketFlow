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
import pe.edu.pucp.ticketflow.usuario.model.Cliente;
import pe.edu.pucp.ticketflow.usuario.model.LoginRequest;
import pe.edu.pucp.ticketflow.usuario.model.Usuario;

import java.util.Map;

@Path("ClienteRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteService {

    private final IUsuarioBL usuarioBL;
    private final IClienteBL clienteBL;

    public ClienteService() {
        this.usuarioBL = new UsuarioBLImpl();
        this.clienteBL = new ClienteBLImpl();
    }

    @POST
    @Path("/Register")
    public Response RegistroCliente(Cliente cliente) {
        try {
            String mensaje = usuarioBL.registrarCliente(cliente);

            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("mensaje", mensaje))
                    .build();

        } catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error interno."))
                    .build();
        }
    }

    @GET
    @Path("/perfil/{idUsuario}")
    public Response verPerfilCliente(@PathParam("idUsuario") Integer idUsuario) {
        try {
            Cliente cliente = clienteBL.buscarClientePorId(idUsuario);

            return Response.ok(cliente).build();

        } catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error interno: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/verPuntosBonus/{idUsuario}")
    public Response verPuntosBonusDelCliente(@PathParam("idUsuario") Integer idUsuario){
        try {
            int pb = clienteBL.obtenerPuntosBonus(idUsuario);

            return Response.ok(pb).build();

        } catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error interno: " + e.getMessage()))
                    .build();
        }
    }

}
