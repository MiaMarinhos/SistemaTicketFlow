package Administrador;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

import pe.edu.pucp.ticketflow.IAdministradorBL;
import pe.edu.pucp.ticketflow.impl.AdministradorBLImpl;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.solicitud.model.Solicitud;

@Path("AdminSolicitudes")
public class AdminSolicitudesService {

    private final IAdministradorBL administradorBL;

    public AdminSolicitudesService() {
        administradorBL = new AdministradorBLImpl();
    }

    @GET
    @Path("listar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarSolicitudes() {

        try {

            List<Solicitud> solicitudes =
                    administradorBL.listarSolicitudes();

            return Response.ok(solicitudes).build();

        } catch (BusinessLogicException e) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of(
                            "error",
                            e.getMessage()
                    ))
                    .build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of(
                            "error",
                            e.getMessage()
                    ))
                    .build();
        }
    }
}