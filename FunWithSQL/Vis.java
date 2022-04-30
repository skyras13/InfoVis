package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Vis extends JPanel implements ActionListener {
    //string for all action commands
    private String query;
    //initial message on window
    public Vis() {
        query = "Check out our cis2019 Database!";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //getting the queries from the setActionCommand() methods in the main class
        String sql = e.getActionCommand();
        System.out.println("Result of query");
        try {
            //getting the driver to connect to the database
            Connection conn = DriverManager.getConnection("jdbc:derby:pollster");
            Statement stmt = conn.createStatement();
            //running the SQL Query and setting it to a result set
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int students = rs.getInt(1);
            //printing results to console
            System.out.println("There are " + students + " students in the table.");
            //adding results to global variable
            query = "The number of students is " + students;
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //repaint with now string variable for message
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        //drawing the string to the contents of the window
        g.drawString(query, 10, 60);
    }
}
