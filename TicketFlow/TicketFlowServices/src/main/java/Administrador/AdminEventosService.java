package Administrador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.evento.model.categoria_evento;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.impl.AdministradorBLImpl;
import pe.edu.pucp.ticketflow.IAdministradorBL;

import java.util.List;
import java.util.Map;

@Path("AdminEventos")
public class AdminEventosService {

    private final IAdministradorBL administradorBL;

    public AdminEventosService() {
        administradorBL = new AdministradorBLImpl();
    }

    @GET
    @Path("listar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarEventos() {

        try {

            List<Evento> eventos = administradorBL.listarEventos();

            return Response.ok(eventos).build();

        } catch (BusinessLogicException e) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("aprobar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response aprobarEvento(@PathParam("id") Integer id) {
        try {
            Evento evento = administradorBL.aprobarEvento(id);
            return Response.ok(evento).build();

        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("rechazar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response rechazarEvento(@PathParam("id") Integer id) {
        try {
            Evento evento = administradorBL.rechazarEvento(id);
            return Response.ok(evento).build();

        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("filtrar/estado/{idEstadoEvento}")
    @Produces(MediaType.APPLICATION_JSON)
        public Response filtrarEventosPorEstado(@PathParam("idEstadoEvento") Integer idEstadoEvento) {
        try {
            List<Evento> eventos = administradorBL.filtrarEventosPorEstado(idEstadoEvento);

            return Response.ok(eventos).build();

        } catch (BusinessLogicException e) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Ocurrió un error inesperado: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("detalle/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response detalleEvento(@PathParam("id") Integer id) {
        try {
            Evento evento = administradorBL.detalleEvento(id);

            return Response.ok(evento).build();

        } catch (BusinessLogicException e) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Ocurrió un error inesperado: " + e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("actualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarEvento(
            @PathParam("id") Integer id,
            Evento evento) {
        try {
            evento.setIdEvento(id);

            Evento actualizado = administradorBL.editarEvento(evento);

            return Response.ok(actualizado).build();

        } catch (BusinessLogicException e) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Ocurrió un error inesperado: " + e.getMessage()))
                    .build();
        }
    }
    @PUT
    @Path("eliminar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarEvento(@PathParam("id") Integer id) {
        try {
            Evento evento = administradorBL.eliminarEvento(id);

            return Response.ok(evento).build();

        } catch (BusinessLogicException e) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Ocurrió un error inesperado: " + e.getMessage()))
                    .build();
        }
    }
    @POST
    @Path("registrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarEvento(Evento evento) {
        try {
            Evento creado = administradorBL.registrarEvento(evento);

            return Response.status(Response.Status.CREATED)
                    .entity(creado)
                    .build();

        } catch (BusinessLogicException e) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Ocurrió un error inesperado: " + e.getMessage()))
                    .build();
        }
    }
    @GET
    @Path("categorias")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarCategoriasEvento() {
        try {
            List<categoria_evento> categorias = administradorBL.listarCategoriasEvento();

            return Response.ok(categorias).build();

        } catch (BusinessLogicException e) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Ocurrió un error inesperado: " + e.getMessage()))
                    .build();
        }
    }
}