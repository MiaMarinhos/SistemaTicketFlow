package Compra;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.ticketflow.IComprasBL;
import pe.edu.pucp.ticketflow.compra.model.Compra;
import pe.edu.pucp.ticketflow.impl.ComprasBLImpl;

<<<<<<< HEAD
import java.util.Map;

=======
>>>>>>> a54a2e19af7d2b41fb4627a5d5cd2414cd4cd0dd
@Path("CompraRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class CompraService {
<<<<<<< HEAD
    private final IComprasBL comprasBL;

    public CompraService(){
        comprasBL = new ComprasBLImpl();
    }

    @POST
    @Path("crear")
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearUsuario(Compra compra){
        try {
            comprasBL.registrarCompra(compra);
            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("mensaje", "Compra creado"))
                    .build();
        }
        catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
=======
    private IComprasBL compraBL = new ComprasBLImpl();

    @POST
    @Path("/registrar")
    public Response registrarCompra(Compra compraRequest) {
        try {
            // El cliente desde C# solo enviará: idCliente, idEvento, entradasCompradas, metodoPago, idEstado, idpuntoBonus
            Compra compraProcesada = compraBL.registrarCompra(compraRequest);

            // Retornamos HTTP 200 con todo el objeto calculado
            return Response.ok(compraProcesada).build();

        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error interno en el servidor: " + e.getMessage() + "\"}").build();
>>>>>>> a54a2e19af7d2b41fb4627a5d5cd2414cd4cd0dd
        }
    }
}
