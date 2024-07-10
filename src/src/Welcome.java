package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome {
    private JFrame frame;
    private JPanel panel;
    private Admin Adminframe;

    public Welcome() {
        initialize();
    }

    public void initialize() {
        this.frame = new JFrame();
        this.frame.setTitle("Welcome");
        this.frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.frame.setSize(500, 400);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(true);
        this.frame.setVisible(true);
        this.Adminframe = new Admin(frame);


        this.panel = new JPanel();
        this.frame.add(panel, BorderLayout.CENTER);

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
                + "</span></html>");
        textPane.setFont(textPane.getFont().deriveFont(13));
        this.panel.add(textPane);


        JButton openadminBT = new JButton("Open Admin");
        openadminBT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Adminframe.visible();
                frame.setVisible(false);
            }
        });

        //noch zu implementieren!
        JButton userBT = new JButton("User");

        this.panel.add(openadminBT);
        this.panel.add(userBT);
    }

    //Um Welcome Fenster von außen sichtbar zu machen
    public void WelcomeVisible() {
        this.frame.setVisible(true);
    }
}
