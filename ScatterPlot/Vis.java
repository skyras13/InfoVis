package com.company;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Vis extends JPanel implements ActionListener, MouseInputListener {

    private List<Double> xValues, xRatios;
    private List<Double> yValues, yRatios;
    private List<Ellipse2D> points;
    private List<String> gender;
    private Rectangle box;
    private Point mouseDown;
    private Point mouseUp;
    private Ellipse2D scat;
    private Double xMax;
    private Double xMin;
    private Double yMax;
    private Double yMin;
    private int xRange;
    private int yRange;
    private Double xZoomMin;
    private Double yZoomMin;
    private Double xZoomMax;
    private Double yZoomMax;
    private boolean genderShow;
    private boolean zoom;

    public Vis() {
        xValues = new ArrayList<>();
        yValues = new ArrayList<>();
        xRatios = new ArrayList<>();
        yRatios = new ArrayList<>();
        gender = new ArrayList<>();
        points = new ArrayList<>();
        addMouseListener(this);
        addMouseMotionListener(this);
//        spencer  = new Ellipse2D.Double(10,10,50,50);
//        scat = new Ellipse2D.Double(0,0,5,5);

        genderShow = false;
        zoom = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!zoom) {
            yMax = 0.0;
            xMax = 0.0;
            yMin = 0.0;
            xMin = 0.0;
        }
        zoom = false;
        String sql = e.getActionCommand();
        if (sql.equals("On")) {//managing the showing of gender
            genderShow = true;
        } else if (sql.equals("Off")) {
            genderShow = false;
        } else {//if command is for a query
            //clearing all previous query values.
            xValues.clear();
            yValues.clear();
            xRatios.clear();
            yRatios.clear();
            points.clear();
            zoom = false;
            try {//getting results from database.
                Connection conn = DriverManager.getConnection("jdbc:derby:pollster_2012");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    //getting x and y axises and the gender values.
                    xValues.add(rs.getDouble(1));
                    yValues.add(rs.getDouble(2));
                    gender.add(rs.getString(3));
                }
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        //finding min and max of original queries.
        if (zoom == false) {
            if (xMax != null && yMax != null) {
                for (Double x : xValues) {
                    if (x > xMax) {
                        xMax = x;
                    }
                }
                //finding max y
                for (Double y : yValues) {
                    if (y > yMax) {
                        yMax = y;
                    }
                }
                //setting ratio for x values
                for (double x : xValues) {
                    double ratio = x / xMax;
                    xRatios.add(ratio);
                }
                //setting ratio for y values.
                for (double y : yValues) {
                    double ratio = y / yMax;
                    yRatios.add(ratio);
                }
            }
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g1) {
        points.clear();//clear the points arraylist.
        Graphics2D g = (Graphics2D)g1;
        DecimalFormat df=new DecimalFormat("#.##");
        int x = 50;
        int h = getHeight();
        int w = getWidth();
//        System.out.println("widthFirst-"+w);
        g.setColor(Color.white);
        g.fillRect(0,0,w,h);
        g.setColor(Color.BLACK);
        //drawing the axis lines.
        g.drawLine(25,h-25,w-25,h-25);
        g.drawLine(25,25,25,h-25);
        g.setColor(Color.BLUE);

        //setting and printing the dots.
        for(int i = 0; i <xValues.size(); i++) {
            double xPoint = 25 + ((w - 50) * xRatios.get(i));
            double yPoint = 30 + ((h - 50) * yRatios.get(i));
            Ellipse2D scat = new Ellipse2D.Double((xPoint), (h - yPoint), 5, 5);
            //changer dot color based on gender.
            if(genderShow)
            if (gender.get(i).equals("M")) {
                g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.PINK);
            }
            g.fill(scat);
            points.add(scat);
        }

        double[] xLabels;
        double[] yLabels;
        System.out.println("Zoom = "+zoom);
        //setting the spacing for the xaxis
        if(xMax != null && yMax != null) {
            if (zoom == false) {//original print
                xLabels = new double[]{0, (xMax * .25), (xMax * .50), (xMax * .75), xMax};
                yLabels = new double[]{yMax, (yMax * .75), (yMax * .50), (yMax * .25), 0};
            } else {//print after zoom.
                double newXRatio = (xMax-xMin);
                double newYRatio = (yMax-yMin);
                xLabels = new double[]{xMin, (xMax-(newXRatio*.75)), (xMax-(newXRatio*.50)), (xMax-(newXRatio*.25)), xMax};
                yLabels = new double[]{yMin, (yMax-(newYRatio*.75)), (yMax-(newYRatio*.50)), (yMax-(newYRatio*.25)), yMax};
            }
            //writing the xaxis labels
            int xbuffer = 25;
            int[] xCoordinates = new int[xLabels.length];
            for (int i = 0; i < xCoordinates.length; i++) {
                xCoordinates[i] = (xbuffer + (((w - (xbuffer * 2)) / ((xCoordinates.length) - 1)) * i));
                g.drawString("" + df.format(xLabels[i]), xCoordinates[i], h - 10);
            }

            //writing the yaxis labels
            int ybuffer = 25;
            int[] yCoordinates = new int[yLabels.length];
            for (int i = 0; i < yCoordinates.length; i++) {
                yCoordinates[i] = (ybuffer + (((h - (ybuffer * 2)) / ((yCoordinates.length) - 1)) * i));
                g.drawString("" + df.format(yLabels[i]), 0, yCoordinates[i]);
            }
        }

        //drawing the box.
        if (box != null) {
            int alpha = 50; // 50% transparent
            //setting the color to transparent
            Color myColour = new Color(0, 0, 255, alpha);
            g.setColor(myColour);
            g.drawRect(box.x, box.y, box.width, box.height);
            g.fill(box);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //nevermind
    }

    @Override
    public void mousePressed(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        mouseDown = new Point((int) x, (int) y);
        box = new Rectangle();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        double x2 = e.getX();
        double y2 = e.getY();
        mouseUp = new Point((int) x2, (int) y2);


        //coverting mouse events to doubles
        Double mDX = Double.valueOf(mouseDown.x);
        Double mDY = Double.valueOf(mouseDown.y);
        Double mUX = Double.valueOf(mouseUp.x);
        Double mUY = Double.valueOf(mouseUp.y);



        xZoomMin = (((mDX/getWidth())*(xMax-xMin))+xMin);
        yZoomMin = (((getHeight()-mDY)/getHeight())*(yMax-yMin)+yMin);

        xZoomMax = ((mUX/getWidth())*(xMax-xMin)+xMin);
        yZoomMax = (((getHeight()-mUY)/getHeight())*(yMax-yMin)+yMin);


        xMin = xZoomMin;
        xMax = xZoomMax;
        yMin = yZoomMin;
        yMax = yZoomMax;

        System.out.println("xMin-"+xMin);
        System.out.println("xMax-"+xMax);
        System.out.println("yMin-"+yMin);
        System.out.println("yMax-"+yMax);

        zoom = true;
        box = null;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //nevermind
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //nevermind
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        box.setFrameFromDiagonal(mouseDown.x, mouseDown.y, e.getX(), e.getY());
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
//        System.out.println("Point "+points.size());
//        System.out.println("x "+xValues.size());
//        System.out.println("y "+yValues.size());
        for(int i=0;i<points.size();i++){
            if (points.get(i).contains(x, y)) {
              setToolTipText("X: "+xValues.get(i)+",Y: "+yValues.get(i));
                break;
            } else {
                setToolTipText(null);
            }
        }
    }
}
