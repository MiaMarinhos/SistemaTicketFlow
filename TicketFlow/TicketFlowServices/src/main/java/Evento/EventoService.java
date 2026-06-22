package Evento;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.ticketflow.IEventoBL;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import jakarta.ws.rs.core.MediaType;
import pe.edu.pucp.ticketflow.evento.model.EstadoEvento;
import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.impl.EventoBLImpl;


import java.util.ArrayList;
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

        try {
            List<Evento> eventos = eventoBL.verTodosLosEventos();

            List<EventoDTO> eventosDTO = new ArrayList<>();

            for (Evento e : eventos) {
                eventosDTO.add(convertirDTO(e));
            }

            System.out.println("EVENTOS EN SERVICE: " + eventosDTO.size());

            return Response.ok(eventosDTO).build();

        } catch (Exception e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error interno"))
                    .build();
        }

    }

    @GET
    @Path("/Filtrar/{categoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ListarEventosPorCategoria(@PathParam("categoria") String categoria) throws BusinessLogicException {

        try {
            List<Evento> eventos = eventoBL.verTodosLosEventosPorCategoria(categoria);

            List<EventoDTO> eventosDTO = new ArrayList<>();

            for (Evento e : eventos) {
                eventosDTO.add(convertirDTO(e));
            }

            System.out.println("EVENTOS EN SERVICE: " + eventosDTO.size());

            return Response.ok(eventosDTO).build();

        } catch (Exception e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error en servicio Filtrar por Categoria"))
                    .build();
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

            return Response.ok(convertirDTO(evento)).build();
        }
        catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", "Error interno.")).build();
        }
    }
    @GET
    @Path("/detalle/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verDetalleEvento(@PathParam("id") Integer idEvento) {
        try{
            Evento evento = eventoBL.verDetalleEvento(idEvento);

            if (evento == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("mensaje", "Evento no encontrado."))
                        .build();
            }
            //cambiar direcatamente las fechas y horas aqui
            if (evento.getFecha() != null) {
                java.time.LocalDate fechaModerna = new java.sql.Timestamp(evento.getFecha().getTime())
                        .toLocalDateTime().toLocalDate();
                // Si creaste el campo fechaModerna en Evento, lo asignas:
                evento.setFechaModerna(fechaModerna);
                evento.setFecha(null);
            }
            if (evento.getHora_inicio() != null) {
                java.time.LocalTime horaInicioModerna = java.time.LocalTime.parse(evento.getHora_inicio().toString());
                evento.setHoraInicioModerna(horaInicioModerna);
                evento.setHora_inicio(null);
            }
            if (evento.getHora_fin() != null) {
                java.time.LocalTime horaFinModerna = java.time.LocalTime.parse(evento.getHora_fin().toString());
                evento.setHoraFinModerna(horaFinModerna);
                evento.setHora_fin(null);
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

            List<Evento> eventos = eventoBL.verTodosLosEventos();

            return Response.ok(
                    Map.of("cantidad", eventos.size())
            ).build();
            //return Response.ok(evento).build();
        }
        catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", "Error interno.")).build();
        }
    }

    private EventoDTO convertirDTO(Evento e) {

        EventoDTO dto = new EventoDTO();

        dto.idEvento = e.getIdEvento();
        dto.titulo = e.getTitulo();
        dto.descripcion = e.getDescripcion();
        dto.capacidad_entradas = e.getCapacidad_entradas();

        dto.fecha = (e.getFecha() != null)
                ? e.getFecha().toString()
                : null;

        dto.hora_inicio = (e.getHora_inicio() != null)
                ? e.getHora_inicio().toString()
                : null;

        dto.hora_fin = (e.getHora_fin() != null)
                ? e.getHora_fin().toString()
                : null;

        dto.ubicacion = e.getUbicacion();
        dto.nombre_establecimiento = e.getNombre_establecimiento();
        dto.precio = e.getPrecio();
        dto.idCategoria = e.getCategoria().getIdCategoria_evento();
        dto.categoria = e.getCategoria().getNombre();
        return dto;
    }
}
