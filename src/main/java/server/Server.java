package server;

import accountconfig.MyAccount;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * @author stevenhorng
 */

public class Server {

    public static Session getSession() {
        // Get system properties
        Properties properties = new Properties();

        // Setup mail server
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", MyAccount.SMTP_HOST_NAME);
        properties.put("mail.smtp.user", MyAccount.MY_OUTLOOK);
        properties.put("mail.smtp.Password", MyAccount.PASSWORD);
        properties.put("mail.smtp.port", MyAccount.SMTP_HOST_PORT);
        properties.put("mail.smtp.auth", "true");

        System.out.println("2nd ===> Get Mail Session..");
        // Get the Session object.

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MyAccount.MY_OUTLOOK, MyAccount.PASSWORD);
            }
        };

        Session session = Session.getInstance(properties, auth);
        return session;
    }
}
