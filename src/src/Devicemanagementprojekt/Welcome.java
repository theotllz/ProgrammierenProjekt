package src.Devicemanagementprojekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class Welcome {
    private JFrame frame;
    private JPanel WelcomePanel;
    private boolean nevercreatedAdminV;
    private boolean nevercreatedUserV;
    private UserWindow UserV;
    private AdminWindow AdminV;
    private JLabel Admintextpanel;
    private DB Datenbank;

    public Welcome() {
        initialize();
    }

    public void initialize() {

        this.Datenbank = new DB();

        this.frame = new JFrame();
        this.frame.setVisible(true);
        this.frame.setResizable(true);
        this.frame.setTitle("Welcome");
        this.frame.setLocationRelativeTo(null);
        this.frame.setSize(500, 400);
        this.frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        this.nevercreatedAdminV = true;
        this.nevercreatedUserV = true;

        this.WelcomePanel = new JPanel();
        this.frame.add(WelcomePanel, BorderLayout.CENTER);


        JLabel textPane = new JLabel("<html><span style='color: teal;'>"
                + "Hallo Alex,<br>"
                + "hier bitte eine Logik für die Anmeldung implementieren.<br>"
                + "Ich habe eine Logik für die Fenster gemacht, so dass die Knöpfe<br>"
                + "diese Seite verstecken und wenn Admin geschlossen wird,<br>"
                + "wird diese Seite wieder geöffnet.<br>"
                + "Bitte noch mit mir absprechen, wie du die Anmeldung<br>"
                + "und das Management von Personen (Usern) handeln würdest.<br>"
                + "Danke dir,<br>"
                + "Grüße Theo"
                + "diese buttons die hier sind "
                + "werden später automatisch durch den login erkannt"
                + "</span></html>");
        textPane.setFont(textPane.getFont().deriveFont(13));
        this.WelcomePanel.add(textPane);



        //Hardcoded User
        User User1 = new User(1, "Theo", "theodor.telliez@gmail.com", "theospasswort", true);

        //Button und text erstellen und zum Panel hinzufügen
        Admintextpanel = new JLabel(String.valueOf(User1.getAdmin()));
        this.WelcomePanel.add(Admintextpanel);
        JButton makeadmin = new JButton("MakeAdmin");
        this.WelcomePanel.add(makeadmin);

        //change admin status and update text
        makeadmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                User1.changeAdmin();
                Admintextpanel.setText(String.valueOf(User1.getAdmin()));
                WelcomePanel.revalidate();
                WelcomePanel.repaint();
            }
        });


        //kreeirt User oder Admin Window ODER zeigt User oder Admin Window an
        JButton Login = new JButton("Login");
        Login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (User1.getAdmin()) {
                    if (nevercreatedAdminV) {
                        AdminV = new AdminWindow(frame, User1, Datenbank);
                        AdminV.visibility(true, Datenbank);
                    } else {
                        AdminV.visibility(true, Datenbank);
                    }
                }
                if (!User1.getAdmin()) {
                    if (nevercreatedUserV) {
                        UserV = new UserWindow(frame, User1, Datenbank);
                    } else {
                        UserV.visibility(true);
                    }
                }

            }
        });

        //stellvertretend für Login mechanismus
        this.WelcomePanel.add(Login);

    }


    //Um Welcome Fenster von außen sichtbar zu machen
    public void WelcomeVisible() {
        this.frame.setVisible(true);
    }

}
