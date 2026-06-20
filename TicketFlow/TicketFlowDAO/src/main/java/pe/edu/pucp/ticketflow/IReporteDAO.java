package pe.edu.pucp.ticketflow;

import java.util.Date;
import java.util.List;

public interface IReporteDAO {

    List<Object[]> reporteVentas(Date fechaInicio, Date fechaFin, Integer idCategoria);
    List<Object[]> reporteFidelizacion();
    List<Object[]> reporteOcupacionEventos();
}