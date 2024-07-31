package src.Devicemanagementprojekt;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.Period;

import static java.awt.BorderLayout.*;

class RoundedPanel extends JPanel {
    private int cornerRadius;

    public RoundedPanel(int radius) {
        super();
        cornerRadius = radius;
        setOpaque(false);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        graphics.setColor(getForeground());
    }
}

public class UserWindow {
    private JFrame frame;
    private JPanel ADevicePanel;
    private User user;

    public UserWindow(JFrame welcomeframe, User user, DB Datenbank) {
        initialize(welcomeframe, user, Datenbank);
    }

    public void initialize(JFrame welcomeframe, User user, DB Datenbank) {
        //user speichern und frame erstellen
        {this.user = user;
        this.frame = new JFrame();
        this.frame.setSize(740, 570);
        this.frame.setTitle("Customer View");
        this.frame.setLayout(new BorderLayout());}

        // Exit code
        {
            this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            this.frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    close(welcomeframe);
                }
            });
        }

        // Top panel mit Titel und Ausloggen button

        JPanel topPanel = new JPanel(new BorderLayout());{
            JButton exitBT = new JButton("Ausloggen");
            exitBT.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    close(welcomeframe);
                }
            });
            topPanel.add(exitBT, WEST);}


        JLabel titleLabel = new JLabel("Geräteübersicht", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel, CENTER);

        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(topPanel, NORTH);

        // DevicePanel
        ADevicePanel = new JPanel();
        ADevicePanel.setLayout(new GridLayout(0, 3, 15, 20)); // GridLayout for square frames
        updateDevicePanel(Datenbank);
        JScrollPane scrollPane = new JScrollPane(ADevicePanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        frame.add(scrollPane, CENTER);
        frame.setVisible(true);
    }

    public void updateDevicePanel(DB Datenbank) {
        ADevicePanel.removeAll();
        // Für jedes gerät in der Liste
        for (Device device : Datenbank.getDeviceList()) {
            //Rundes Panel für jedes Gerät
            RoundedPanel devicePanel = new RoundedPanel(25);
            devicePanel.setLayout(new BorderLayout(15, 20));
            devicePanel.setBackground(new Color(71, 71, 71));
            devicePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Feste Größe für jedes Gerätepanel
            Dimension fixedSize = new Dimension(229, 234);
            devicePanel.setPreferredSize(fixedSize);
            devicePanel.setMinimumSize(fixedSize);
            devicePanel.setMaximumSize(fixedSize);

            // Gerätenamelabel
            JLabel deviceNameLabel = new JLabel(device.getName());
            deviceNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            devicePanel.add(deviceNameLabel, NORTH);

            //Panel für Notiz und NotizenButton
            RoundedPanel DetailsandDetails = new RoundedPanel(20);
            DetailsandDetails.setBackground(new Color(151, 151, 151));
            DetailsandDetails.setLayout(new GridLayout(2, 0, 10, 10));
            DetailsandDetails.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            //Notiz
            JLabel note = new JLabel(device.getNotizen());
            note.setFont(new Font("Arial", Font.BOLD, 12));
            DetailsandDetails.add(note, NORTH);
            openDetailsUs details = new openDetailsUs(device);
            // Details button
            JButton detailsButton = new JButton("Details");
            detailsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    details.detailsvisible(device);
                }
            });
            DetailsandDetails.add(detailsButton, BorderLayout.CENTER);

            DetailsandDetails.setVisible(true);
            devicePanel.add(DetailsandDetails, BorderLayout.CENTER);

            // Ausleihen/Rückgabe Button
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BorderLayout());
            buttonPanel.setPreferredSize(new Dimension(50, 50));
            buttonPanel.setSize(new Dimension(50, 50));
            if (device.getVerfuegbarkeit()) {
                JButton ausleihenButton = new JButton("Ausleihen");
                ausleihenButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (openLoanPeriod(device)) {
                            device.Ausleihen(user.getUsername());
                            updateDevicePanel(Datenbank);
                        }
                    }
                });
                buttonPanel.add(ausleihenButton, BorderLayout.CENTER);
            } else if (device.getAusleiher().equals(user.getUsername())) {
                JButton zurueckgebenButton = new JButton("Zurückgeben");
                zurueckgebenButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        closeLoanPeriod(device);
                        device.Rückgabe();
                        updateDevicePanel(Datenbank);
                    }
                });

                buttonPanel.add(zurueckgebenButton, BorderLayout.CENTER);
            } else {
                JButton uverfügbarBT = new JButton("Nicht verfügbar");
                buttonPanel.add(uverfügbarBT, BorderLayout.CENTER);
            }

            devicePanel.add(buttonPanel, BorderLayout.SOUTH);

            ADevicePanel.add(devicePanel);
        }

        // Update Panel
        ADevicePanel.revalidate();
        ADevicePanel.repaint();
    }


        public boolean openLoanPeriod(Device device){
            boolean validInput = false;
            int number = 0;
            while (!validInput) {
                String input = JOptionPane.showInputDialog(null, "Bitte geben Sie an wie lange Sie das Gerät ausleihen wollen:", "Eingabe", JOptionPane.QUESTION_MESSAGE);

                try {
                    if (input != null) { // Überprüfen, ob der Benutzer nicht auf Abbrechen geklickt hat
                        number = Integer.parseInt(input);
                        if(number>0) {
                            validInput = true;
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Eingabe abgebrochen oder falsch.", "Abbruch", JOptionPane.INFORMATION_MESSAGE);
                            return false;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Eingabe abgebrochen oder falsch.", "Abbruch", JOptionPane.INFORMATION_MESSAGE);
                        return false;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Ungültige Eingabe. Bitte geben Sie eine gültige ganze Zahl ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (validInput) {
                JOptionPane.showMessageDialog(null, "Sie haben " + number + " eingegeben.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                LocalDate now = LocalDate.now();
                device.setAusleihDauer(number);
                device.setAusleihTag(now);
                return true;
            }
            return true;

        }
        public void closeLoanPeriod(Device device){
            LocalDate now = LocalDate.now();
            device.setRückgabeTag(now);
            Period period = Period.between(device.getAusleihTag(), device.getRückgabeTag());
            int dauer = period.getDays() + period.getMonths() * 30 + period.getYears() * 365;
            if (dauer > device.getAusleihDauer()) {
                JOptionPane.showMessageDialog(null, "Sie haben das Gerät zu lange ausgeliegen. Bitte wenden Sie sich an einen Mitarbeiter", "Überzogen", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Vielen Dank!", "Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
            }
        }



    private void close(JFrame welcomeframe) {
        this.frame.setVisible(false);
        welcomeframe.setVisible(true);
    }

    public void visibility(boolean visible, User user) {
        this.frame.setVisible(visible);
        this.user = user;
    }
}
