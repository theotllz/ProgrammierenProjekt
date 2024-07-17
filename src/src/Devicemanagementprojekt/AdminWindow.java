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
        frame.setTitle("Manager");
        frame.setSize(600, 400);
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
        StaticPanel.add(addDeviceButton);


    }


    public void updateDevicePanel(DB Datenbank) {
        this.DevicePanel.removeAll();
        //für jedes Gerät folgende iteration
        for (Device device : Datenbank.getDeviceList()) {

            //neues Panel erstellen
            JPanel deviceRow = new JPanel();
            deviceRow.setLayout(new BorderLayout());

            //Devicenamelabel
            String Ausleihstatus = "";
            String Name = "";
            if (device.getAusleiher() != 12437 && device.getAusleiher() != 0) {
                System.out.println("in der if abfrage");
                for (User user : Datenbank.getUserList()) {
                    System.out.println("in der for schleife");
                    if (device.getAusleiher() == user.getId()) {
                        Name = user.getName();
                        System.out.println(Name);
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

            //Details Fenster
            //Details Fenster
            JButton openDetails = new JButton("Open Details");
            openDetails.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    openDetailsWindow(device);
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

    private void openDetailsWindow(Device device) {
        JFrame frame = new JFrame("Details for " + device.getName());
        frame.setSize(400, 120);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());
        JPanel preisPanel = new JPanel();
        preisPanel.setLayout(new BorderLayout());

        // Create a panel to hold both the label and the text pane for notes
        JPanel notesPanel = new JPanel();
        notesPanel.setLayout(new BorderLayout());
        JLabel notesLabel = new JLabel("Notes:");
        JTextPane detailsText = new JTextPane();
        detailsText.setText(device.getNotizen());
        detailsText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (detailsText.getText().equals("/")) {
                    detailsText.selectAll();
                }
            }
        });
        notesPanel.add(notesLabel, BorderLayout.NORTH);
        notesPanel.add(new JScrollPane(detailsText), BorderLayout.CENTER);

        // Create a panel to hold both the label and the text pane for price
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BorderLayout());
        JLabel priceLabel = new JLabel("Price:");
        JTextPane neuPreis = new JTextPane();
        neuPreis.setText(device.getNeuPreis());
        neuPreis.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (neuPreis.getText().equals("/")) {
                    neuPreis.selectAll();
                }
            }
        });
        pricePanel.add(priceLabel, BorderLayout.NORTH);
        pricePanel.add(new JScrollPane(neuPreis), BorderLayout.CENTER);

        // Add the note and price panels to the details and price panels
        detailsPanel.add(notesPanel, BorderLayout.CENTER);
        preisPanel.add(pricePanel, BorderLayout.CENTER);

        // Add the details and price panels to the frame
        frame.add(detailsPanel, BorderLayout.NORTH);
        frame.add(preisPanel, BorderLayout.SOUTH);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                device.setNotizen(detailsText.getText());
                device.setNeuPreis(neuPreis.getText());
            }
        });

        frame.setVisible(true);
    }


}


