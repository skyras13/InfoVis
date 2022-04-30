package com.company;
import javax.swing.*;

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
        setSize(800,400);
        //setting tile of window
        setTitle("cis2019 Database Query Machine");
        //getting the jpanel class
        contents = new Vis();
        setContentPane(contents);
        //setting the menubar
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
        var option1 = new JMenuItem("Age vs GPA");
        option1.addActionListener(contents);
        option1.setActionCommand("SELECT age, gpa, gender FROM cis2012");//use action command to send query to vis.
        file.add(option1);

        //second menu option and query
        JMenuItem option2 = new JMenuItem("Credits Attempted Vs. Credits Passed");
        option2.addActionListener(contents);
        option2.setActionCommand("SELECT credits_attempted, credits_passed, gender FROM cis2012");
        file.add(option2);

        //third menu option and query
        JMenuItem option3 = new JMenuItem("Credits Attempted Vs. GPA");
        option3.addActionListener(contents);
        option3.setActionCommand("SELECT credits_attempted, gpa, gender FROM cis2012");
        file.add(option3);

        //4th menu option and query
        var option4 = new JMenuItem("Credits Passed Vs. GPA");
        option4.addActionListener(contents);
        option4.setActionCommand("SELECT credits_passed, GPA, gender FROM cis2012");
        file.add(option4);

        //5th menu option and query
        var option5 = new JMenuItem("Age Vs. Credits Attempted");
        option5.addActionListener(contents);
        option5.setActionCommand("SELECT age, credits_passed, gender FROM cis2012");
        file.add(option5);

        //sixth menu option and query
//        var option6 = new JMenuItem("Number of students per GPA");
//        option6.addActionListener(contents);//contents are the jpanel from the vis class
//        option6.setActionCommand("SELECT COUNT(*), gparange FROM cis2019 GROUP BY gparange");
//        file.add(option6);

        //setting a new menu for chart type
        var chart = new JMenu("Show Gender");

        //chart type option for bar char
        var bar = new JMenuItem("Gender On");
        bar.addActionListener(contents);
        bar.setActionCommand("On");
        chart.add(bar);
//
        //chart type option for line chart
        var line = new JMenuItem("Gender Off");
        line.addActionListener(contents);
        line.setActionCommand("Off");
        chart.add(line);

        //adding everything to the menu bar
        mb.add(file);
        mb.add(chart);
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
