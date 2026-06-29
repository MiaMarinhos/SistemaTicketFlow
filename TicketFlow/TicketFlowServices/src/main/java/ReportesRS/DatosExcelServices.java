package ReportesRS;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.IAdministradorBL;
import pe.edu.pucp.ticketflow.impl.AdministradorBLImpl;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Path("DatosExcelRS")
public class DatosExcelServices {

    private static final String XLSX_TYPE =
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private final IAdministradorBL reportesBL;

    public DatosExcelServices() {
        this.reportesBL = new AdministradorBLImpl();
    }

    @GET
    @Path("/fidelizacion/excel")
    @Produces(XLSX_TYPE)
    public Response generarExcelFidelizacion() {
        try {
            List<Object[]> datos = reportesBL.generarReporteFidelizacion();

            if (datos == null || datos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontraron datos de fidelización.")
                        .build();
            }

            String[] cabeceras = {"Usuario", "Puntos Acumulados", "Puntos Canjeados"};
            byte[] excel = construirExcel("Fidelizacion", cabeceras, datos);

            return Response.ok(excel)
                    .type(XLSX_TYPE)
                    .header("Content-Disposition",
                            "attachment; filename=datos_fidelizacion.xlsx")
                    .build();

        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error de negocio: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Error al generar excel: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/ocupacion/excel")
    @Produces(XLSX_TYPE)
    public Response generarExcelOcupacion() {
        try {
            List<Object[]> datos = reportesBL.generarReporteOcupacionEventos();

            if (datos == null || datos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontraron datos de ocupacion.")
                        .build();
            }

            String[] cabeceras = {"Evento", "Capacidad", "Entradas Vendidas", "Porcentaje de Ocupacion"};
            byte[] excel = construirExcel("Ocupacion", cabeceras, datos);

            return Response.ok(excel)
                    .type(XLSX_TYPE)
                    .header("Content-Disposition",
                            "attachment; filename=datos_ocupacion.xlsx")
                    .build();

        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error de negocio: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Error al generar excel: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/ventas/excel")
    @Produces(XLSX_TYPE)
    public Response generarExcelVentas(
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

            String[] cabeceras = {"Categoria", "Cantidad Vendida", "Total"};
            byte[] excel = construirExcel("Ventas", cabeceras, datos);

            return Response.ok(excel)
                    .type(XLSX_TYPE)
                    .header("Content-Disposition",
                            "attachment; filename=datos_ventas.xlsx")
                    .build();

        } catch (BusinessLogicException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error de negocio: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Error al generar excel: " + e.getMessage())
                    .build();
        }
    }

    private byte[] construirExcel(String nombreHoja, String[] cabeceras, List<Object[]> datos)
            throws Exception {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(nombreHoja);

            CellStyle estiloCabecera = workbook.createCellStyle();
            Font fuenteCabecera = workbook.createFont();
            fuenteCabecera.setBold(true);
            estiloCabecera.setFont(fuenteCabecera);

            // Fila 0: cabeceras
            Row filaCabecera = sheet.createRow(0);
            for (int c = 0; c < cabeceras.length; c++) {
                Cell celda = filaCabecera.createCell(c);
                celda.setCellValue(cabeceras[c]);
                celda.setCellStyle(estiloCabecera);
            }

            // Filas de datos
            int numFila = 1;
            for (Object[] fila : datos) {
                Row filaExcel = sheet.createRow(numFila++);
                for (int c = 0; c < fila.length; c++) {
                    asignarValor(filaExcel.createCell(c), fila[c]);
                }
            }

            // Autoajustar ancho de columnas
            for (int c = 0; c < cabeceras.length; c++) {
                sheet.autoSizeColumn(c);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    // Escribe el valor según su tipo: números como número, fechas como fecha, resto como texto
    private void asignarValor(Cell celda, Object valor) {
        if (valor == null) {
            celda.setBlank();
        } else if (valor instanceof Number) {
            celda.setCellValue(((Number) valor).doubleValue());
        } else if (valor instanceof java.util.Date) {
            celda.setCellValue((java.util.Date) valor);
        } else if (valor instanceof Boolean) {
            celda.setCellValue((Boolean) valor);
        } else {
            celda.setCellValue(String.valueOf(valor));
        }
    }
}