package Administrador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

import pe.edu.pucp.ticketflow.IAdministradorBL;
import pe.edu.pucp.ticketflow.impl.AdministradorBLImpl;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.pago.model.Pago;

@Path("AdminPagos")
public class AdminPagosService {

    private final IAdministradorBL administradorBL;

    public AdminPagosService() {
        administradorBL = new AdministradorBLImpl();
    }

    @GET
    @Path("listar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPagos() {
        try {
            List<Pago> pagos = administradorBL.listarPagos();

            return Response.ok(pagos).build();

        } catch (BusinessLogicException e) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage() != null
                            ? e.getMessage()
                            : "Error interno del servidor"))
                    .build();
        }
    }
    @GET
    @Path("filtrar/estado/{idEstado}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtrarPagosPorEstado(@PathParam("idEstado") Integer idEstado) {
        try {
            List<Pago> pagos = administradorBL.filtrarPagosPorEstado(idEstado);

            return Response.ok(pagos).build();

        } catch (BusinessLogicException e) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage() != null
                            ? e.getMessage()
                            : "Error interno del servidor"))
                    .build();
        }
    }

    @GET
    @Path("filtrar/fecha/{fecha}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filtrarPagosPorFecha(@PathParam("fecha") String fecha) {
        try {
            List<Pago> pagos = administradorBL.filtrarPagosPorFecha(fecha);

            return Response.ok(pagos).build();

        } catch (BusinessLogicException e) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage() != null
                            ? e.getMessage()
                            : "Error interno del servidor"))
                    .build();
        }
    }

    @GET
    @Path("detalle/{idPago}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response detallePago(@PathParam("idPago") Integer idPago) {
        try {
            Pago pago = administradorBL.detallePago(idPago);

            return Response.ok(pago).build();

        } catch (BusinessLogicException e) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", e.getMessage() != null
                            ? e.getMessage()
                            : "Error interno del servidor"))
                    .build();
        }
    }

    @POST
    @Path("/generarPagosFinalizados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generarPagosEventosFinalizados() {
        try {
            int cantidad = administradorBL.generarPagosEventosFinalizados();

            return Response.ok("Pagos generados correctamente. Registros afectados: " + cantidad).build();

        } catch (BusinessLogicException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();

        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al generar pagos de eventos finalizados")
                    .build();
        }
    }

    @PUT
    @Path("/confirmarTransferencia/{idPago}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response confirmarTransferencia(
            @PathParam("idPago") Integer idPago,
            ConfirmarTransferenciaRequest request) {
        try {
            if (request == null) {
                return Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity("Debe enviar el comprobante de la transferencia.")
                        .build();
            }

            Pago pago = administradorBL.confirmarTransferenciaPago(
                    idPago,
                    request.getComprobante()
            );

            return Response.ok(pago).build();

        } catch (BusinessLogicException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();

        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al confirmar la transferencia bancaria")
                    .build();
        }
    }

    public static class ConfirmarTransferenciaRequest {

        private String comprobante;

        public String getComprobante() {
            return comprobante;
        }

        public void setComprobante(String comprobante) {
            this.comprobante = comprobante;
        }
    }
}