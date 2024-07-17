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

    public Welcome() {
        initialize();
    }

    public void initialize() {
        //läd informationen von serialisierter Datei oder erstellt neue DB
        loadDatabase();
        userCreate();

        //nevercreated buttons
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
        topLeftPanel.add(saveAndCloseButton);

        // Panel for center controls
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        // Hardcoded User
        User user1 = new User(1, "Theo", "theodor.telliez@gmail.com", "theospasswort", true);
        User user2 = new User(2, "alex", "Alexwalosycxysyk@gmail.com", "alexspasswort", false);
        datenbank.getUserList().add(user1);
        datenbank.getUserList().add(user2);

        // Admin text panel
        adminTextPanel = new JLabel(String.valueOf(user1.getAdmin()));
        adminTextPanel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(adminTextPanel);

        // Make Admin button
        JButton makeAdminButton = new JButton("MakeAdmin");
        //Formatierung
        makeAdminButton.setBackground(Color.BLUE); // Set button background color
        makeAdminButton.setForeground(Color.DARK_GRAY); // Set text color
        makeAdminButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        //Actionlistener
        makeAdminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //ändert adminastatus
                user1.changeAdmin();
                adminTextPanel.setText(String.valueOf(user1.getAdmin()));
            }
        });
        centerPanel.add(makeAdminButton);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.GREEN); // Set button background color
        loginButton.setForeground(Color.DARK_GRAY); // Set text color
        loginButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (user1.getAdmin()) {
                    if (nevercreatedAdminV) {
                        adminV = new AdminWindow(frame, user1, datenbank);
                        adminV.visibility(true);
                    } else {
                        adminV.visibility(true);
                    }
                } else {
                    if (nevercreatedUserV) {
                        userV = new UserWindow(frame, user1, datenbank);
                    } else {
                        userV.visibility(true, user1);
                    }
                }
                frame.setVisible(false); // Hide the Welcome window after login
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

    //Methode zur erstellung eines neuen nutzers
    private void userCreate() {

        Registerframe = new JFrame("Benutzer erstellen");
        Registerframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Registerpanel = new JPanel(new GridBagLayout());
        Registerframe.add(Registerpanel);
        updatepanelREGISTER(false);
    }

    private void updatepanelREGISTER(boolean bereitsvergebenID){
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

        // Adjust grid bag constraints for each component

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

        //bereitsvergeben
        if(bereitsvergebenID){
            gbc.gridx = i;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5); // Margin around the components
            gbc.anchor = GridBagConstraints.WEST;
            Registerpanel.add(namenichtverfügbarmeldung, gbc);
            ;
        }

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

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(idcheck(IDeingabe.getText())){
                    updatepanelREGISTER(true);
                }
                else{

                }
            }
        });
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

    public boolean idcheck(String EingegebenID){
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
                    "Gib bitte Text ein",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
                    updatepanelREGISTER(false);
            return false;
        }

        for(User u : datenbank.getUserList()){
            if(Eiingabe==u.getId()&&Eiingabe!=0){
                return true;
            }
        }
        return false;
    }
}