package Usuario;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.ticketflow.IClienteBL;
import pe.edu.pucp.ticketflow.IUsuarioBL;
import pe.edu.pucp.ticketflow.impl.ClienteBLImpl;
import pe.edu.pucp.ticketflow.impl.UsuarioBLImpl;
import pe.edu.pucp.ticketflow.solicitud.model.Solicitud;
import pe.edu.pucp.ticketflow.usuario.model.Cliente;
import pe.edu.pucp.ticketflow.usuario.model.Genero;

import java.util.Map;

@Path("ClienteRS")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteService {

    private final IUsuarioBL usuarioBL;
    private final IClienteBL clienteBL;

    public ClienteService() {
        this.usuarioBL = new UsuarioBLImpl();
        this.clienteBL = new ClienteBLImpl();
    }

    @POST
    @Path("/Register")
    public Response RegistroCliente(Cliente cliente) {
        try {
            String mensaje = usuarioBL.registrarCliente(cliente);

            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("mensaje", mensaje))
                    .build();

        } catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error interno."))
                    .build();
        }
    }

    @GET
    @Path("/perfil/{idUsuario}")
    public Response verPerfilCliente(@PathParam("idUsuario") Integer idUsuario) {
        try {
            Cliente cliente = clienteBL.buscarClientePorId(idUsuario);

            return Response.ok(cliente).build();

        } catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error interno: " + e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/perfil/{idUsuario}")
    public Response actualizarPerfilCliente(@PathParam("idUsuario") Integer idUsuario,
                                            ClientePerfilUpdateDTO dto) {
        try {
            Cliente actual = clienteBL.buscarClientePorId(idUsuario);

            actual.setDni(elegir(dto.getDni(), actual.getDni()));
            actual.setNombre(elegir(dto.getNombre(), actual.getNombre()));
            actual.setApellidoPaterno(elegir(dto.getApellidoPaterno(), actual.getApellidoPaterno()));
            actual.setApellidoMaterno(elegir(dto.getApellidoMaterno(), actual.getApellidoMaterno()));
            actual.setTelefono(elegir(dto.getTelefono(), actual.getTelefono()));
            actual.setCorreoElectronico(elegir(dto.getCorreoElectronico(), actual.getCorreoElectronico()));

            // No mostramos contraseña en el formulario.
            // Si no llega una nueva, se conserva la actual.
            actual.setContrasena(elegir(dto.getContrasena(), actual.getContrasena()));

            if (dto.getEdad() > 0) {
                actual.setEdad(dto.getEdad());
            }

            if (dto.getIdDistrito() > 0) {
                actual.setIdDistrito(dto.getIdDistrito());
            }

            if (dto.getIdGenero() > 0) {
                Genero genero = actual.getGenero();

                if (genero == null) {
                    genero = new Genero();
                }

                genero.setIdGenero(dto.getIdGenero());
                actual.setGenero(genero);
            }


            Cliente actualizado = clienteBL.actualizarCliente(actual, idUsuario);

            return Response.ok(actualizado).build();

        } catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error interno: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/verPuntosBonus/{idUsuario}")
    public Response verPuntosBonusDelCliente(@PathParam("idUsuario") Integer idUsuario) {
        try {
            int pb = clienteBL.obtenerPuntosBonus(idUsuario);

            return Response.ok(pb).build();

        } catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Error interno: " + e.getMessage()))
                    .build();
        }
    }

    private String elegir(String nuevo, String actual) {
        return nuevo != null && !nuevo.isBlank() ? nuevo : actual;
    }

    @POST
    @Path("enviarSolicitud")
    @Produces(MediaType.APPLICATION_JSON)
    public Response enviarSolicitud(Solicitud solicitud){
        try {
            usuarioBL.enviarSolicitud(solicitud);
            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("mensaje", "Solicitud enviada"))
                    .build();
        }catch (pe.edu.pucp.ticketflow.exception.BusinessLogicException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", e.getMessage())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage() != null
                            ? e.getMessage()
                            : "Error interno del servidor"))
                    .build();
        }
    }
}