package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main extends JFrame{

    private Vis contents;
    private Node root;

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);

        //getting the jpanel class
        contents = new Vis();
        setContentPane(contents);
        //setting the menubar
        //setting tile of window
        setTitle("Tree Map");
        var myMenuBar = createMenu();
        setJMenuBar(myMenuBar);
        setVisible(true);
    }

    //creating the Menu Bar and options
    private JMenuBar createMenu() {
        //make the menu bar
        var mb = new JMenuBar();
        //make the menu on the menu bar
        var folder = new JMenu("Folder");
        mb.add(folder);

        //first menu option and query
        var folderSelect = new JMenuItem("Select Folder");
        folderSelect.addActionListener(contents);
        folderSelect.setActionCommand("selectFolder");//use action command to send query to vis.
        folder.add(folderSelect);

        //second menu option and query
        JMenu setColor = new JMenu("Color By");
        JMenuItem blackWhite = new JMenuItem("No Color");
        blackWhite.addActionListener(contents);
        blackWhite.setActionCommand("blackWhite");
        setColor.add(blackWhite);

        JMenuItem fileType = new JMenuItem("File Type");
        fileType.addActionListener(contents);
        fileType.setActionCommand("fileType");
        setColor.add(fileType);

        JMenuItem fileAge = new JMenuItem("File Age");
        fileAge.addActionListener(contents);
        fileAge.setActionCommand("fileAge");
        setColor.add(fileAge);

        JMenuItem random = new JMenuItem("Random");
        random.addActionListener(contents);
        random.setActionCommand("random");
        setColor.add(random);

        folder.add(setColor);

        return mb;
    }



    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
