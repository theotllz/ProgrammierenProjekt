package src;

import javax.swing.*;

public class JFrameDemo {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                Admin frame = new Admin();


            }
        });

    }
}


