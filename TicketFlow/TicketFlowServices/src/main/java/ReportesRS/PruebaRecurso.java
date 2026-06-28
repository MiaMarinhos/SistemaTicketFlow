package ReportesRS;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("PruebaRS")
public class PruebaRecurso {

    public PruebaRecurso(){}
    @GET
    @Path("/ping")
    @Produces("text/plain")
    public Response ping() {
        return Response.ok("pong").build();
    }
}