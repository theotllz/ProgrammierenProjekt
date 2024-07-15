package src.Devicemanagementprojekt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Welcome {
    private JFrame frame;
    private JPanel welcomePanel;
    private boolean nevercreatedAdminV;
    private boolean nevercreatedUserV;
    private UserWindow userV;
    private AdminWindow adminV;
    private JLabel adminTextPanel;
    private DB datenbank;
    private static final String DATABASE_FOLDER = "database";
    private static final String DATABASE_FILE = "database.ser";

    public Welcome() {
        initialize();
    }

    public void initialize() {
        loadDatabase(); // Initialize your database connection

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
        saveAndCloseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add save logic if needed
                try{
                    saveDatabase();
                }
                catch(Exception ex){
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
        User user1 = new User(1, "Theo", "theodor.telliez@gmail.com", "theospasswort", true);

        // Admin text panel
        adminTextPanel = new JLabel(String.valueOf(user1.getAdmin()));
        adminTextPanel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(adminTextPanel);

        // Make Admin button
        JButton makeAdminButton = new JButton("MakeAdmin");
        makeAdminButton.setBackground(Color.BLUE); // Set button background color
        makeAdminButton.setForeground(Color.DARK_GRAY); // Set text color
        makeAdminButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        makeAdminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
                        adminV.visibility(true, datenbank);
                    } else {
                        adminV.visibility(true, datenbank);
                    }
                } else {
                    if (nevercreatedUserV) {
                        userV = new UserWindow(frame, user1, datenbank);
                    } else {
                        userV.visibility(true);
                    }
                }
                frame.setVisible(false); // Hide the Welcome window after login
            }
        });
        centerPanel.add(loginButton);

        welcomePanel.add(centerPanel, BorderLayout.CENTER);

        // Set initial visibility flags
        nevercreatedAdminV = true;
        nevercreatedUserV = true;

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

    private String getRunningFilePath() {
        try {
            String path = Welcome.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            return new File(path).getParent();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}