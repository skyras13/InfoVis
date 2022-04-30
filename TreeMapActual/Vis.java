package com.company;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;


public class Vis extends JPanel implements ActionListener, MouseInputListener {

    private Rect rect;
    private Point mouseDown;
    private Node root;
    private String colorType;
    private String filePath;

    public Vis() {
        rect = new Rect();
        addMouseListener(this);
        addMouseMotionListener(this);
        colorType = "fileType";
        filePath = "TreeMaps: ";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();
        System.out.println("Event "+event);
        if(event.equals("selectFolder")){//pulling up the folders and getting the file path.
            rect = new Rect();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int selected = fileChooser.showOpenDialog(null);

            if(selected == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
//                System.out.print(file);
                filePath = file.getAbsolutePath();
                root = new Node(file);
            }
        }else if(event.equals("fileType")){//changeing the color schemes.
            colorType = "fileType";
        }else if(event.equals("fileAge")){
            colorType = "fileAge";
        }else if(event.equals("blackWhite")){
            colorType = "blackWhite";
        }else if(event.equals("random")){
            colorType = "random";
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g1) {
        int h = (getHeight()-20); //leave space for the path to display at bottom of page
        int w = getWidth();


        g1.setColor(Color.WHITE);
        g1.fillRect(0,0,w,h+20);
        g1.setColor(Color.BLACK);
        g1.drawString(filePath,(int)(w*.45),h+12);
        if(root != null) {
            rect.paintDiagram(g1,0,0,w,h,true,root,colorType);
            //calling the draw method to paint all of the rectangles.
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //nevermind
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
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

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Point p = new Point(x,y);//getting the test point

        Node hitNode = rect.rectangleContains(p);//tests the contains in rect class
        if(hitNode!=null){
            String path = hitNode.file.getAbsolutePath();
            long fileSize = hitNode.file.length();//getting the size of each of the files.
            setToolTipText(path+", File Size: "+fileSize+" Bytes");
        }else{
            setToolTipText(null);//clearing the tool tip.
        }
        //clear out the arraylists so they dont waist data.
        rect.file.clear();
        rect.rectangles.clear();

        repaint();
    }
}