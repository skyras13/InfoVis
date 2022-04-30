package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Vis extends JPanel implements ActionListener {
    //string for all action commands
    private String queryInt;
    private String queryString;
    private String chartString = "bar"; //String used to switch bar and line charts
    private Map<String, Double> ratios;//hashmap to store the ratios
    private double gMax;//value of the max count

    //initial viz class
    public Vis() {
        queryInt = "Check out our cis2019 Database!";
        queryString = "this is the category";
        ratios = new HashMap<>();
        gMax = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //getting the queries from the setActionCommand() methods in the main class
        String sql = e.getActionCommand();
        System.out.println("SQL = "+sql);
        //if statement to help handle the switching of graphs later in paintcomponent
        if(sql.equals("bar") || sql.equals("line")){
            chartString = sql;
        }else { //else statement to run the queries
            ratios.clear();//clears the last stored query data.
            try {
                //getting the driver to connect to the database
                Connection conn = DriverManager.getConnection("jdbc:derby:pollster");
                Statement stmt = conn.createStatement();
                //running the SQL Query and setting it to a result set
                ResultSet rs = stmt.executeQuery(sql);
                //adding a new hashmap to store the returned data
                Map<String, Double> map = new HashMap<>();
                while (rs.next()) {//looping through left and right columns of returned data
                    Double count = rs.getDouble(1);
                    String section = rs.getString(2);
                    //                System.out.println(section + " Year Value");
                    map.put(section, count);
                }
                //finding the max values for determining height ratios later.
                double max = Double.MIN_VALUE;
                for (var i : map.values()) {
                    //                System.out.println("i Val - " + i);
                    if (i > max) {
                        max = i;
                    }
                }
                gMax = max;//global variable used for printing y-axis labels in paint component
                for (var label : map.keySet()) {
                    double i = map.get(label);
                    //                System.out.println("label "+label);
                    double ratio = i / max; //determining bar height of each label
                    ratios.put(label, ratio);
                }
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        //repaint with now string variable for message
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        //drawing the string to the contents of the window
//        g.drawString(queryInt, 10, 60);
//        g.drawString(queryString,10,90);

        if(this.ratios != null) {//avoiding null pointer exception
            int x = 50;
            int barX = 0;
            int h = getHeight();
            int w = getWidth();
            //storing the strings and x-axis to their own strings to sort and print later
            ArrayList labels = new ArrayList<String>();
            ArrayList xLen = new ArrayList<Integer>();
            //setting to max 2 decimal places on doubles.
            DecimalFormat df = new DecimalFormat("#.##");
            df.format(gMax);
            System.out.println("gMax " + df.format(gMax));

            //setting the distance between each bar.
            if(ratios.size() != 0){
                barX = (getWidth()/ratios.size())/2;
            }

            for (var bars : ratios.keySet()) {//keyset puts large hashmaps out of order. needed to resort.
                labels.add(bars);
                Collections.sort(labels);

                //Left Values starting with y max.
                g.setColor(Color.BLACK);
                g.drawString(""+df.format(gMax),2,24);
                g.drawString(""+df.format(gMax*.75),2,(int) (20+(h*.22)));
                g.drawString(""+df.format(gMax*.50),2,(int) (20+(h*.45)));
                g.drawString(""+df.format(gMax*.25),2,(int) (20+(h*.70)));
                g.drawString(""+0,2,h - 20);

                //drawing axis lines
                g.fillRect(30,h - 20,getWidth()-20,5);
                g.fillRect(30,20,5,h - 40);

                //adding onto the x coordinates
                xLen.add(x);

                //incrementing the x- axis
                x += (barX)+(barX/2);
            }

            if (chartString.equals("line")) {//drawing the line chart
                g.setColor(Color.BLUE);
                for (int i = 1; i < labels.size(); i++) {
                    //printing sorted arrayslist with x values from x coord arraylist
                    g.setColor(Color.BLACK);
                    g.drawString((String) labels.get(i-1), (Integer) xLen.get(i-1), h - 5);
                    g.drawString((String) labels.get(i), (Integer) xLen.get(i), h - 5);

                    //getting height ratios.
                    double ratio = ratios.get(labels.get(i-1));
                    double ratioNext = ratios.get(labels.get(i));
                    double barHeight = h * ratio;
                    double nextBarHeight = h * ratioNext;

                    //getting x and y coordinates for each
                    g.setColor(Color.BLUE);
                    int x1 = ((Integer) xLen.get(i-1)) + 10;
                    int y1 = 20 + (h - (int) barHeight);
                    if(barHeight < (h*0.03)){
                        y1 = h - 30;
                    }
                    int x2 = ((Integer) xLen.get(i)) + 10;
                    int y2 = 20 + (h - (int) nextBarHeight);

                    //drawing line graph points and lines
                    g.fillOval(x1, y1, (int) (h * .05), (int) (h * 0.05));
                    g.fillOval(x2, y2, (int) (h * .05), (int) (h * 0.05));
                    g.drawLine(x1+ (int)(w*.02), y1+ (int)(h*.02), x2+ (int)(w*.02), y2+ (int)(h*.02));
                }
            }
            else{//printing the bar graph
                for (int i = 0; i < labels.size(); i++) {
                    g.setColor(Color.BLACK);

                    //printing sorted arraylist of labels
                    g.drawString((String) labels.get(i), (Integer) xLen.get(i), h - 5);

                    //get the height for each bar
                    double ratio = ratios.get(labels.get(i));
                    double barHeight = h * ratio;

                    //printing the bars.
                    g.setColor(Color.BLUE);
                    g.fillRect((Integer) xLen.get(i), 20 + (h - (int) barHeight), barX, (int) barHeight - 40);
                }
            }
        }
    }
}
