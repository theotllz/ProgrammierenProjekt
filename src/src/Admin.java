/*package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class Admin {
    private JFrame frame;
    private JPanel panel;

    private List<Device> myobjectlist;

    private JPanel inputpanel;

    public Admin(JFrame welcomeframe) {
        initalize(welcomeframe);
    }

    public void initalize(JFrame welcomeframe) {
        //Frame wird erstellt:
        this.frame = new JFrame();
        this.frame.setVisible(false);
        this.frame.setResizable(true);
        this.frame.setTitle("Admin Window");
        this.frame.setLocationRelativeTo(null);
        this.frame.setSize(500, 400);

        //Panel wird erstellt:
        this.panel = new JPanel();
        this.frame.add(panel, BorderLayout.CENTER);

        //Wartet auf schließen und ruft dann die close Methode auf:
        //->Admin unsichtbar machen und Welcome Frame sichtbar machen
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close(welcomeframe);
            }
        });

        //erstellung der Gerätliste
        List<Device> myobjectlist = new ArrayList<>();
        this.myobjectlist = myobjectlist;

        //Gerät hinzufügen Button mit listener welcher addDevice aufruft
        JButton addDeviceButton = new JButton("Add Device");
        addDeviceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDevice();
            }
        });
        frame.add(addDeviceButton, BorderLayout.SOUTH);
    }

    //Gerät hinzufügen Methode aufgerufen siehe oben
    private void addDevice() {
        String deviceName = JOptionPane.showInputDialog(this, "Enter device name:");
        if (deviceName != null && !deviceName.trim().isEmpty()) {
            Device device = new Device(deviceName);
            myobjectlist.add(device);
            updateDevicePanel();
        }
    }

    //Leert das panel und füllt alles von der Liste neu ein
    private void updateDevicePanel() {
        panel.removeAll();
        for (Device device : myobjectlist) {
            JPanel deviceRow = new JPanel();
            deviceRow.setLayout(new BorderLayout());

            JLabel deviceNameLabel = new JLabel(device.getName());
            JButton verfügbarkeitaendernBT = new JButton("?");
            if (device.getVerfuegbarkeit()) {
                deviceNameLabel = new JLabel(device.getName() + " verfügbar");
                verfügbarkeitaendernBT = new JButton("Ausleihen");
            } else {
                deviceNameLabel = new JLabel(device.getName() + " ausgeliehen von " + device.getAusleiher());
                verfügbarkeitaendernBT = new JButton("Zurücgeben");
            }


            JButton removeButton = new JButton("Remove");
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    removeDevice(device);
                }
            });

            verfügbarkeitaendernBT.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    verfügbarkeitaendern(device);
                }
            });

            deviceRow.add(verfügbarkeitaendernBT, BorderLayout.WEST);
            deviceRow.add(deviceNameLabel, BorderLayout.CENTER);
            deviceRow.add(removeButton, BorderLayout.EAST);
            panel.add(deviceRow);
        }


        panel.revalidate();
        panel.repaint();
    }

    //entfernt das aus dem button (siehe oben) übergebene Objekt aus
    //der Liste und beim updaten wird der button nicht neu angezeigt
    private void removeDevice(Device device) {
        myobjectlist.remove(device);
        updateDevicePanel();
    }

    private void verfügbarkeitaendern(Device device) {
        if (device.getVerfuegbarkeit()) {
            device.setVerfuegbarkeit(false, 2948);
        } else
            device.setVerfuegbarkeit(true,1938 );

        updateDevicePanel();
    }


    //kann das Admin fenster von außerhalb verfügbar machen
    public void visible() {
        frame.setVisible(true);
    }

    //methode welche beim schließen aufgerufen wird
    //->Admin unsichtbar machen und Welcome Frame sichtbar machen
    public int close(JFrame welcomeframe) {
        this.frame.setVisible(false);
        welcomeframe.setVisible(true);
        return 0;
    }


}
*/

