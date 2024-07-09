package src;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JFrametwo {
    private JFrame frame;
    private JPanel panel;
    private JButton button;
    private JButton button2;
    private List<Beispielklasse> myobjectlist;
    private List<JButton> mybuttonlist;
    private JPanel inputpanel;

    public JFrametwo(){
        initalize();
    }

    public void initalize() {
        this.frame = new JFrame();
        this.frame.setTitle("Device manager");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(500,400);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(true);
        this.frame.setVisible(true);

        this.panel = new JPanel();
        this.frame.add(panel, BorderLayout.CENTER);

        //inputpanel
        this.inputpanel = new JPanel();
        this.frame.add(inputpanel, BorderLayout.SOUTH);

        List<Beispielklasse> myobjectlist = new ArrayList<>();
        this.myobjectlist = myobjectlist;
        cdv("iphone3");
        cdv("iphone15pro");

        List<JButton> mybuttonlist = new ArrayList<>();
        this.mybuttonlist = mybuttonlist;
        for(Beispielklasse myobject: this.myobjectlist){
            this.panel.add(new JButton(myobject.getName()));
        }

        }



        public void cdv(String name){
        this.myobjectlist.add(new Beispielklasse(name));
        }

    }

