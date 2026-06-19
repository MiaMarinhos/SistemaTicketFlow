package Usuario;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.ticketflow.IDistritoDAO;
import pe.edu.pucp.ticketflow.IUbicacionBL;
import pe.edu.pucp.ticketflow.impl.DistritoDAOImpl;
import pe.edu.pucp.ticketflow.impl.UbicacionBLImpl;
import pe.edu.pucp.ticketflow.ubicacion.model.Distrito;

import java.util.List;
import java.util.Map;

@Path("DistritoRS")
@Produces(MediaType.APPLICATION_JSON)
public class UbicacionService {
    private final IUbicacionBL ubicacionBL = new UbicacionBLImpl(); // O usa tu capa BL si la tienes

    @GET
    @Path("/listar")
    public Response listar() {
        try{
            List<Distrito> distritos = ubicacionBL.listarDistritos();
            return Response.ok(distritos).build();
        }catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", "Error interno.")).build();
        }
    }
}
