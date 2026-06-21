package Administrador;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.ticketflow.IAdministradorBL;
import pe.edu.pucp.ticketflow.administrador.model.Administrador;
import pe.edu.pucp.ticketflow.impl.AdministradorBLImpl;

import java.util.Map;

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
}
