package Usuario;

import pe.edu.pucp.ticketflow.IAnfitrionBL;
import pe.edu.pucp.ticketflow.impl.AnfitrionBLImpl;
import pe.edu.pucp.ticketflow.usuario.model.Anfitrion;
import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.compra.model.Compra;
import pe.edu.pucp.ticketflow.pago.model.Pago;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import Evento.EventoDTO;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
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

    @PUT
    @Path("/{id}/datos-empresa")
    public Response editarDatosEmpresa(@PathParam("id") Integer idAnfitrion, Anfitrion anfitrion) {
        try {
            Integer idBanco = null;

            if (anfitrion.getBanco() != null) {
                idBanco = anfitrion.getBanco().getId();
            }

            anfitrionBL.actualizarDatosEmpresa(
                    idAnfitrion,
                    anfitrion.getRazonSocial(),
                    anfitrion.getRuc(),
                    anfitrion.getCuentaBancaria(),
                    idBanco
            );

            return Response.ok(Map.of("mensaje", "Datos de empresa actualizados correctamente.")).build();

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

    @GET
    @Path("/{id}/mis-eventos")
    public Response verMisEventos(@PathParam("id") Integer idAnfitrion) {
        try {
            // Asumiendo que crearás este método en tu BL y DAO
            List<Evento> eventos = anfitrionBL.verEventosPorAnfitrion(idAnfitrion);
            List<EventoDTO> eventosDTO = new ArrayList<>();

            for (Evento e : eventos) {
                eventosDTO.add(convertirDTO(e)); // Asegúrate de copiar el método convertirDTO de EventoService a esta clase
            }

            return Response.ok(eventosDTO).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Map.of("error", e.getMessage())).build();
        }
    }

    // Agrega el mismo método convertirDTO que tienes en EventoService.java
    private EventoDTO convertirDTO(Evento e) {
        EventoDTO dto = new EventoDTO();
        dto.idEvento = e.getIdEvento();
        dto.titulo = e.getTitulo();
        dto.descripcion = e.getDescripcion();
        dto.capacidad_entradas = e.getCapacidad_entradas();
        dto.idAnfitrion = e.getIdAnfitrion();
        dto.fecha = e.getFecha();
        dto.hora_inicio = e.getHora_inicio();
        dto.hora_fin = e.getHora_fin();
        dto.ubicacion = e.getUbicacion();
        dto.nombre_establecimiento = e.getNombre_establecimiento();
        dto.precio = e.getPrecio();
        // Cuidado aquí: asegúrate de que getCategoria() no sea nulo antes de llamar a sus métodos
        if(e.getCategoria() != null) {
            dto.idCategoria = e.getCategoria().getIdCategoria_evento();
            dto.categoria = e.getCategoria().getNombre();
        }
        dto.setImg(e.getImg());
        return dto;
    }

}