package src.Devicemanagementprojekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register {
    private final JFrame frame;
    private final JPanel panel;
    private final DB datenbank;

    public Register(DB datenbank) {
        this.datenbank = datenbank;
        frame = new JFrame();
        panel = new JPanel(new GridBagLayout());
        frame.add(panel, BorderLayout.CENTER);
        updatepanel(0); // changed default fehlercode to 0
        frame.pack(); // Adjust the frame size to fit its contents
        frame.setVisible(true);
    }

    public void updatepanel(int fehlercode) {
        panel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();

        //Komponenten erstellen
        JLabel aufforderungName = new JLabel("Name:");
        JTextField NameEingabe = new JTextField(20);
        JLabel aufforderungIDLabel = new JLabel("Nutzername:");
        JTextField IDeingabe = new JTextField(20);
        JLabel aufforderungPWLabel = new JLabel("Passwort erstellen:");
        JPasswordField setPW = new JPasswordField(20);
        JButton registerButton = new JButton("Registrieren");

        JLabel UNfehler = new JLabel("Nutzername schon vergeben oder leer");
        JLabel PWfehler = new JLabel("Bitte Passwort eingeben");

        // Name Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Margin around the components
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(aufforderungName, gbc);

        // Name Eingabe
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(NameEingabe, gbc);

        // Username Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10); // Margin around the components
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(aufforderungIDLabel, gbc);

        // Username Eingabe
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(IDeingabe, gbc);

        //Fehlermeldung
        if(fehlercode == 2 || fehlercode == 12){
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(UNfehler, gbc);
        }

        // Password Label
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(aufforderungPWLabel, gbc);

        // Password Eingabe
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(setPW, gbc);

        //Passwortfehler anzeige
        if(fehlercode == 12 || fehlercode == 11){
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            panel.add(PWfehler, gbc);
        }

        //Fenstervergrößertung bei doppelter Fehlermeldung
        if(fehlercode == 12){
            frame.setSize(new Dimension(415, 300));
        }

        //Fenstergröße bei Fehlermeldung
        if(fehlercode == 11 || fehlercode == 2){
            frame.setSize(new Dimension(415, 260));
        }

        // Register Button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, gbc);

        panel.revalidate();
        panel.repaint();

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                usernamecheck(IDeingabe.getText(), new String(setPW.getPassword()), NameEingabe.getText());
            }
        });
    }

    public void usernamecheck(String EingabeUsN, String EingabePW, String Name) {
        int fehlercode = 1;
        //guckt ob Username schon existiert und ob er leer ist
        for (User u : datenbank.getUserList()) {
            if (EingabeUsN.equals(u.getUsername()) || EingabeUsN.equals("0") || EingabeUsN.equals("12437") || EingabeUsN.isEmpty()) {
                fehlercode = 2;
                break;
            }
        }

        if(checkpw(EingabePW, fehlercode) == 1){
            CreateUser(EingabeUsN, EingabePW, Name);
        } else {
            updatepanel(checkpw(EingabePW, fehlercode));
        }
    }

    public int checkpw(String EingabePW, int fehlercode) {
        if (EingabePW.isEmpty()) {
            fehlercode = fehlercode + 10;
        }
        System.out.println(fehlercode);
        return fehlercode;
    }

    private void usercreatedpanel(User user) {
        JPanel usercreatedpane = new JPanel(new BorderLayout());
        panel.removeAll();
        frame.setSize(new Dimension(200, 80));
        JLabel usercreated = new JLabel(user.getName() + " " + user.getUsername() + " erstellt");
        usercreatedpane.add(usercreated, BorderLayout.CENTER);
        frame.remove(panel);
        frame.add(usercreatedpane, BorderLayout.CENTER);
    }

    public void CreateUser(String EingabeUsN, String EingabePW, String Name) {
        User newuser = new User(EingabeUsN, Name, "//", EingabePW, false);
        datenbank.getUserList().add(newuser);
        usercreatedpanel(newuser);
    }

    public void makevisible() {
        frame.dispose();
        frame.setVisible(false);
        new Register(datenbank);
    }

    public void makeinvisible() {
        frame.dispose();
    }
}