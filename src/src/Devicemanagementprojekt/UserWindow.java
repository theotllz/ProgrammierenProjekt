package src.Devicemanagementprojekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Custom rounded panel class
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
        this.user = user;
        this.frame = new JFrame();
        this.frame.setSize(740, 570);
        this.frame.setTitle("Customer View");
        this.frame.setLayout(new BorderLayout());

        // Exit code
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close(welcomeframe);
            }
        });

        // Top panel with title and exit button
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton exitBT = new JButton("Exit");
        exitBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close(welcomeframe);
            }
        });
        topPanel.add(exitBT, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Geräteübersicht", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(topPanel, BorderLayout.NORTH);

        // DevicePanel
        ADevicePanel = new JPanel();
        ADevicePanel.setLayout(new GridLayout(0, 3, 15, 20)); // GridLayout for square frames
        updateDevicePanel(Datenbank);
        JScrollPane scrollPane = new JScrollPane(ADevicePanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void updateDevicePanel(DB Datenbank) {
        ADevicePanel.removeAll();
        // For each device in the list
        for (Device device : Datenbank.getDeviceList()) {
            // Create a custom rounded panel for each device
            RoundedPanel devicePanel = new RoundedPanel(25);
            devicePanel.setLayout(new BorderLayout(15, 20));
            devicePanel.setBackground(new Color(71, 71, 71));
            devicePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Set fixed size for the device panel
            Dimension fixedSize = new Dimension(229, 234);
            devicePanel.setPreferredSize(fixedSize);
            devicePanel.setMinimumSize(fixedSize);
            devicePanel.setMaximumSize(fixedSize);

            // Device name label
            JLabel deviceNameLabel = new JLabel(device.getName());
            deviceNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            devicePanel.add(deviceNameLabel, BorderLayout.NORTH);

            //Panel für Notiz und NotizenButton
            RoundedPanel DetailsandDetails = new RoundedPanel(20);
            DetailsandDetails.setBackground(new Color(151, 151, 151));
            DetailsandDetails.setLayout(new GridLayout(2, 0, 10, 10));
            DetailsandDetails.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            //Note
            JLabel note = new JLabel(device.getNotizen());
            note.setFont(new Font("Arial", Font.BOLD, 12));
            DetailsandDetails.add(note, BorderLayout.NORTH);

            // Details button
            JButton detailsButton = new JButton("Details");
            detailsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openDetailsWindow(device);
                }
            });
            DetailsandDetails.add(detailsButton, BorderLayout.CENTER);

            DetailsandDetails.setVisible(true);
            devicePanel.add(DetailsandDetails, BorderLayout.CENTER);

            // Rent/Return button
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BorderLayout());
            buttonPanel.setPreferredSize(new Dimension(50, 50));
            buttonPanel.setSize(new Dimension(50, 50));
            if (device.getVerfuegbarkeit()) {
                JButton ausleihenButton = new JButton("Ausleihen");
                ausleihenButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        device.Ausleihen(user.getId());
                        updateDevicePanel(Datenbank);
                    }
                });
                buttonPanel.add(ausleihenButton, BorderLayout.CENTER);
            } else if (device.getAusleiher() == user.getId()) {
                JButton zurueckgebenButton = new JButton("Zurückgeben");
                zurueckgebenButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        device.Rückgabe();
                        updateDevicePanel(Datenbank);
                    }
                });
                buttonPanel.add(zurueckgebenButton, BorderLayout.CENTER);
            }
            devicePanel.add(buttonPanel, BorderLayout.SOUTH);

            ADevicePanel.add(devicePanel);
        }

        // Update Panel
        ADevicePanel.revalidate();
        ADevicePanel.repaint();
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

        // Create a panel to hold both the label and the notes
        JPanel notesPanel = new JPanel();
        notesPanel.setLayout(new BorderLayout());
        JLabel notesLabel = new JLabel("Notes:");
        JLabel detailsText = new JLabel("<html>" + device.getNotizen().replaceAll("\n", "<br>") + "</html>");
        notesPanel.add(notesLabel, BorderLayout.NORTH);
        notesPanel.add(detailsText, BorderLayout.CENTER);

        // Create a panel to hold both the label and the price
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BorderLayout());
        JLabel priceLabel = new JLabel("Price:");
        JLabel neuPreis = new JLabel(device.getNeuPreis());
        pricePanel.add(priceLabel, BorderLayout.NORTH);
        pricePanel.add(neuPreis, BorderLayout.CENTER);

        // Add the notes and price panels to the details and price panels
        detailsPanel.add(notesPanel, BorderLayout.CENTER);
        preisPanel.add(pricePanel, BorderLayout.CENTER);

        // Add the details and price panels to the frame
        frame.add(detailsPanel, BorderLayout.NORTH);
        frame.add(preisPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void close(JFrame welcomeframe) {
        this.frame.setVisible(false);
        welcomeframe.setVisible(true);
    }

    public void visibility(boolean visible) {
        this.frame.setVisible(visible);
    }
}
