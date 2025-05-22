package utils;

import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.*;
import javax.mail.internet.*;

public class MailUtil {
    private static MailUtil instance = null;

    public static MailUtil getInstance(){
        if (instance == null)
            instance = new MailUtil();
        return instance;
    }

    public void sendMail(String body, String subject, String email){
        ResourceBundle mybundle = ResourceBundle.getBundle("application");
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", mybundle.getString("mail.host"));
        props.put("mail.smtp.port", mybundle.getString("mail.port"));

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mybundle.getString("mail.email"), mybundle.getString("mail.password"));
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mybundle.getString("mail.email")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject, "UTF-8");
            message.setText(body, "UTF-8");
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
