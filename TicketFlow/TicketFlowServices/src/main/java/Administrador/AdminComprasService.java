package Administrador;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import pe.edu.pucp.ticketflow.IAdministradorBL;
import pe.edu.pucp.ticketflow.impl.AdministradorBLImpl;
import pe.edu.pucp.ticketflow.compra.model.Compra;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;

@Path("/AdminCompras")
public class AdminComprasService {

    private final IAdministradorBL administradorBL;

    public AdminComprasService() {
        this.administradorBL = new AdministradorBLImpl();
    }

    @GET
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarCompras() {
        try {
            List<Compra> compras = administradorBL.listarCompras();
            return Response.ok(compras).build();

        } catch (BusinessLogicException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();

        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al listar compras")
                    .build();
        }
    }

    @GET
    @Path("filtrar/estado/{idEstado}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtrarComprasPorEstado(@PathParam("idEstado") Integer idEstado) {
        try {
            List<Compra> compras = administradorBL.filtrarComprasPorEstado(idEstado);
            return Response.ok(compras).build();

        } catch (BusinessLogicException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();

        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al filtrar compras por estado")
                    .build();
        }
    }

    @GET
    @Path("/detalle/{idCompra}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response detalleCompra(@PathParam("idCompra") Integer idCompra) {
        try {
            Compra compra = administradorBL.detalleCompra(idCompra);
            return Response.ok(compra).build();

        } catch (BusinessLogicException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();

        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener el detalle de la compra")
                    .build();
        }
    }
}