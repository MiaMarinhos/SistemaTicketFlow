package Administrador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.ticketflow.IAdministradorBL;
import pe.edu.pucp.ticketflow.IUsuarioBL;
import pe.edu.pucp.ticketflow.administrador.model.Administrador;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.impl.AdministradorBLImpl;
import pe.edu.pucp.ticketflow.ubicacion.model.Distrito;
import pe.edu.pucp.ticketflow.usuario.model.*;

import java.util.List;
import java.util.Map;

@Path("AdminUsuarios")
@Produces(MediaType.APPLICATION_JSON)
public class AdminUsuariosService {

    private final IAdministradorBL administradorBL;

    public AdminUsuariosService() {
        this.administradorBL = new AdministradorBLImpl();
    }

    @GET
    @Path("listar")
    public Response listarUsuarios() {
        try {
            List<Usuario> usuarios = administradorBL.listarUsuarios();

            return Response.ok(usuarios).build();
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

    @GET
    @Path("{id}")
    public Response obtenerUsuario(@PathParam("id") Integer id) {
        try {
            Usuario usuario = administradorBL.buscarUsuarioPorId(id);

            return Response.ok(usuario).build();
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

    @PUT
    @Path("actualizar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarUsuario(
            @PathParam("id") Integer id,
            Cliente usuario) {

        try {
            usuario.setIdUsuario(id);

            Usuario actualizado = administradorBL.editarUsuario(usuario);

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
    @Path("/bloquear/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response bloquearUsuario(@PathParam("id") Integer id) {
        try {
            Usuario usuario = administradorBL.bloquearUsuario(id);
            return Response.ok(usuario).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al bloquear usuario: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/desbloquear/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response desbloquearUsuario(@PathParam("id") Integer id) {
        try {
            Usuario usuario = administradorBL.desbloquearUsuario(id);
            return Response.ok(usuario).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al desbloquear usuario: " + e.getMessage())
                    .build();
        }
    }
    //FILTROS:
    @GET
    @Path("filtrar/tipo/{idTipoUsuario}")
    public Response filtrarUsuariosPorTipo(@PathParam("idTipoUsuario") Integer idTipoUsuario) {
        try {
            List<Usuario> usuarios = administradorBL.filtrarUsuariosPorTipo(idTipoUsuario);

            return Response.ok(usuarios).build();
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
    @GET
    @Path("filtrar/estado/{idEstado}")
    public Response filtrarUsuariosPorEstado(@PathParam("idEstado") Integer idEstado) {
        try {
            List<Usuario> usuarios = administradorBL.filtrarUsuariosPorEstado(idEstado);

            return Response.ok(usuarios).build();
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

    @POST
    @Path("registrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(UsuarioDTO dto) {
        try {
            Usuario usuario;

            switch (dto.getIdTipoUsuario()) {
                case 1:
                    usuario = new Cliente();
                    break;
                case 2:
                    usuario = new Anfitrion();
                    break;
                case 3:
                    usuario = new Administrador();
                    break;
                default:
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(Map.of("error", "Tipo de usuario no válido"))
                            .build();
            }

            usuario.setDni(dto.getDni());
            usuario.setNombre(dto.getNombre());
            usuario.setApellidoPaterno(dto.getApellidoPaterno());
            usuario.setApellidoMaterno(dto.getApellidoMaterno());
            usuario.setTelefono(dto.getTelefono());
            usuario.setEdad(dto.getEdad());
            usuario.setCorreoElectronico(dto.getCorreoElectronico());
            usuario.setContrasena(dto.getContrasena());
            usuario.setFechaNacimiento(java.sql.Date.valueOf(dto.getFechaNacimiento()));

            Genero genero = new Genero();
            genero.setIdGenero(dto.getIdGenero());
            usuario.setGenero(genero);

            Distrito distrito = new Distrito();
            distrito.setIdDistrito(dto.getIdDistrito());
            usuario.setDistrito(distrito);
            usuario.setIdDistrito(dto.getIdDistrito());

            TipoUsuario tipo = new TipoUsuario();
            tipo.setIdTipoUsuario(dto.getIdTipoUsuario());
            usuario.setTipo(tipo);

            Usuario creado = administradorBL.registrarUsuario(usuario);

            return Response.status(Response.Status.CREATED)
                    .entity(creado)
                    .build();
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

    @GET
    @Path("generos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarGeneros() {
        try {
            List<Genero> generos = administradorBL.listarGeneros();

            return Response.ok(generos).build();
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
    @GET
    @Path("distritos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarDistritos() {
        try {
            List<Distrito> distritos = administradorBL.listarDistritos();

            return Response.ok(distritos).build();
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