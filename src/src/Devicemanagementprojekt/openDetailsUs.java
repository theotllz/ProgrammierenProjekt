package src.Devicemanagementprojekt;

import javax.swing.*;
import java.awt.*;

public class openDetailsUs {
    JFrame frame;
    public openDetailsUs(Device device) {
        frame = new JFrame("Details for " + device.getName());
        frame.setSize(400, 120);
        openDetailsWindow(device);
    }
    private void openDetailsWindow(Device device) {
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

        frame.setVisible(false);
    }

    public void detailsvisible(Device device){
        openDetailsWindow(device);
        frame.setVisible(true);
    }

}
