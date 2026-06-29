package ReportesRS;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;

import pe.edu.pucp.ticketflow.IAdministradorBL;
import pe.edu.pucp.ticketflow.impl.AdministradorBLImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("ReporteRS")
public class ReporteServices {

    private final IAdministradorBL reportesBL;

    public ReporteServices(){
        this.reportesBL = new AdministradorBLImpl();
    }
    @GET
    @Path("/fidelizacion/pdf")
    @Produces("application/pdf")
    public Response generarReporteFidelizacion() {
        try {
            List<Object[]> datos = reportesBL.generarReporteFidelizacion();

            if (datos == null || datos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontraron datos de fidelización.")
                        .build();
            }

            // Convertir List<Object[]> a List<Map<String, Object>>
            List<Map<String, ?>> listaMapas = new ArrayList<>();
            for (Object[] fila : datos) {
                Map<String, Object> mapa = new HashMap<>();
                mapa.put("usuario",           fila[0]);
                mapa.put("puntos_acumulados", fila[1]);
                mapa.put("puntos_canjeados",  fila[2]);
                listaMapas.add(mapa);
            }

            java.io.InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("reportes/ReporteDeFidelizacion.jasper");

            if (inputStream == null) {
                return Response.serverError()
                        .entity("No se encontró reportes/ReporteDeFidelizacion.jasper en el classpath")
                        .build();
            }

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);

            JRMapCollectionDataSource dataSource =
                    new JRMapCollectionDataSource(listaMapas);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Fecha", LocalDate.now().toString());
            parametros.put("Cantidad", datos.size());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parametros,
                    dataSource
            );

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            return Response.ok(pdf)
                    .type("application/pdf")
                    .header("Content-Disposition", "attachment; filename=reporte_fidelizacion.pdf")
                    .build();

        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error de negocio: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Error al generar reporte: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/ocupacion/pdf")
    @Produces("application/pdf")
    public Response generarReporteOcupacion() {
        try {
            List<Object[]> datos = reportesBL.generarReporteOcupacionEventos();

            if (datos == null || datos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontraron datos de ocupacion.")
                        .build();
            }

            // Convertir List<Object[]> a List<Map<String, Object>>
            List<Map<String, ?>> listaMapas = new ArrayList<>();
            for (Object[] fila : datos) {
                Map<String, Object> mapa = new HashMap<>();
                mapa.put("Evento",fila[0]);
                mapa.put("Capacidad",fila[1]);
                mapa.put("Entradas Vendidas",fila[2]);
                mapa.put("Porcentaje de Ocupacion",fila[3]);
                listaMapas.add(mapa);
            }

            java.io.InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("reportes/ReporteOcupacion.jasper");

            if (inputStream == null) {
                return Response.serverError()
                        .entity("No se encontró reportes/ReporteOcupacion.jasper en el classpath")
                        .build();
            }

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);

            JRMapCollectionDataSource dataSource =
                    new JRMapCollectionDataSource(listaMapas);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Fecha", LocalDate.now().toString());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parametros,
                    dataSource
            );

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            return Response.ok(pdf)
                    .type("application/pdf")
                    .header("Content-Disposition", "attachment; filename=reporte_de_ocupacion.pdf")
                    .build();

        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error de negocio: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Error al generar reporte: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/ventas/pdf")
    @Produces("application/pdf")
    public Response generarReporteVentas(
            @QueryParam("fechaInicio") String fechaInicioStr,
            @QueryParam("fechaFin")    String fechaFinStr) {
        try {
            java.util.Date fechaInicio = java.sql.Date.valueOf(fechaInicioStr); // espera yyyy-MM-dd
            java.util.Date fechaFin    = java.sql.Date.valueOf(fechaFinStr);

            List<Object[]> datos = reportesBL.generarReporteVentas(fechaInicio, fechaFin, 0);

            if (datos == null || datos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontraron datos de ventas.")
                        .build();
            }

            // Convertir List<Object[]> a List<Map<String, Object>>
            List<Map<String, ?>> listaMapas = new ArrayList<>();
            for (Object[] fila : datos) {
                Map<String, Object> mapa = new HashMap<>();
                mapa.put("Categoria",fila[0]);
                mapa.put("Cantidad Vendida",fila[1]);
                mapa.put("Total",fila[2]);
                listaMapas.add(mapa);
            }

            java.io.InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("reportes/ReporteVentasFiltradas.jasper");

            if (inputStream == null) {
                return Response.serverError()
                        .entity("No se encontró reportes/ReporteVentasFiltradas.jasper en el classpath")
                        .build();
            }

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);

            JRMapCollectionDataSource dataSource =
                    new JRMapCollectionDataSource(listaMapas);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Fecha", LocalDate.now().toString());
            parametros.put("FechaInicio", fechaInicioStr);
            parametros.put("FechaFin", fechaFinStr);

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parametros,
                    dataSource
            );

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            return Response.ok(pdf)
                    .type("application/pdf")
                    .header("Content-Disposition", "attachment; filename=ReporteVentasFiltradas.pdf")
                    .build();

        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error de negocio: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Error al generar reporte: " + e.getMessage())
                    .build();
        }
    }
}
