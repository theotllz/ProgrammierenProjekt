package src.Devicemanagementprojekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class openDetails {
    JFrame frame;
    public openDetails(Device device) {
        frame = new JFrame("Details for " + device.getName());
        openDetailsWindow(device);
    }

    public void openDetailsWindow(Device device) {
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
    }

    public void detailsvisible(Device device){
        openDetailsWindow(device);
        frame.setVisible(true);
    }

    public void detailsinvisible(){
        frame.setVisible(false);
    }
}





