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
    private JLabel adminTextPanel;
    private DB datenbank;
    //Dateiname bei Serialisierung
    private static final String DATABASE_FILE = "database.ser";
    JFrame Registerframe;
    JPanel Registerpanel;

    private User Loginuser;

    public Welcome() {
        initialize();
    }

    public void initialize() {
        //läd informationen von serialisierter Datei oder erstellt neue DB
        loadDatabase();
        //nevercreated windows
        nevercreatedAdminV = true;
        nevercreatedUserV = true;

        // Initialize the JFrame
        this.frame = new JFrame();
        this.frame.setTitle("Willkommen");
        this.frame.setSize(500, 400);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // Create the main panel with BorderLayout
        welcomePanel = new JPanel(new BorderLayout());
        frame.add(welcomePanel);

        // Create a panel for the top left corner with FlowLayout
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        welcomePanel.add(topLeftPanel, BorderLayout.NORTH);

        // Button "Speichern und Schließen"
        JButton saveAndCloseButton = new JButton("Speichern und Schließen");
        saveAndCloseButton.setMargin(new Insets(5, 10, 5, 10)); // Adjust button padding
        saveAndCloseButton.addActionListener(e -> {
            try{
                saveDatabase();
            }
            catch(Exception ex){
                System.out.println("Error");
            }

            frame.dispose(); // Close the frame
        });


        saveAndCloseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add save logic if needed
                try {
                    saveDatabase();
                } catch (Exception ex) {
                    System.out.println("Error");
                }

                frame.dispose(); // Close the frame
            }
        });
        topLeftPanel.add(saveAndCloseButton);

        // Panel for center controls
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        // Hardcoded User
        User user1 = new User(1, "Theo", "theodor.telliez@gmail.com", "123", true);
        User user2 = new User(2, "alex", "Alexwalosycxysyk@gmail.com", "123", false);

        datenbank.getUserList().add(user1);
        datenbank.getUserList().add(user2);

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
                if(Loginuser ==null){
                    initialize();
                }
                else {
                    if (Loginuser.getAdmin()) {
                        if (nevercreatedAdminV) {
                            //initialize();
                            adminV = new AdminWindow(frame, Loginuser, datenbank);
                            adminV.visibility(true);
                        } else {
                            //initialize();
                            adminV.visibility(true);
                        }
                    } else {
                        if (nevercreatedUserV) {
                            //initialize();
                            userV = new UserWindow(frame, Loginuser, datenbank);
                        } else {
                            //initialize();
                            userV.visibility(true, Loginuser);
                        }
                    }
                    frame.setVisible(false); // Hide the Welcome window after login
                }
            }
        });
        centerPanel.add(loginButton);


        welcomePanel.add(centerPanel, BorderLayout.CENTER);


        // Make the frame visible
        frame.setVisible(true);
    }



    // Method to make the Welcome window visible externally
    public void welcomeVisible() {
        this.frame.setVisible(true);

    }

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
                // Initialize a new database if not found
                datenbank = new DB();
                System.out.println("New database object created.");
            }
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Database class not found.");
            c.printStackTrace();
        }
    }

    public User userCheck(JTextField UsernameField, JTextField PasswordField) {
        for (User user : datenbank.getUserList()) {
            if (UsernameField.getText().equals(user.getName())) {
                System.out.println("checkname");
                if (user.getPassword().equals(PasswordField.getText())) {
                    System.out.println("checkpw");
                    return user;
                }
            }
        }
        System.out.println("Username or passwort wrong");
        return null;
    }

    //Methode zur erstellung eines neuen nutzers


    /*private void updatepanelREGISTER(int fehlercode){
        Registerpanel.add(new JLabel("blabla"));
        Registerpanel.removeAll();
        int i = 1;

        GridBagConstraints gbc = new GridBagConstraints();

        // Create components
        JLabel aufforderungIDLabel = new JLabel("Id erstellen:");
        //JTextPane IDeingabe = new JTextPane();
        JTextField IDeingabe = new JTextField();

        JLabel aufforderungPWLabel = new JLabel("Passwort erstellen:");
        JTextPane setPW = new JTextPane();
        JButton registerButton = new JButton("Registrieren");
        JLabel namenichtverfügbarmeldung = new JLabel("Nutzername vergeben!");
        JLabel bittezahl = new JLabel("Gib bitte eine Zahl ein");
        JLabel pwmusssein = new JLabel("Bitte passwort eingeben");

        // Adjust grid bag constraints for each component

        // Id Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Margin around the components
        gbc.anchor = GridBagConstraints.EAST;
        Registerpanel.add(aufforderungIDLabel, gbc);

        // Id Eingabe
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        Registerpanel.add(IDeingabe, gbc);

        //bereitsvergeben
        if(fehlercode==1||fehlercode==11){
            gbc.gridx = 1;
            gbc.gridy = i;
            gbc.insets = new Insets(5, 5, 5, 5); // Margin around the components
            gbc.anchor = GridBagConstraints.EAST;
            Registerpanel.add(namenichtverfügbarmeldung, gbc);
            i++;
        }

        if(fehlercode==2||fehlercode==12){
            gbc.gridx = 1;
            gbc.gridy = i;
            gbc.insets = new Insets(5, 5, 5, 5); // Margin around the components
            gbc.anchor = GridBagConstraints.EAST;
            Registerpanel.add(bittezahl, gbc);
            i++;
        }

        // Password Label
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        Registerpanel.add(aufforderungPWLabel, gbc);

        // Password Eingabe
        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        Registerpanel.add(setPW, gbc);

        if(fehlercode==11||fehlercode==16||fehlercode==12){
            System.out.println("paswortleck2");
            gbc.gridx = 0;
            gbc.gridy = i+1;
            gbc.gridwidth = 2;
            gbc.insets = new Insets(5, 5, 5, 5); // Margin around the components
            gbc.anchor = GridBagConstraints.EAST;
            Registerpanel.add(pwmusssein, gbc);
            i++;
        }

        // Register Button
        gbc.gridx = 0;
        gbc.gridy = i+1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        Registerpanel.add(registerButton, gbc);

        Registerframe.add(Registerpanel, BorderLayout.CENTER);
        Registerframe.pack(); // Adjust the frame size to fit its contents
        Registerframe.setVisible(true);
        Registerframe.setSize(400,400);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                idcheck(IDeingabe.getText(), setPW.getText());


            }
        });
    }*/

    private void continueREGISTER(){
        Registerpanel.add(new JLabel("blabla"));
        Registerpanel.removeAll();
        int i = 1;

        GridBagConstraints gbc = new GridBagConstraints();

        // Create components
        JLabel aufforderungIDLabel = new JLabel("Name");
        //JTextPane IDeingabe = new JTextPane();
        JTextField IDeingabe = new JTextField();

        JLabel aufforderungPWLabel = new JLabel("Geburtstag");
        JTextPane setPW = new JTextPane();
        JButton registerButton = new JButton("Weiter");

        // Id Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Margin around the components
        gbc.anchor = GridBagConstraints.WEST;
        Registerpanel.add(aufforderungIDLabel, gbc);

        // Id Eingabe
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        Registerpanel.add(IDeingabe, gbc);


        // Password Label
        gbc.gridx = 0;
        gbc.gridy = i+1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        Registerpanel.add(aufforderungPWLabel, gbc);

        // Password Eingabe
        gbc.gridx = 1;
        gbc.gridy = i+1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        Registerpanel.add(setPW, gbc);

        // Register Button
        gbc.gridx = 0;
        gbc.gridy = i+2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        Registerpanel.add(registerButton, gbc);

        Registerframe.add(Registerpanel, BorderLayout.CENTER);
        Registerframe.pack(); // Adjust the frame size to fit its contents
        Registerframe.setVisible(true);
        Registerframe.setSize(400,400);

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

    //Method for login for a user with password


    /*private void userCreate() {

        Registerframe = new JFrame("Benutzer erstellen");
        Registerframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Registerpanel = new JPanel(new GridBagLayout());
        Registerframe.add(Registerpanel);
        updatepanelREGISTER(0);
    }
    public void idcheck(String EingegebenID, String EingabePW){
        boolean Dopplung = false;
        int Eiingabe = 0;
            System.out.println(EingegebenID);
            try{
                Eiingabe = Integer.parseInt(EingegebenID);
            }
        catch(Exception e){
            System.out.println("Fehler wurde gecatcht");

            JFrame fehlerframe = new JFrame("Fehlermeldung Beispiel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setVisible(true);

            // Zeige eine Fehlermeldung an
            JOptionPane.showMessageDialog(frame,
                    "ID muss eine zahl sein",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
                    updatepanelREGISTER(pwcheck(2, EingabePW));
        }

        for(User u : datenbank.getUserList()){
            if(Eiingabe==u.getId()&&Eiingabe!=0){
                updatepanelREGISTER(pwcheck(1, EingabePW));
            }
        }
        continueREGISTER();
    }
    public int pwcheck(int fehlercode, String EingegebenPW){
        int fehlercode2 = fehlercode;
        if(EingegebenPW.equals("")){
            fehlercode2 = fehlercode + 10;
            System.out.println("passwortleck erkannt");
        }
        return fehlercode2;
    }*/
}