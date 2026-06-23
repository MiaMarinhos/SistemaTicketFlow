package Compra;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.ticketflow.IComprasBL;
import pe.edu.pucp.ticketflow.compra.model.Compra;
import pe.edu.pucp.ticketflow.impl.ComprasBLImpl;


import java.util.List;
import java.util.Map;


@Path("CompraRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class CompraService {

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
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        }
    }

    private IComprasBL compraBL = new ComprasBLImpl();

    @POST
    @Path("/registrar")
    public Response registrarCompra(Compra compraRequest) {
        try {
            // El cliente desde C# solo enviará: idCliente, idEvento, entradasCompradas, metodoPago, idpuntoBonus
            Compra compraProcesada = compraBL.registrarCompra(compraRequest);

            // Retornamos HTTP 200 con todo el objeto calculado
            return Response.ok(compraProcesada).build();

        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error interno en el servidor: " + e.getMessage() + "\"}").build();

        }
    }


    @GET
    @Path("/listar/{idCliente}")
    public Response listarComprasPorCliente(@PathParam("idCliente") Integer idCliente) {
        try {
            // Asumiendo que tu método retorna un List<Compra> o similar
            List<Compra> listaCompras = comprasBL.listarComprasPorCliente(idCliente);

            // Si el cliente no existe o la lista viene nula, la inicializamos vacía por seguridad
            if (listaCompras == null) {
                return Response.ok("[]").build();
            }
            for(Compra c : listaCompras){
                if (c.getEvento().getFecha() != null) {
                    java.time.LocalDate fechaModerna = new java.sql.Timestamp(c.getEvento().getFecha().getTime())
                            .toLocalDateTime().toLocalDate();
                    // Si creaste el campo fechaModerna en Evento, lo asignas:
                    c.getEvento().setFechaModerna(fechaModerna);
                    c.getEvento().setFecha(null);
                }
                if (c.getEvento().getHora_inicio() != null) {
                    java.time.LocalTime horaInicioModerna = java.time.LocalTime.parse(c.getEvento().getHora_inicio().toString());
                    c.getEvento().setHoraInicioModerna(horaInicioModerna);
                    c.getEvento().setHora_inicio(null);
                }
                if (c.getEvento().getHora_fin() != null) {
                    java.time.LocalTime horaFinModerna = java.time.LocalTime.parse(c.getEvento().getHora_fin().toString());
                    c.getEvento().setHoraFinModerna(horaFinModerna);
                    c.getEvento().setHora_fin(null);
                }
            }


            // Retorna la lista con un estado 200 OK (GlassFish se encarga de convertirla a JSON)
            return Response.ok(listaCompras).build();

        } catch (Exception e) {
            // Tu catch está perfecto, mantiene el formato JSON para el manejo de errores
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error interno en el servidor: " + e.getMessage() + "\"}").build();
        }
    }


}
