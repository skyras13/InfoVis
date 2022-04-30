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
        var option1 = new JMenuItem("Number of Students in each Major");
        option1.addActionListener(contents);
        option1.setActionCommand("SELECT COUNT(*), Major FROM cis2019 GROUP BY Major");//use action command to send query to vis.
        file.add(option1);

        //second menu option and query
        JMenuItem option2 = new JMenuItem("Number of students from Different Home areas");
        option2.addActionListener(contents);
        option2.setActionCommand("SELECT COUNT(*), Home FROM cis2019 GROUP BY Home");
        file.add(option2);

        //third menu option and query
        JMenuItem option3 = new JMenuItem("Avg GPA of Students From Each Major");
        option3.addActionListener(contents);
        option3.setActionCommand("SELECT AVG(GPA), Major FROM cis2019 GROUP BY Major");
        file.add(option3);

        //4th menu option and query
        var option4 = new JMenuItem("Avg Number of Credits Attempted per Year");
        option4.addActionListener(contents);
        option4.setActionCommand("SELECT AVG(Credits_Attempted), GradYear FROM cis2019 GROUP BY GradYear ORDER BY GradYear");
        file.add(option4);

        //5th menu option and query
        var option5 = new JMenuItem("Number of students with GPA > 3.0 FROM each Country");
        option5.addActionListener(contents);
        option5.setActionCommand("SELECT COUNT(*), Home FROM cis2019 WHERE gpa > 3.0 GROUP BY Home");
        file.add(option5);

        //sixth menu option and query
        var option6 = new JMenuItem("Number of students per GPA");
        option6.addActionListener(contents);//contents are the jpanel from the vis class
        option6.setActionCommand("SELECT COUNT(*), gparange FROM cis2019 GROUP BY gparange");
        file.add(option6);

        //setting a new menu for chart type
        var chart = new JMenu("Chart Type");

        //chart type option for bar char
        var bar = new JMenuItem("Bar Chart");
        bar.addActionListener(contents);
        bar.setActionCommand("bar");
        chart.add(bar);

        //chart type option for line chart
        var line = new JMenuItem("Line Chart");
        line.addActionListener(contents);
        line.setActionCommand("line");
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
