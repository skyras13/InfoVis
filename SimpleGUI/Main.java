package com.company;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private Vis contents;

    public Main() {
        //setting up the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setting size of the window
        setSize(400,400);
        //how to change the title of window
        setTitle("House Landscapes");
        contents = new Vis();
        setContentPane(contents);
        var abigail = createMenu();
        setJMenuBar(abigail);
        setVisible(true);
    }

    //method to createOptionMenu
    private JMenuBar createMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu Options = new JMenu("Options");
        //creating homepage or first menu option
        JMenuItem home = new JMenuItem("Home");
        home.addActionListener(contents);
        //naming action command Home
        home.setActionCommand("Home");
        Options.add(home);
        //next menu option fairy
        JMenuItem fairy = new JMenuItem("Fairy");
        fairy.addActionListener(contents);
        fairy.setActionCommand("fairy");
        Options.add(fairy);
        //next menu option meadow
        JMenuItem meadow = new JMenuItem("Meadow");
        meadow.addActionListener(contents);
        meadow.setActionCommand("meadow");
        Options.add(meadow);
        //final menu option desert
        JMenuItem desert = new JMenuItem("Desert");
        desert.addActionListener(contents);
        desert.setActionCommand("desert");
        Options.add(desert);
        //adding the options to menu bar
        mb.add(Options);
        return mb;
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
