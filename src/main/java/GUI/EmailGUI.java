package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Launcher.MockEmailGUI.sendFromOutlook;
import static Launcher.MockEmailGUI.validateEmails;

/**
 * @author stevenhorng
 */

public class EmailGUI extends JFrame {
    public static String fileLocation;
    public JPanel Jpanel;
    private JTextField textTo;
    private JTextField textCC;
    private JTextField textBCC;
    private JTextField textSubject;
    private JTextArea textMessage;
    private JButton attachButton;
    private JTextField filePathLocation;
    private JLabel To;
    private JButton buttonSend;

    public EmailGUI() {
        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strTextTo = textTo.getText();
                String strTextBCC = textBCC.getText();
                String strTextCC = textCC.getText();
                String strTextSubject = textSubject.getText();
                String strTextMessage = textMessage.getText();
                System.out.println();
                String[] bccList = null;
                if (strTextBCC != null && !strTextBCC.isEmpty()) {
                    bccList = strTextBCC.split(",");
                    boolean isValid = validateEmails(bccList, javax.mail.Message.RecipientType.BCC);
                    if (!isValid) {
                        return;
                    }
                }

                String[] toList = null;
                if (strTextTo != null && !strTextTo.isEmpty()) {
                    toList = strTextTo.split(",");
                    boolean isValid = validateEmails(toList, javax.mail.Message.RecipientType.TO);
                    if (!isValid) {
                        return;
                    }
                }
                String[] ccList = null;
                if (strTextCC != null && !strTextCC.isEmpty()) {
                    ccList = strTextCC.split(",");
                    boolean isValid = validateEmails(ccList, javax.mail.Message.RecipientType.CC);
                    if (!isValid) {
                        return;
                    }

                }

                if (strTextMessage.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You forgot to write message. ");
                }
                if (strTextTo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You forgot to input To email address. ");
                    return;
                }

                sendFromOutlook(toList, ccList, bccList, strTextSubject, strTextMessage);
            }
        });

        attachButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == attachButton) {
                    JFileChooser fileChooser = new JFileChooser();
                    int res = fileChooser.showOpenDialog(null);
                    if (res == JFileChooser.APPROVE_OPTION) {
                        fileLocation = fileChooser.getSelectedFile().getAbsolutePath();
                        filePathLocation.setText(fileLocation);

                    }
                }

            }
        });
    }
}