package pe.edu.pucp.ticketflow.Infrastructure;
import pe.edu.pucp.ticketflow.Infrastructure.AsyncExecutor;

import pe.edu.pucp.ticketflow.Infrastructure.EmailSender;

public class EmailService {

    public void enviarCorreo(String destino, String asunto, String html) {
        EmailSender.enviar(destino, asunto, html);
    }
    public void enviarCorreoAsync(String destino, String asunto, String html) {
        AsyncExecutor.ejecutar(() -> {
            try {
                EmailSender.enviar(destino, asunto, html);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }



}