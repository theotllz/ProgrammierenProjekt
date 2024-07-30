package src.Devicemanagementprojekt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Welcome {
    //Frame und Panel
    private JFrame frame;
    private JPanel welcomePanel;
    //gegen Doppelte erstellung
    private boolean nevercreatedAdminV;
    private boolean nevercreatedUserV;
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
        //nevercreated windows(damit keine Klasse doppelt erstellt werden muss)
        nevercreatedAdminV = true;
        nevercreatedUserV = true;

        // Initialize the JFrame
        this.frame = new JFrame();
        this.frame.setTitle("Login");
        this.frame.setSize(500, 400);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomePanel = new JPanel(new BorderLayout());
        //füllt das Panel
        updateWelcomePanel();
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
        saveAndCloseButton.setMargin(new Insets(10, 10, 10, 10)); // Adjust button padding
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
        RegisterButton.setMargin(new Insets(20, 20, 20, 20)); // Adjust button padding
        RegisterButton.addActionListener(e -> {
            Register reg = new Register(datenbank);
        });
        bottom.add(RegisterButton);


        // Panel für Login
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding around the panel


        //Login feld
        JTextField UsernameField = new JTextField(15);
        JPasswordField PasswordField = new JPasswordField(15);
        centerPanel.add(UsernameField);
        centerPanel.add(PasswordField);

        //LOGIN BUTTON & ACTION
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.GREEN); // Set button background color
        loginButton.setForeground(Color.DARK_GRAY); // Set text color
        loginButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Loginuser = userCheck(UsernameField, PasswordField);
                if (Loginuser == null) {
                    updateWelcomePanel();
                } else {
                    if (Loginuser.getAdmin()) {
                        if (nevercreatedAdminV) {
                            updateWelcomePanel();
                            adminV = new AdminWindow(frame, Loginuser, datenbank);
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
                    frame.setVisible(false); // Hide the Welcome window after login
                }
            }
        });
        centerPanel.add(loginButton);


        welcomePanel.add(centerPanel, BorderLayout.CENTER);


        // Make the frame visible when
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

    //zum abrufen der Datenbankklasse
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
        JOptionPane.showMessageDialog(null, "Username or password wrong", "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }


}