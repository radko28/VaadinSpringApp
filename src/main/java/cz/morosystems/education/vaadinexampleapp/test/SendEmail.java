package cz.morosystems.education.vaadinexampleapp.test;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendEmail {

    public static void main(String[] args) {
        // Recipient's email ID needs to be mentioned.
        String to = "rado.kuzma@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "radoslav.kuzma@morosystems.cz";

        // Assuming you are sending email from localhost
        String host = "smtp.ogservices.cz";
        String port = "25";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.user", "radoslav.kuzma@morosystems.cz");
        properties.setProperty("mail.password", "rd50mogs12");
        properties.setProperty("mail.smtp.starttls.enable", "true");


        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}