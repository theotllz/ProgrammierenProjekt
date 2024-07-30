package src.Devicemanagementprojekt;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminWindow {
    private JFrame frame;
    private JPanel DevicePanel;
    private JPanel StaticPanel;
    private JPanel exit;
    private DB Datenbank;
    private User user;

    public AdminWindow(JFrame welcomeframe, User user, DB Datenbank) {
        initalize(welcomeframe, user, Datenbank);
    }


    public void initalize(JFrame welcomeframe, User user, DB Datenbank) {
        this.user = user;

        frame = new JFrame();
        frame.setVisible(true);
        frame.setTitle("Admin Ansicht");
        frame.setSize(600, 400);

        //zurück zu Welcome bei schließen
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close(frame, welcomeframe);
            }
        });

        StaticPanel = new JPanel();
        frame.add(StaticPanel, BorderLayout.SOUTH);
        DevicePanel = new JPanel();
        this.Datenbank = Datenbank;
        updateDevicePanel(this.Datenbank);

        exit = new JPanel();
        frame.add(exit, BorderLayout.NORTH);
        frame.add(DevicePanel, BorderLayout.CENTER);

        //exit button
        JButton exitBT = new JButton("Exit");
        exitBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close(frame, welcomeframe);
            }
        });
        exit.add(exitBT);


        JButton addDeviceButton = new JButton("Add Device");
        addDeviceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewDevice(Datenbank);
            }
        });
        JButton userlistBT = new JButton("Print Users");
        userlistBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(User user1 : Datenbank.getUserList()){
                    System.out.println(user1.getUsername().toString());
                    System.out.println(user1.getPassword());
                }
            }
        });
        StaticPanel.add(userlistBT);
        StaticPanel.add(addDeviceButton);


    }


    public void updateDevicePanel(DB Datenbank) {
        this.DevicePanel.removeAll();
        //für jedes Gerät folgende iteration
        for (Device device : Datenbank.getDeviceList()) {
            openDetails details = new openDetails(device);
            //neues Panel erstellen
            JPanel deviceRow = new JPanel();
            deviceRow.setLayout(new BorderLayout());

            String Ausleihstatus = "";
            String Name = "";

            String ausleiher = device.getAusleiher();
            if (ausleiher != null && !(ausleiher.equals("12437")) && !(ausleiher.equals("0"))) {
                for (User user : Datenbank.getUserList()) {
                    if (ausleiher.equals(user.getUsername())) {
                        Name = user.getName();
                    }
                }
                Ausleihstatus = " ausgeliehen von " + Name;
            } else {
                Ausleihstatus = " ist verfügbar";
            }




            JLabel deviceNameLabel = new JLabel(device.getName() + Ausleihstatus);
            if (Ausleihstatus.equals(" ist verfügbar")) {
                deviceNameLabel.setForeground(new Color(27, 87, 0));
            } else {
                deviceNameLabel.setForeground(Color.RED);
            }
            deviceRow.add(deviceNameLabel, BorderLayout.WEST);
            boolean opendw = false;
            //Details Fenster
            //Details Fenster
            JButton openDetails = new JButton("Open Details");
            openDetails.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    details.detailsvisible(device);
                }
            });
            deviceRow.add(openDetails, BorderLayout.CENTER);

            //Remove button
            JButton removeButton = new JButton("Removedevice");
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeDevice(Datenbank, device);
                }
            });
            deviceRow.add(removeButton, BorderLayout.EAST);


            //zusammenführen
            DevicePanel.add(deviceRow);
        }

        //Update Panel
        DevicePanel.revalidate();
        DevicePanel.repaint();
    }


    private void createNewDevice(DB Datenbank) {
        String deviceName = JOptionPane.showInputDialog(frame, "Enter device name:");
        if (deviceName != null && !deviceName.trim().isEmpty()) {
            Device newDevice = new Device(deviceName);
            Datenbank.getDeviceList().add(newDevice);
            updateDevicePanel(Datenbank);
        } else {
            JOptionPane.showMessageDialog(frame, "Device name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeDevice(DB Datenbank, Device device) {
        Datenbank.getDeviceList().remove(device);
        updateDevicePanel(Datenbank);
    }

    private void close(JFrame frame, JFrame welcomeframe) {
        frame.setVisible(false);
        welcomeframe.setVisible(true);
    }

    public void visibility(boolean visible) {
        this.frame.setVisible(visible);
        updateDevicePanel(this.Datenbank);
    }




}


