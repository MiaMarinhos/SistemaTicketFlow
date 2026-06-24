package Administrador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import pe.edu.pucp.ticketflow.evento.model.Evento;
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
}