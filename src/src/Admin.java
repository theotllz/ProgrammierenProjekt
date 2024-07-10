package src;

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
        //Frame
        this.frame = new JFrame();
        this.frame.setTitle("Admin Window");
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Call your custom method
                close(welcomeframe);
            }
        });
        this.frame.setSize(500, 400);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(true);
        this.frame.setVisible(false);

        //Panelerstellung
        this.panel = new JPanel();
        this.frame.add(panel, BorderLayout.CENTER);


        //Objektspeicherung
        List<Device> myobjectlist = new ArrayList<>();
        this.myobjectlist = myobjectlist;


        JButton addDeviceButton = new JButton("Add Device");
        addDeviceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDevice();
            }
        });
        frame.add(addDeviceButton, BorderLayout.SOUTH);
    }

    private void addDevice() {
        String deviceName = JOptionPane.showInputDialog(this, "Enter device name:");
        if (deviceName != null && !deviceName.trim().isEmpty()) {
            Device device = new Device(deviceName);
            myobjectlist.add(device);
            updateDevicePanel();
        }
    }



    private void updateDevicePanel () {
        panel.removeAll();

        for (Device device : myobjectlist) {
            JPanel deviceRow = new JPanel();
            deviceRow.setLayout(new BorderLayout());
            JLabel deviceLabel = new JLabel(device.getName());
            JButton removeButton = new JButton("Remove");
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    removeDevice(device);
                }
            });
            deviceRow.add(deviceLabel, BorderLayout.CENTER);
            deviceRow.add(removeButton, BorderLayout.EAST);
            panel.add(deviceRow);
        }


        panel.revalidate();
        panel.repaint();
    }

    private void removeDevice(Device device) {
        myobjectlist.remove(device);
        updateDevicePanel();
    }

    public void visible(){
        frame.setVisible(true);
    }

    public int close(JFrame welcomeframe){
        this.frame.setVisible(false);
        welcomeframe.setVisible(true);
        return 0;
    }
}


