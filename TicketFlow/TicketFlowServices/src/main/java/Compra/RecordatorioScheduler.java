package Compra;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import java.time.*;
import java.util.List;

import pe.edu.pucp.ticketflow.IComprasBL;
import pe.edu.pucp.ticketflow.IEventoBL;
import pe.edu.pucp.ticketflow.IUsuarioBL;
import pe.edu.pucp.ticketflow.compra.model.Compra;
import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.impl.ComprasBLImpl;
import pe.edu.pucp.ticketflow.impl.EventoBLImpl;
import pe.edu.pucp.ticketflow.impl.UsuarioBLImpl;
import pe.edu.pucp.ticketflow.usuario.model.Usuario;

import pe.edu.pucp.ticketflow.Infrastructure.EmailService;


@Singleton
public class RecordatorioScheduler {
    private EmailService emailService=new EmailService();

    private IEventoBL eventoBL = new EventoBLImpl();
    private IComprasBL comprasBL = new ComprasBLImpl();
    private IUsuarioBL usuarioBL = new UsuarioBLImpl();

    @Schedule(minute = "*/3", hour = "*", persistent = false)
    public void ejecutar() {

        System.out.println("===== Scheduler iniciado =====");

        try {

            List<Evento> eventos = eventoBL.ListarEventosProximos();

            System.out.println("Eventos encontrados: " + eventos.size());

            for (Evento e : eventos) {

                System.out.println("\nEvento ID: " + e.getIdEvento());
                System.out.println("Título: " + e.getTitulo());

                LocalDateTime fechaEvento = LocalDateTime.of(
                        LocalDate.parse(e.getFecha()),
                        LocalTime.parse(e.getHora_inicio())
                );

                List<Compra> compras =
                        comprasBL.ListarComprasDeEvento(e.getIdEvento());

                System.out.println("Compras encontradas: " + compras.size());

                for (Compra compra : compras) {

                    System.out.println("\n========================================");
                    System.out.println("Compra ID: " + compra.getIdCompra());

                    System.out.println("recordatorio_enviado  = "
                            + compra.isRecordatorio_enviado());

                    System.out.println("recordatorio2_enviado = "
                            + compra.isRecordatorio2_enviado());

                    LocalDateTime ahora = LocalDateTime.now();
                    Duration diferencia = Duration.between(ahora, fechaEvento);

                    System.out.println("Minutos restantes = " + diferencia.toMinutes());

                    System.out.println("debeEnviar24Horas = "
                            + debeEnviar24Horas(fechaEvento));

                    System.out.println("debeEnviar3Horas = "
                            + debeEnviar3Horas(fechaEvento));

                    Usuario u = usuarioBL.verPerfil(compra.getIdCliente());


// =======================
// Recordatorio 24 horas
// =======================
                    if (!compra.isRecordatorio_enviado()
                            && debeEnviar24Horas(fechaEvento)) {

                        System.out.println(">>> ENTRA AL IF DE 24 HORAS");

                        String html3h = """
                                        <html>
                                        <body style="font-family: Arial, sans-serif; background-color:#f4f6f8; margin:0; padding:20px;">
                                        
                                            <div style="max-width:600px; margin:auto; background:white; border-radius:12px; overflow:hidden; box-shadow:0 4px 12px rgba(0,0,0,0.1);">
                                        
                                                <!-- HEADER -->
                                                <div style="background:#2c3e50; padding:20px; text-align:center;">
                                                    <h1 style="color:white; margin:0;">🎟 TicketFlow</h1>
                                                    <p style="color:#ecf0f1; margin:5px 0 0;">Recordatorio de evento</p>
                                                </div>
                                        
                                                <!-- BODY -->
                                                <div style="padding:25px;">
                                        
                                                    <p style="font-size:16px;">Hola <b>%s</b>,</p>
                                        
                                                    <p style="font-size:15px; color:#444;">
                                                        Te recordamos que tu evento comenzará en <b>24 horas</b>.
                                                    </p>
                                        
                                                    <div style="background:#ecf0f1; padding:15px; border-radius:8px; margin:20px 0;">
                                                        <p style="margin:5px 0;"><b>🎤 Evento:</b> %s</p>
                                                        <p style="margin:5px 0;"><b>📅 Fecha:</b> %s</p>
                                                        <p style="margin:5px 0;"><b>⏰ Hora:</b> %s</p>
                                                    </div>
                                        
                                                    <p style="color:#666; font-size:14px;">
                                                        Asegúrate de llegar con anticipación para disfrutar tu experiencia sin inconvenientes.
                                                    </p>
                                        
                                                    <div style="text-align:center; margin-top:25px;">
                                                        <span style="display:inline-block; padding:10px 20px; background:#3498db; color:white; border-radius:6px; font-size:14px;">
                                                            ✔ Recordatorio automático TicketFlow
                                                        </span>
                                                    </div>
                                        
                                                </div>
                                        
                                                <!-- FOOTER -->
                                                <div style="background:#f1f1f1; text-align:center; padding:12px;">
                                                    <small style="color:#888;">© 2026 TicketFlow - Todos los derechos reservados</small>
                                                </div>
                                        
                                            </div>
                                        
                                        </body>
                                        </html>
                                        """.formatted(
                                u.getNombre(),
                                e.getTitulo(),
                                e.getFecha(),
                                e.getHora_inicio()
                        );

                        emailService.enviarCorreoAsync(
                                u.getCorreoElectronico(),
                                "TICKET FLOW - Recordatorio",
                                html3h
                        );


                            comprasBL.marcarCompraComoEnviado(compra.getIdCompra());
                            System.out.println("Se marcó en BD (24h)");

                    }


// =======================
// Recordatorio 3 horas
// =======================
                    if (!compra.isRecordatorio2_enviado()
                            && debeEnviar3Horas(fechaEvento)) {

                        System.out.println(">>> ENTRA AL IF DE 3 HORAS");

                        String html3h = """
                                        <html>
                                        <body style="font-family: Arial; background:#fff3cd; padding:20px;">
                                        
                                            <div style="max-width:600px; margin:auto; background:white; padding:20px; border-radius:10px;">
                                        
                                                <h2 style="color:#d35400;">⚠ Último aviso - TicketFlow</h2>
                                        
                                                <p>Hola <b>%s</b>,</p>
                                        
                                                <p>🔥 Tu evento empieza en menos de <b>3 horas</b>.</p>
                                        
                                                <hr>
                                        
                                                <p><b>Evento:</b> %s</p>
                                                <p><b>Fecha:</b> %s</p>
                                                <p><b>Hora:</b> %s</p>
                                        
                                                <br>
                                                <p style="color:gray;">No respondas a este correo.</p>
                                        
                                            </div>
                                        
                                        </body>
                                        </html>
                                        """.formatted(u.getNombre(),
                                e.getTitulo(),
                                e.getFecha(),
                                e.getHora_inicio()
                        );


                        emailService.enviarCorreoAsync(
                                u.getCorreoElectronico(),
                                "TICKET FLOW - Último recordatorio",
                                html3h
                        );

                            comprasBL.marcarCompraComoEnviado2(compra.getIdCompra());
                            System.out.println("Se marcó en BD (3h)");

                    }
                }
            }

        } catch (Exception ex) {
            System.err.println("❌ ERROR EN SCHEDULER:");
            ex.printStackTrace();
        }

        System.out.println("===== Scheduler finalizado =====");
    }

    private boolean debeEnviar24Horas(LocalDateTime fechaEvento) {

        long minutos = Duration.between(LocalDateTime.now(), fechaEvento).toMinutes();

        return minutos <= 24 * 60;
    }
    private boolean debeEnviar3Horas(LocalDateTime fechaEvento) {

        long minutos = Duration.between(LocalDateTime.now(), fechaEvento).toMinutes();

        return minutos <= 3 * 60;
    }

}