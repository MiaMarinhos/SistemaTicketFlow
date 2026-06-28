package Email;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import java.time.*;
import java.util.List;

import pe.edu.pucp.ticketflow.IComprasBL;
import pe.edu.pucp.ticketflow.IEventoBL;
import pe.edu.pucp.ticketflow.IUsuarioBL;
import pe.edu.pucp.ticketflow.compra.model.Compra;
import pe.edu.pucp.ticketflow.evento.model.Evento;
import pe.edu.pucp.ticketflow.exception.BusinessLogicException;
import pe.edu.pucp.ticketflow.impl.ComprasBLImpl;
import pe.edu.pucp.ticketflow.impl.EventoBLImpl;
import pe.edu.pucp.ticketflow.impl.UsuarioBLImpl;
import pe.edu.pucp.ticketflow.usuario.model.Usuario;
@Singleton
public class RecordatorioScheduler {

    private IEventoBL eventoBL = new EventoBLImpl();
    private IComprasBL comprasBL = new ComprasBLImpl();
    private IUsuarioBL usuarioBL = new UsuarioBLImpl();

    @Schedule(minute = "*/2", hour = "*", persistent = false)
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

                        boolean enviado = EmailSender.enviar(
                                u.getCorreoElectronico(),
                                "TICKET FLOW - Recordatorio de evento",
                                "¡Faltan 24 horas para tu evento!\n\n"
                                        + "Evento: " + e.getTitulo()
                                        + "\nFecha: " + e.getFecha()
                                        + "\nHora: " + e.getHora_inicio()
                        );

                        System.out.println("Email enviado = " + enviado);

                        if (enviado) {
                            comprasBL.marcarCompraComoEnviado(compra.getIdCompra());
                            System.out.println("Se marcó en BD (24h)");
                        }
                    }


// =======================
// Recordatorio 3 horas
// =======================
                    if (!compra.isRecordatorio2_enviado()
                            && debeEnviar3Horas(fechaEvento)) {

                        System.out.println(">>> ENTRA AL IF DE 3 HORAS");

                        boolean enviado = EmailSender.enviar(
                                u.getCorreoElectronico(),
                                "TICKET FLOW - Recordatorio de evento",
                                "¡Tu evento comienza en menos de 3 horas!\n\n"
                                        + "Evento: " + e.getTitulo()
                                        + "\nFecha: " + e.getFecha()
                                        + "\nHora: " + e.getHora_inicio()
                        );

                        System.out.println("Email enviado = " + enviado);

                        if (enviado) {
                            comprasBL.marcarCompraComoEnviado2(compra.getIdCompra());
                            System.out.println("Se marcó en BD (3h)");
                        }
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