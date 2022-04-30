package com.company;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vis extends JPanel implements ActionListener {

    private String theme;
    //instantiate the cloud class
    private Cloud cloud1 = new Cloud();
    private Cloud cloud2 = new Cloud();

    public Vis() {
        theme = "Home";
        //setting initial co-ordinates for the clouds
        cloud1.setX(50);
        cloud2.setX(300);
        new Timer(100, paintTimer).start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        theme = e.getActionCommand();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        Font font = new Font("Verdana", Font.BOLD, 25);
        g.setFont(font);
        if(theme.equals("meadow")){
            //draw blank background
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, getWidth(), getHeight());
            //draw field
            g.setColor(Color.GREEN);
            g.fillRect(0,(int)(getHeight()*.6),getWidth(),getHeight());
            g.setColor(Color.BLACK);
            //house
            g.fillRect((int)(getWidth()*.25),(int)(getHeight()*.35),(int)(getWidth()*.50),(int)(getHeight()*.50));
            g.fillRect((int)(getWidth()*.20),(int)(getHeight()*.30),(int)(getWidth()*.60),(int)(getHeight()*.10));
            g.setColor(Color.RED);
            //door
            g.fillRect((int)(getWidth()*.50),(int)(getHeight()*.65),(int)(getWidth()*.10),(int)(getHeight()*.20));
            g.setColor(Color.white);
            //window
            g.fillRect((int)(getWidth()*.30),(int)(getHeight()*.45),(int)(getWidth()*.175),(int)(getHeight()*.175));
            //cloud1
            g.fillOval(cloud1.getX(),(int)(getHeight()*.10),(int)(getWidth()*.175),(int)(getHeight()*.175));
            g.fillOval(cloud1.getX()+20,(int)(getHeight()*.075),(int)(getWidth()*.175),(int)(getHeight()*.2));
            g.fillOval(cloud1.getX()+40,(int)(getHeight()*.10),(int)(getWidth()*.175),(int)(getHeight()*.175));
            //cloud2
            g.fillOval(cloud2.getX(),(int)(getHeight()*.10),(int)(getWidth()*.175),(int)(getHeight()*.175));
            g.fillOval(cloud2.getX()+20,(int)(getHeight()*.075),(int)(getWidth()*.175),(int)(getHeight()*.2));
            g.fillOval(cloud2.getX()+40,(int)(getHeight()*.10),(int)(getWidth()*.175),(int)(getHeight()*.175));
        }else if(theme.equals("desert")){
            //draw blank background
            g.setColor(Color.CYAN);
            g.fillRect(0, 0, getWidth(), getHeight());
            //draw field
            g.setColor(Color.ORANGE);
            g.fillRect(0,(int)(getHeight()*.6),getWidth(),getHeight());
            g.setColor(Color.RED);
            //house
            g.fillRect((int)(getWidth()*.25),(int)(getHeight()*.35),(int)(getWidth()*.50),(int)(getHeight()*.50));
            g.fillRect((int)(getWidth()*.20),(int)(getHeight()*.30),(int)(getWidth()*.60),(int)(getHeight()*.10));
            g.setColor(Color.BLACK);
            //door
            g.fillRect((int)(getWidth()*.50),(int)(getHeight()*.65),(int)(getWidth()*.10),(int)(getHeight()*.20));
            g.setColor(Color.WHITE);
            //window
            g.fillRect((int)(getWidth()*.30),(int)(getHeight()*.45),(int)(getWidth()*.175),(int)(getHeight()*.175));
            //cloud1
            g.fillOval(cloud1.getX(),(int)(getHeight()*.10),(int)(getWidth()*.175),(int)(getHeight()*.175));
            g.fillOval(cloud1.getX()+20,(int)(getHeight()*.075),(int)(getWidth()*.175),(int)(getHeight()*.2));
            g.fillOval(cloud1.getX()+40,(int)(getHeight()*.10),(int)(getWidth()*.175),(int)(getHeight()*.175));
            //cloud2
            g.fillOval(cloud2.getX(),(int)(getHeight()*.10),(int)(getWidth()*.175),(int)(getHeight()*.175));
            g.fillOval(cloud2.getX()+20,(int)(getHeight()*.075),(int)(getWidth()*.175),(int)(getHeight()*.2));
            g.fillOval(cloud2.getX()+40,(int)(getHeight()*.10),(int)(getWidth()*.175),(int)(getHeight()*.175));
        }else if(theme.equals("fairy")){
            //draw blank background
            g.setColor(Color.YELLOW);
            g.fillRect(0, 0, getWidth(), getHeight());
            //draw field
            g.setColor(Color.MAGENTA);
            g.fillRect(0,(int)(getHeight()*.6),getWidth(),getHeight());
            g.setColor(Color.PINK);
            //house
            g.fillRect((int)(getWidth()*.25),(int)(getHeight()*.35),(int)(getWidth()*.50),(int)(getHeight()*.50));
            g.fillRect((int)(getWidth()*.20),(int)(getHeight()*.30),(int)(getWidth()*.60),(int)(getHeight()*.10));
            g.setColor(Color.green);
            //door
            g.fillRect((int)(getWidth()*.50),(int)(getHeight()*.65),(int)(getWidth()*.10),(int)(getHeight()*.20));
            g.setColor(Color.white);
            //window
            g.fillRect((int)(getWidth()*.30),(int)(getHeight()*.45),(int)(getWidth()*.175),(int)(getHeight()*.175));
            //cloud1
            g.fillOval(cloud1.getX(),(int)(getHeight()*.10),(int)(getWidth()*.175),(int)(getHeight()*.175));
            g.fillOval(cloud1.getX()+20,(int)(getHeight()*.075),(int)(getWidth()*.175),(int)(getHeight()*.2));
            g.fillOval(cloud1.getX()+40,(int)(getHeight()*.10),(int)(getWidth()*.175),(int)(getHeight()*.175));
            //cloud2
            g.fillOval(cloud2.getX(),(int)(getHeight()*.10),(int)(getWidth()*.175),(int)(getHeight()*.175));
            g.fillOval(cloud2.getX()+20,(int)(getHeight()*.075),(int)(getWidth()*.175),(int)(getHeight()*.2));
            g.fillOval(cloud2.getX()+40,(int)(getHeight()*.10),(int)(getWidth()*.175),(int)(getHeight()*.175));
        }else{
            //draw blank background
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            //draw white box
            g.setColor(Color.white);
            g.fillRect((int)(getWidth()*.20),(int)(getHeight()*.20),(int)(getWidth()*.60),(int)(getHeight()*.60));
            g.setColor(Color.red);
            g.drawString("Check out our",(int)(getWidth()*.25),(int)(getHeight()*.40));
            g.drawString("Cartoon Houses!",(int)(getWidth()*.20),(int)(getHeight()*.65));
        }
    }
    // functionality of the timer:
    Action paintTimer = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            //moving cloud1
            if(cloud1.getX() < getWidth()) {
                cloud1.setX(cloud1.getX() + 5);
            }else{
                cloud1.setX(cloud1.getX()-getWidth()-40);
            }
            //moving cloud2
            if(cloud2.getX() < getWidth()) {
                cloud2.setX(cloud2.getX() + 5);
            }else{
                cloud2.setX(cloud2.getX()-getWidth()-40);
            }
            repaint();
        }
    };


}
