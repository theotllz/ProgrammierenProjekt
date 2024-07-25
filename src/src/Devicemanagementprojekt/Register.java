package src.Devicemanagementprojekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Register {
    private JFrame frame;
    private JPanel panel;
    private DB datenbank;

    public Register(DB datenbank) {
        this.datenbank = datenbank;
        frame = new JFrame();
        panel = new JPanel(new GridLayout());
        updatepanel(05);
    }

    public void updatepanel(int fehlercode) {
        panel.removeAll();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create components
        JLabel aufforderungName = new JLabel("Name eingeben: ");
        JTextField NameEingabe = new JTextField(20);
        JLabel aufforderungIDLabel = new JLabel("Username:");
        JTextField IDeingabe = new JTextField(20);
        JLabel aufforderungPWLabel = new JLabel("Passwort erstellen:");
        JPasswordField setPW = new JPasswordField(20);
        JButton registerButton = new JButton("Registrieren");


        // Adjust grid bag constraints for each component

        // Name Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Margin around the components
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(aufforderungName, gbc);

        // Name Eingabe
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(NameEingabe, gbc);

        // Id Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10,10); // Margin around the components
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(aufforderungIDLabel, gbc);


        // Id Eingabe
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(IDeingabe, gbc);

        // Password Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(aufforderungPWLabel, gbc);

        // Password Eingabe
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(setPW, gbc);

        // Register Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, gbc);

        frame.add(panel, BorderLayout.CENTER);
        frame.pack(); // Adjust the frame size to fit its contents
        frame.setVisible(true);
        frame.setSize(400, 200); // Adjust size as needed


        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                idcheck(IDeingabe.getText(), setPW.getText());
            }
        });
    }

    public void idcheck(String EingabeUsN, String EingabePW){
        int fehlercode = 2;
        for(User u : datenbank.getUserList()){
            System.out.println("durchgang check");
            if(!EingabeUsN.equals(u.getUsername()) && !EingabeUsN.equals("0") && !(EingabeUsN.equals("12437"))){
                fehlercode = 1;
            }
        }
        if(fehlercode == 1){

        }
        checkpw(EingabePW, fehlercode);
    }

    public int checkpw(String EingabePW,int fehlercode){
        if(EingabePW.equals("")){
            fehlercode = fehlercode + 10;
        }
        System.out.println(fehlercode);
        return fehlercode;
    }

}
