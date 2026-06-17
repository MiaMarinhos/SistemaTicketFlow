package Evento;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.ticketflow.IEventoBL;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.ticketflow.evento.model.EstadoEvento;
import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.impl.EventoBLImpl;

import java.util.List;
import java.util.Map;

@Path("EventoRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class EventoService {
    private final IEventoBL eventoBL;

    public EventoService(){
        eventoBL = new EventoBLImpl();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarEventos() {
        try{
            List<Evento> eventos = eventoBL.verTodosLosEventos();
            return Response.ok(eventos).build();
        }
        catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", "Error interno.")).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEventoPorId(@PathParam("id") Integer idEvento) {
        try{
            Evento evento = eventoBL.mostrarEvento(idEvento);

            if (evento == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("mensaje", "Evento no encontrado."))
                        .build();
            }

            return Response.ok(evento).build();
        }
        catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", "Error interno.")).build();
        }
    }

    @POST
    public Response registrarEvento(Evento evento) {
        try{
            eventoBL.crearEvento(evento);

            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("mensaje", "Evento creado"))
                    .build();
        }
        catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", "Error interno.")).build();
        }
    }

    @PUT
    @Path("{id}")
    public Response actualizarEvento(@PathParam("id") Integer idEvento,
                                       Evento evento) {
        try{
            evento = eventoBL.editarEvento(evento, idEvento);

            if (evento == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("mensaje", "Evento no encontrado."))
                        .build();
            }
            return Response.ok(evento).build();
        }
        catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", "Error interno.")).build();
        }
    }

    @PUT
    @Path("{id}/ocultar")
    public Response ocultarEvento(@PathParam("id") Integer idEvento) {
        try{

            Evento evento = eventoBL.mostrarEvento(idEvento);
            EstadoEvento estadoEvento = new EstadoEvento(0,"Desactivado");
            evento.setEstadoEvento(estadoEvento);
            evento = eventoBL.editarEvento(evento, idEvento);
            if (evento == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("mensaje", "Evento no encontrado."))
                        .build();
            }
            return Response.ok(evento).build();
        }
        catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", "Error interno.")).build();
        }
    }
}
