package Administrador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.ticketflow.IAdministradorBL;
import pe.edu.pucp.ticketflow.administrador.model.Administrador;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.impl.AdministradorBLImpl;

import java.util.Map;
@Path("AdminBasico")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminBasicoService {
    private final IAdministradorBL administradorBL;

    public AdminBasicoService() {
        this.administradorBL = new AdministradorBLImpl();
    }

    @POST
    @Path("registrar")
    public Response registrarAdministrador(Administrador administrador) {
        try {
            // El BL devuelve el objeto administrador
            Administrador registrado = administradorBL.registrarAdministrador(administrador);

            return Response.status(Response.Status.CREATED)
                    .entity(registrado) // Devolvemos el objeto completo como JSON
                    .build();
        }
        catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            // Error de validación de negocio (ej: datos duplicados o inválidos)
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Ocurrió un error inesperado: " + e.getMessage()))
                    .build();
        }
    }
    // http://localhost:8080/TicketFlow/api/AdminUsuarios/1
    //PERFIL ADMINISTRADOR - MOSTRAR INFORMACION
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerAdministrador(@PathParam("id") Integer id) {

        try {

            Administrador administrador =
                    administradorBL.buscarAdministradorPorId(id);

            return Response.status(Response.Status.OK)
                    .entity(administrador)
                    .build();

        }
        catch (BusinessLogicException e) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of(
                            "error",
                            e.getMessage()
                    ))
                    .build();
        }
        catch (Exception e) {

            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of(
                            "error",
                            "Ocurrió un error inesperado: " + e.getMessage()
                    ))
                    .build();
        }
    }

    //PERFIL ADMINISTRADOR - EDITAR PERFIL
    @PUT
    @Path("actualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarAdministrador(
            @PathParam("id") Integer id,
            Administrador administrador) {

        try {
            Administrador actualizado =
                    administradorBL.actualizarAdministrador(administrador, id);

            return Response.ok(actualizado).build();
        }
        catch (BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Ocurrió un error inesperado: " + e.getMessage()))
                    .build();
        }
    }

}
