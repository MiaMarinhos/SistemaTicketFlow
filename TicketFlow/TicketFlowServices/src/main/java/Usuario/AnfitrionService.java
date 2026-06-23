package Usuario;

import pe.edu.pucp.ticketflow.IAnfitrionBL;
import pe.edu.pucp.ticketflow.impl.AnfitrionBLImpl;
import pe.edu.pucp.ticketflow.usuario.model.Anfitrion;
import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.compra.model.Compra;
import pe.edu.pucp.ticketflow.pago.model.Pago;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/AnfitrionRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnfitrionService {

    private final IAnfitrionBL anfitrionBL;

    public AnfitrionService() {
        this.anfitrionBL = new AnfitrionBLImpl();
    }

    // ==========================================
    // 1. GESTIÓN DEL ANFITRIÓN
    // ==========================================

    @POST
    @Path("/registrar")
    public Response registrarAnfitrion(Anfitrion anfitrion) {
        try {
            Anfitrion nuevoAnfitrion = anfitrionBL.registrarAnfitrion(anfitrion);
            return Response.ok(nuevoAnfitrion).build();
        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarAnfitrion(@PathParam("id") Integer idAnfitrion) {
        try {
            Anfitrion anfitrion = anfitrionBL.buscarAnfitrionPorId(idAnfitrion);
            return Response.ok(anfitrion).build();
        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @PUT
    @Path("/editarPerfil")
    public Response editarPerfilAnfitrion(Anfitrion anfitrion) {
        try {
            anfitrionBL.editarPerfilAnfitrion(anfitrion);
            return Response.ok(Map.of("mensaje", "Perfil actualizado con éxito.")).build();
        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        }
    }

    // ==========================================
    // 2. GESTIÓN DE EVENTOS
    // ==========================================

    @POST
    @Path("/eventos/crear")
    public Response crearEvento(Evento evento) {
        try {
            Evento nuevoEvento = anfitrionBL.crearEvento(evento);
            return Response.ok(nuevoEvento).build();
        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @GET
    @Path("/eventos/{id}")
    public Response mostrarEvento(@PathParam("id") Integer idEvento) {
        try {
            Evento evento = anfitrionBL.mostrarEvento(idEvento);
            return Response.ok(evento).build();
        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(Map.of("error", e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @PUT
    @Path("/eventos/actualizar/{id}")
    public Response actualizarEvento(@PathParam("id") Integer idEvento, Evento evento) {
        try {
            Evento eventoActualizado = anfitrionBL.actualizarEvento(evento, idEvento);
            return Response.ok(eventoActualizado).build();
        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/eventos/eliminar/{id}")
    public Response eliminarEvento(@PathParam("id") Integer idEvento) {
        try {
            anfitrionBL.eliminarEvento(idEvento);
            return Response.ok(Map.of("mensaje", "Evento eliminado con éxito.")).build();
        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @GET
    @Path("/eventos/todos")
    public Response verTodosLosEventos() {
        try {
            List<Evento> eventos = anfitrionBL.verTodosLosEventos();
            return Response.ok(eventos).build();
        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        }
    }

    // ==========================================
    // 3. GESTIÓN DE COMPRAS Y PAGOS
    // ==========================================

    @GET
    @Path("/{id}/compras")
    public Response verComprasDeSusEventos(@PathParam("id") Integer idAnfitrion) {
        try {
            List<Compra> compras = anfitrionBL.verComprasDeSusEventos(idAnfitrion);
            return Response.ok(compras).build();
        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @GET
    @Path("/{id}/pagos")
    public Response verPagosDeSusEventos(@PathParam("id") Integer idAnfitrion) {
        try {
            List<Pago> pagos = anfitrionBL.verPagosDeSusEventos(idAnfitrion);
            return Response.ok(pagos).build();
        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        }
    }
}