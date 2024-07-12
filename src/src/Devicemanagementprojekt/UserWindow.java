package src.Devicemanagementprojekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserWindow {
    private JFrame frame;
    private JPanel ADevicePanel;
    private User user;


    public UserWindow(JFrame welcomeframe, User user, DB Datenbank) {
        initialize(welcomeframe, user, Datenbank);
    }


    public void initialize(JFrame welcomeframe, User user, DB Datenbank) {
        this.user = user;
        this.frame = new JFrame();
        this.frame.setSize(400, 400);
        this.frame.setTitle("Customer view");

        // Exit code
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close(welcomeframe);
            }
        });
        JPanel exit = new JPanel();
        frame.add(exit, BorderLayout.NORTH);
        JButton exitBT = new JButton("Exit");
        exitBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close(welcomeframe);
            }
        });
        exit.add(exitBT);


        // DevicePanel
        ADevicePanel = new JPanel();
        ADevicePanel.setLayout(new BoxLayout(ADevicePanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical stacking
        updateDevicePanel(Datenbank);
        frame.add(new JScrollPane(ADevicePanel), BorderLayout.CENTER);
        frame.setVisible(true);
    }


    public void updateDevicePanel(DB Datenbank) {
        ADevicePanel.removeAll();
        // For each device in the list
        for (Device device : Datenbank.getDeviceList()) {
            //Panel mit einem Gerät
            JPanel deviceRow = new JPanel();
            deviceRow.setLayout(new BorderLayout());

            //Gerät Bezeichung
            JLabel deviceNameLabel = new JLabel(device.getName());
            deviceRow.add(deviceNameLabel, BorderLayout.WEST);

            //
            if (device.getVerfuegbarkeit()) {
                JButton ausleihenButton = new JButton("Ausleihen");
                ausleihenButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        device.Ausleihen(user.getId());
                        updateDevicePanel(Datenbank);
                    }
                });
                deviceRow.add(ausleihenButton, BorderLayout.EAST);
            } else if (device.getAusleiher() == user.getId()) {
                JButton zurueckgebenButton = new JButton("Zurückgeben");
                zurueckgebenButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        device.Rückgabe();
                        updateDevicePanel(Datenbank);
                    }
                });
                deviceRow.add(zurueckgebenButton, BorderLayout.EAST);
            }

            ADevicePanel.add(deviceRow);
        }

        // Update Panel
        ADevicePanel.revalidate();
        ADevicePanel.repaint();
    }


    private void close(JFrame welcomeframe) {
        this.frame.setVisible(false);
        welcomeframe.setVisible(true);
    }


    public void visibility(boolean visible) {
        this.frame.setVisible(visible);
    }
}
