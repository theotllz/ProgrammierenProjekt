package src.Devicemanagementprojekt;


import javax.swing.*;

public class
Gerätemanagement {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                new Welcome();

            }
        });

    }
}
