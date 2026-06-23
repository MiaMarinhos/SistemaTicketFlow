package pe.edu.pucp.ticketflow.impl;

import pe.edu.pucp.ticketflow.*;
import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.compra.model.Compra;
import pe.edu.pucp.ticketflow.compra.model.EstadoCompra;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;

import java.time.LocalDate;
import java.time.LocalTime;

public class ComprasBLImpl implements IComprasBL {

    private ICompraDAO compraDAO = new CompraDAOImpl();
    private IEstadoComprasDAO estadoComprasDAO = new EstadoComprasDAOImpl();
    private IClienteDAO clienteDAO = new ClienteDAOImpl();
    private IEventoDAO eventoDAO = new EventoDAOImpl();
    @Override
    public Compra registrarCompra(Compra compraReq) throws BusinessLogicException {
        try {
            // 1. Obtener los datos reales del evento desde la BD (Seguridad)
            Evento evento = eventoDAO.read(compraReq.getIdEvento());
            if (evento == null) {
                throw new IllegalArgumentException("El evento especificado no existe.");
            }
            if (evento.getCapacidad_entradas() < compraReq.getEntradasCompradas()) {
                throw new IllegalStateException("No hay suficientes entradas disponibles.");
            }

            // 3. Generar ID de Comprobante Automático por Computadora (9 dígitos)
            // Prefijo 26 (Año 2026) + 7 dígitos aleatorios únicos
            int min = 1000000;
            int max = 9999999;
            int randomDigits = (int)(Math.random() * (max - min + 1) + min);
            int idComprobante = Integer.parseInt("26" + randomDigits);
            compraReq.setIdCompra(idComprobante);

            // 4. Asignar fecha y hora actuales para auditoría interna
            compraReq.setFechaCompra(LocalDate.now());
            compraReq.setHoraCompra(LocalTime.now());
            // 2 de APROBADO
            compraReq.setIdEstado(2);
            // 5. Enviar al DAO para guardar en la Base de Datos
            compraDAO.create(compraReq);

            return compraReq;
        }
        catch (Exception ex){
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException)ex;
            } else {
                throw new BusinessLogicException(ex);
            }
        }
    }
}
