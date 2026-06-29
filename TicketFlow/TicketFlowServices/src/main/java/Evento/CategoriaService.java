package Evento;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import pe.edu.pucp.ticketflow.evento.model.categoria_evento;
import pe.edu.pucp.ticketflow.ICategoria_EventoBL;
import pe.edu.pucp.ticketflow.impl.Categoria_EventoBLImpl;


@Path("CategoriaRS")
@Produces(MediaType.APPLICATION_JSON)
public class CategoriaService {

    // Dentro de CategoriaService.java...

    private final ICategoria_EventoBL categoriaBL = new Categoria_EventoBLImpl();

    @GET
    @Path("/listar")
    public Response listar() {
        try {
            // AHORA SÍ LLAMAMOS A LA BD REAL
            List<categoria_evento> categorias = categoriaBL.listarCategorias();
            return Response.ok(categorias).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error interno al listar categorías.")).build();
        }
    }
}