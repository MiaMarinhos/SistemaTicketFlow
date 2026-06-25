package Email;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailSender {

    private static final String USER = "orihuelamonterofabianandres@gmail.com";
    private static final String PASS = "qvkgxfwlnofkxgck";

    private static Session getSession() {

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

// 🔥 FIX SSL CERT
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, PASS);
            }
        });

        session.setDebug(true);
        return session;
    }

    public static boolean enviar(String to, String subject, String body) {

        try {

            Message message = new MimeMessage(getSession());

            message.setFrom(new InternetAddress(USER));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Correo enviado a: " + to);

            return true;

        } catch (MessagingException e) {

            System.err.println("ERROR enviando correo a: " + to);
            e.printStackTrace();

            return false;
        }
    }}