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

                    System.out.println("Procesando compra ID: " + compra.getIdCompra());

                    if (compra.isRecordatorioEnviado()) {
                        System.out.println("Ya fue enviado, se omite");
                        continue;
                    }

                    boolean debeEnviar = debeEnviar(fechaEvento);

                    System.out.println("Debe enviar?: " + debeEnviar);

                    if (!debeEnviar) {
                        continue;
                    }

                    Usuario u = usuarioBL.verPerfil(compra.getIdCliente());

                    System.out.println("Enviando a: " + u.getCorreoElectronico());

                    boolean enviado = EmailSender.enviar(
                            u.getCorreoElectronico(),
                            "TICKET FLOW - Recordatorio de evento",
                            "Tu evento '" + e.getTitulo()
                                    + "' es el" + e.getFecha() + " a las "
                                    + e.getHora_inicio()
                    );

                    if (enviado) {
                        comprasBL.marcarCompraComoEnviado(compra.getIdCompra());
                        System.out.println("✔ Compra marcada como enviada");
                    } else {
                        System.out.println("❌ Falló envío de correo");
                    }
                }
            }

        } catch (Exception ex) {
            System.err.println("❌ ERROR EN SCHEDULER:");
            ex.printStackTrace();
        }

        System.out.println("===== Scheduler finalizado =====");
    }

    private boolean debeEnviar(LocalDateTime fechaEvento) {

        LocalDateTime ahora = LocalDateTime.now();

        return fechaEvento.isAfter(ahora)
                && fechaEvento.isBefore(ahora.plusHours(24));
    }
}