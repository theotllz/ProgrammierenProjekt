package src.Devicemanagementprojekt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Welcome {

    //Frame und Panel
    private final JFrame frame;
    private final JPanel welcomePanel;
    //gegen Doppelte erstellung
    private final boolean nevercreatedAdminV;
    private final boolean nevercreatedUserV;
    //Fenster
    private UserWindow userV;
    private AdminWindow adminV;
    //Text und Datenbank
    private DB datenbank;
    //Dateiname bei Serialisierung
    private static final String DATABASE_FILE = "database.ser";
    //zum abspeichern des loginusers, methodenübergreifend
    private User Loginuser;


    public Welcome() {
        //läd informationen von serialisierter Datei oder erstellt neue Datenbanm(DB)
        loadDatabase();
        //nevercreatedwindows boolean(damit keine Klasse doppelt erstellt werden muss)
        nevercreatedAdminV = true;
        nevercreatedUserV = true;

        // Initlialisiert den Frame
        {
        this.frame = new JFrame();
        this.frame.setTitle("Login");
        this.frame.setSize(500, 400);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomePanel = new JPanel(new BorderLayout());
        //füllt das Panel
        updateWelcomePanel();}
    }


    //füllt das Panel neu bei Ersterllung und zur Veränderung
    public void updateWelcomePanel() {
        welcomePanel.removeAll();
        frame.add(welcomePanel);

        //Top Panel
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        welcomePanel.add(top, BorderLayout.NORTH);
        //Bottom Panel
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        welcomePanel.add(bottom, BorderLayout.SOUTH);


        // Button "Speichern und Schließen"
        JButton saveAndCloseButton = new JButton("Speichern und Schließen");
        saveAndCloseButton.setMargin(new Insets(10, 10, 10, 10));
        //Beendet das programm nach Speicherung
        saveAndCloseButton.addActionListener(e -> {
            try {
                saveDatabase();
                System.exit(0);
            } catch (Exception ex) {
                System.out.println("Error");
            }

        });
        top.add(saveAndCloseButton);


        // Button "Registrieren"
        JButton RegisterButton = new JButton("Registrieren");
        Register reg = new Register(datenbank);
        reg.makeinvisible();
        RegisterButton.setMargin(new Insets(20, 20, 20, 20));
        RegisterButton.addActionListener(e -> {
            reg.makevisible();

        });
        bottom.add(RegisterButton);


        // Panel für Login
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));


        //Login feld
        JTextField UsernameField = new JTextField(15);
        JPasswordField PasswordField = new JPasswordField(15);
        centerPanel.add(UsernameField);
        centerPanel.add(PasswordField);

        //LOGIN BUTTON & ACTION
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.GREEN);
        loginButton.setForeground(Color.DARK_GRAY);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Loginuser = userCheck(UsernameField, PasswordField);
                if (Loginuser == null) {
                    //Panelupdate, wenn user nicht gefunden wird
                    //updateWelcomePanel();

                } else {
                    if (Loginuser.getAdmin()) {
                        if (nevercreatedAdminV) {
                            updateWelcomePanel();
                            adminV = new AdminWindow(frame, datenbank);
                            adminV.visibility(true);
                        } else {
                            updateWelcomePanel();
                            adminV.visibility(true);
                        }
                    } else {
                        if (nevercreatedUserV) {
                            updateWelcomePanel();
                            userV = new UserWindow(frame, Loginuser, datenbank);
                        } else {
                            userV.visibility(true, Loginuser);
                        }
                    }
                    frame.setVisible(false); //Login Frame nach Login schließen
                }
            }
        });
        centerPanel.add(loginButton);


        welcomePanel.add(centerPanel, BorderLayout.CENTER);


        // Frame sichtbar machen
        frame.setVisible(true);
    }


    //zum speichern und schließen des programmes
    private void saveDatabase() {
        try {
            String path = getRunningFilePath();
            System.out.println(path);
            File file = new File(path, DATABASE_FILE);
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(datenbank);
            out.close();
            fileOut.close();
            System.out.println("Database object saved at " + file.getAbsolutePath());
        } catch (IOException i) {
            i.printStackTrace();
        }
    }


    //zum abrufen der serialisierten Datenbanklasse
    private void loadDatabase() {
        try {
            String path = getRunningFilePath();
            File file = new File(path, DATABASE_FILE);
            if (file.exists()) {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                datenbank = (DB) in.readObject();
                in.close();
                fileIn.close();
                System.out.println("Database object loaded from " + file.getAbsolutePath());
            } else {
                // Initialisiert eine neue Datenbank wenn nicht gefunden
                datenbank = new DB();
                System.out.println("New database object created.");
            }
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.err.println("Database class not found.");
        }
    }

    //Pfad der ausgeführten Datei abrufen, um serialisierte Datei zu speichern
    private String getRunningFilePath() {
        try {
            String path = Welcome.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            return new File(path).getParent();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //checkt ob der eingegebene user existiert und ob das Passwort korrekt ist.
    public User userCheck(JTextField UsernameField, JTextField PasswordField) {
        for (User user : datenbank.getUserList()) {
            if (UsernameField.getText().equals(user.getUsername())) {
                if (user.getPassword().equals(PasswordField.getText())) {
                    return user;
                }
            }
        }
        // Darstellung einer Fehlermeldung bei falschen Anmeldedaten
        JOptionPane.showMessageDialog(null, "Benutzername oder Passwort falsch", "Bitte erneut eingeben", JOptionPane.ERROR_MESSAGE);
        return null;
    }


}