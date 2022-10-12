package Launcher;


import GUI.EmailGUI;
import accountconfig.MyAccount;
import server.Server;

import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

/**
 * @author stevenhorng
 */
public class MockEmailGUI extends JFrame {
    private static String fileLocation;
    private JTextField textTo;
    private JTextField textCC;
    private JTextField textBCC;
    private JTextField textSubject;
    private JTextArea textMessage;
    private JLabel To;
    private JButton btnSend;
    private JPanel Jpanel;
    private JButton attachButton;
    private JTextField filePathLocation;

    public static void main(String[] args) {
        String email = "www.outlook.com";
        System.out.println(isEmailValid(email));
        EmailGUI s = new EmailGUI();
        s.setContentPane(s.Jpanel);
        s.setTitle("Send Outlook mail via Java");
        s.setSize(300, 400);
        s.setLocationRelativeTo(null);
        s.setVisible(true);
        s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    //Initializing
    public static void sendFromOutlook(String[] to, String[] cc, String[] bcc, String subject, String body) {

        System.out.println("1st ===> Setup SMTP Mail Server Properties..!");


        Session session = Server.getSession();
        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);

        try {

            addRecipient(message, MimeMessage.RecipientType.BCC, bcc);
            addRecipient(message, MimeMessage.RecipientType.CC, cc);
            addRecipient(message, MimeMessage.RecipientType.TO, to);
            //---------------------------------------------
// attachment
            try {

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(MyAccount.MY_OUTLOOK));

                Multipart multipart = new MimeMultipart();

                MimeBodyPart attachmentPart = new MimeBodyPart();

                MimeBodyPart textPart = new MimeBodyPart();

                try {
                    textPart.setText(body);
                    multipart.addBodyPart(textPart);

                    if (EmailGUI.fileLocation != null && !EmailGUI.fileLocation.isEmpty()) {

                        File f = new File(EmailGUI.fileLocation);
                        attachmentPart.attachFile(f);
                        multipart.addBodyPart(attachmentPart);
                    }

                } catch (IOException e) {

                    e.printStackTrace();

                }


                // Set Subject: header field
                message.setSubject(subject);

                // Now set the date to actual message
                message.setSentDate(new Date());


                message.setContent(multipart);

                System.out.println("sending...");
                // Send message
                Transport.send(message);
                System.out.println("Sent message successfully....");
            } catch (MessagingException mex) {
                mex.printStackTrace();
            }

        } catch (
                AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }


    }


    public static void addRecipient(MimeMessage message, Message.RecipientType sendType, String... recipientArray) throws
            MessagingException {

        if (recipientArray == null) {
            return;
        }
        // To get the array of ccaddresses
        for (int i = 0; i < recipientArray.length; i++) {
            message.addRecipient(sendType, new InternetAddress(recipientArray[i]));
        }

    }


    public static boolean validateEmails(String[] allEmails, javax.mail.Message.RecipientType type) {
        boolean isValid = true;
        String messageFormat = "You Email [%s] within [%s] recipient is an invalid email address or you forgot to enter Sender email.";

        for (int i = 0; i < allEmails.length; i++) {
            String email = allEmails[i].trim();

            if (!isEmailValid(email)) {

                JOptionPane.showMessageDialog(null, String.format(messageFormat, email, type.toString()));
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    public static boolean isEmailValid(String email) {

        Pattern pattern = compile("(?:^|[ ])([a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7})(?=$|[ ])", CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        boolean matchFound = matcher.find();
        return matchFound;
    }
}
