package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Admin {
    private JFrame frame;
    private JPanel panel;

    private List<Device> myobjectlist;
    private List<JButton> mybuttonlist;

    private JPanel inputpanel;
    private JButton createdevice;
    private JTextField Devname;

    public Admin() {
        initalize();
    }

    public void initalize() {
        //Frame
        this.frame = new JFrame();
        this.frame.setTitle("Device manager");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(500, 400);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(true);
        this.frame.setVisible(true);

        //Panelerstellung
        this.panel = new JPanel();
        this.frame.add(panel, BorderLayout.CENTER);

        /*//inputpanel
        this.inputpanel = new JPanel();
        this.frame.add(inputpanel, BorderLayout.SOUTH);
        this.createdevice = new JButton("Createdevice");
        this.Devname = new JTextField("hier gerätname");

        Devname.setPreferredSize(new Dimension(200, 30));
        this.inputpanel.add(Devname, BorderLayout.WEST);
        this.inputpanel.add(createdevice);*/

        /*//actionlistener
        createdevice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("button clicked");
            }
        });*/

        //Objektspeicherung
        List<Device> myobjectlist = new ArrayList<>();
        this.myobjectlist = myobjectlist;

        //Beispiel


        //Buttons
        List<JButton> mybuttonlist = new ArrayList<>();
        this.mybuttonlist = mybuttonlist;
        //for (Device myobject : this.myobjectlist) {
        //    this.panel.add(new JButton(myobject.getName()));
        //}

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
}


