package src;

import javax.swing.*;

public class JFrameDemo {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                JFrametwo frame = new JFrametwo();

            }
        });

    }
}


