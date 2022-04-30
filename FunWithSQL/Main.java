package com.company;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends JFrame {

    private Vis contents;

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,400);
        //setting tile of window
        setTitle("cis2019 Database Query Machine");
        //getting the jpanel class
        contents = new Vis();
        setContentPane(contents);
        var myMenuBar = createMenu();
        setJMenuBar(myMenuBar);
        setVisible(true);
    }

    //creating the Menu Bar and options
    private JMenuBar createMenu() {
        //make the menu bar
        var mb = new JMenuBar();
        //make the menu on the menu bar
        var file = new JMenu("Query");
        //first menu option and query
        var option1 = new JMenuItem("Number of Students from Hawaii");
        option1.addActionListener(contents);
        option1.setActionCommand("SELECT COUNT(*) FROM cis2019 WHERE Home = 'Hawaii'");//use action command to send query to vis.
        file.add(option1);
        //second menu option and query
        JMenuItem option2 = new JMenuItem("Number of students who are not CS Majors");
        option2.addActionListener(contents);
        option2.setActionCommand("SELECT COUNT(*) FROM cis2019 WHERE NOT Major = 'Computer Science'");
        file.add(option2);
        //third menu option and query
        JMenuItem option3 = new JMenuItem("Number of students graduating in 2009 or 2012");
        option3.addActionListener(contents);
        option3.setActionCommand("SELECT COUNT(*) FROM cis2019 WHERE GradYear = 2009 OR GradYear = 2012");
        file.add(option3);
        //4th menu option and query
        var option4 = new JMenuItem("Number of Male students Majoring in Information Systems");
        option4.addActionListener(contents);
        option4.setActionCommand("SELECT COUNT(*) FROM cis2019 WHERE Major = 'Information Systems' and GENDER = 'M'");
        file.add(option4);
        //5th menu option and query
        var option5 = new JMenuItem("Number of students from Asia and with GPA > 3.0");
        option5.addActionListener(contents);
        option5.setActionCommand("SELECT COUNT(*) FROM cis2019 WHERE Home = 'Asia' AND gpa > 3.0");
        file.add(option5);
        //sixth menu option and query
        var option6 = new JMenuItem("Number of students from Pacific and in the age group 22-24");
        option6.addActionListener(contents);//contents are the jpanel from the vis class
        option6.setActionCommand("SELECT COUNT(*) FROM cis2019 WHERE Home = 'Pacific' AND AgeGroup = '22-24'");
        file.add(option6);
        mb.add(file);
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
