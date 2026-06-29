package pe.edu.pucp.ticketflow.impl;

import jakarta.ejb.EJB;
import pe.edu.pucp.ticketflow.*;
import pe.edu.pucp.ticketflow.Infrastructure.EmailService;
import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.compra.model.Compra;
import pe.edu.pucp.ticketflow.compra.model.EstadoCompra;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.usuario.model.Cliente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;



public class ComprasBLImpl implements IComprasBL {

    private EmailService emailService=new EmailService();
    private ICompraDAO compraDAO = new CompraDAOImpl();
    private IEstadoComprasDAO estadoComprasDAO = new EstadoComprasDAOImpl();
    private IClienteDAO clienteDAO = new ClienteDAOImpl();
    private IEventoDAO eventoDAO = new EventoDAOImpl();
    @Override
    public Compra registrarCompra(Compra compraReq) throws BusinessLogicException {
        try {
            // 1. Obtener los datos reales del evento desde la BD (Seguridad)
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


            System.out.println("===== Leyendo cliente de la compra =====");

            compraReq.setCliente(clienteDAO.read(compraReq.getIdCliente()));

            System.out.println("===== Leyendo evento de la compra =====");

            compraReq.setEvento(eventoDAO.read(compraReq.getIdEvento()));

            System.out.println("=====generando formato de correo =====");

            String htmlCompra = """
<html>
<body style="font-family: Arial; background:#f4f6f8; padding:20px;">

    <div style="max-width:600px; margin:auto; background:white; border-radius:10px; overflow:hidden;">

        <div style="background:#27ae60; padding:20px; text-align:center;">
            <h2 style="color:white; margin:0;">🎉 ¡Gracias por tu compra!</h2>
            <p style="color:#ecf0f1;">Tu compra ha sido registrada exitosamente.</p>
        </div>

        <div style="padding:25px;">

            <p>Hola <b>%s %s</b>,</p>

            <p>Gracias por confiar en <b>TicketFlow</b>. A continuación encontrarás la constancia de tu compra:</p>

            <div style="background:#ecf0f1; padding:15px; border-radius:8px;">

                <p><b>🧾 ID de Compra:</b> %d</p>
                <p><b>🎟 Evento:</b> %s</p>
                <p><b>🎫 Entradas:</b> %d</p>
                <p><b>📅 Fecha de compra:</b> %s</p>
                <p><b>🕒 Hora de compra:</b> %s</p>
                <p><b>💳 Método de pago:</b> %s</p>
                <p><b>💰 Subtotal:</b> S/ %.2f</p>
                <p><b>💵 Total pagado:</b> <span style="color:#27ae60;"><b>S/ %.2f</b></span></p>
            </div>

            <p style="margin-top:15px; color:#555;">
                Conserva este correo como comprobante de tu compra.
            </p>

            <p style="color:#555;">
                Antes del evento recibirás recordatorios automáticos para que no te pierdas la función.
            </p>

        </div>

        <div style="background:#f1f1f1; text-align:center; padding:10px;">
            <small>© TicketFlow - Gracias por elegirnos</small>
        </div>

    </div>

</body>
</html>
""".formatted(
                    compraReq.getCliente().getNombre(),
                    compraReq.getCliente().getApellidoPaterno(),
                    compraReq.getIdCompra(),
                    compraReq.getEvento().getTitulo(),
                    compraReq.getEntradasCompradas(),
                    compraReq.getFechaCompraS(),
                    compraReq.getHoraCompraS(),
                    compraReq.getMetodoPago(),
                    compraReq.getMontoParcial(),
                    compraReq.getMontoTotal()
            );

            System.out.println("===== Enviando correo =====");

            emailService.enviarCorreoAsync(
                    compraReq.getCliente().getCorreoElectronico(),
                    "TICKET FLOW - Compra exitosa",
                    htmlCompra
            );


            System.out.println("===== Correo enviado =====");

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

    @Override
    public List<Compra> listarComprasPorCliente(Integer idCliente) throws BusinessLogicException {
        try {
            List<Compra> compras = new ArrayList<>();
            compras = compraDAO.listarComprasPorCliente(idCliente);
            for(Compra c : compras){
                Evento evento = eventoDAO.read(c.getIdEvento());
                c.setEvento(evento);
                Cliente cliente = clienteDAO.read(c.getIdCliente());
                c.setCliente(cliente);
                EstadoCompra estado = estadoComprasDAO.read(c.getIdEstado());
                c.setEstado(estado);
            }

            return compras;
        }
        catch (Exception ex){
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException)ex;
            } else {
                throw new BusinessLogicException(ex);
            }
        }
    }
    @Override
    public List<Compra>ListarComprasDeEvento(int idEvento)throws BusinessLogicException{
        try {
            return compraDAO.ListarComprasDeEvento(idEvento);
        }
        catch (Exception ex){
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException)ex;
            } else {
                throw new BusinessLogicException(ex);
            }
        }
    }
    @Override
    public void marcarCompraComoEnviado(int idCompra)throws BusinessLogicException{
        try{
            compraDAO.marcarCompraComoEnviado(idCompra);
        }
        catch (Exception ex){
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException)ex;
            } else {
                throw new BusinessLogicException(ex);
            }

        }
    }

    @Override
    public void marcarCompraComoEnviado2(int idCompra)throws BusinessLogicException{
        try{
            compraDAO.marcarCompraComoEnviado2(idCompra);
        }
        catch (Exception ex){
            if (ex instanceof BusinessLogicException) {
                throw (BusinessLogicException)ex;
            } else {
                throw new BusinessLogicException(ex);
            }

        }
    }

    @Override
    public void validarIngresoCliente(int idCompra) throws BusinessLogicException{

        try {
            compraDAO.validarIngreso(idCompra); // Llama al DAO
        }catch (Exception ex){
                if (ex instanceof BusinessLogicException) {
                    throw (BusinessLogicException)ex;
                } else {
                    throw new BusinessLogicException(ex);
                }

            }
    }
}
