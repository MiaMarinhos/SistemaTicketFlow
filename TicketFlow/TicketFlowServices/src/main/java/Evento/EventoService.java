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

            // 💡 ELIMINAMOS la conversión manual de fechas. Ya viajan como String.

            return Response.ok(evento).build();
        }
        catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", "Error interno.")).build();
        }
    }

    @GET
    @Path("/Buscar/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarEventoPorNombre(@PathParam("nombre") String nombre){
        try{
            List<Evento>eventos=eventoBL.buscarEventoPorNombre(nombre);
            List<EventoDTO> eventosDTO = new ArrayList<>();
            for(Evento e:eventos){
                eventosDTO.add(convertirDTO(e));
            }
            return  Response.ok(eventosDTO).build();
        }
        catch(pe.edu.pucp.ticketflow.exception.BusinessLogicException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        }
    }

    @POST
    public Response registrarEvento(Evento evento) {
        try{
            eventoBL.crearEvento(evento);

            // ¡AQUÍ ESTÁ LO QUE FALTABA! El retorno de éxito.
            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("mensaje", "Evento creado"))
                    .build();
        }
        catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            e.printStackTrace(); // Para ver el error en consola
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        }
        catch (Exception e) {
            e.printStackTrace(); // Para ver el error en consola
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
        dto.idAnfitrion = e.getIdAnfitrion();
        // 💡 Asignamos directamente los Strings
        dto.fecha = e.getFecha();
        dto.hora_inicio = e.getHora_inicio();
        dto.hora_fin = e.getHora_fin();

        dto.ubicacion = e.getUbicacion();
        dto.nombre_establecimiento = e.getNombre_establecimiento();
        dto.precio = e.getPrecio();
        dto.idCategoria = e.getCategoria().getIdCategoria_evento();
        dto.categoria = e.getCategoria().getNombre();
        dto.setImg(e.getImg());
        return dto;
    }
}