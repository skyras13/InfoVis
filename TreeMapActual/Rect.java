package com.company;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Rect {
    ArrayList<Node> file;
    ArrayList<Rectangle> rectangles;
    private Rectangle rect;


    public Rect() {
        rectangles = new ArrayList<Rectangle>();
        file = new ArrayList<Node>();
    }


    public void paintDiagram(Graphics g1,float left, float top, float w, float h, boolean horizontal,Node node,String colorType){
//        System.out.println("Left: "+left+" top: "+top+" height: "+h+" width: "+w);
        if(node.children.isEmpty()){//if final box then this if statement will paint it in.
            rect = new Rectangle((int)left,(int)top,(int)w,(int)h);//get rect area for the tool tip
            rectangles.add(rect);
            file.add(node);
//            System.out.println("Color "+colorType);
            //implement the color selection.
            if(colorType.equals("fileAge")){
                oldestFileColor(node.file,g1);
            }else if(colorType.equals("fileType")){
                fileTypeColor(node.file,g1);
            }else if(colorType.equals("blackWhite")){
                g1.setColor(Color.WHITE);
            }else{
                Random rand = new Random();
                float r = rand.nextFloat();
                float g = rand.nextFloat();
                float b = rand.nextFloat();
                Color randomColor = new Color(r, g, b);
                g1.setColor(randomColor);
            }
            //drawing the rectangles and borders
            g1.fillRect((int)left,(int)top,(int)w,(int)h);
            g1.setColor(Color.BLACK);
            g1.drawRect((int)left,(int)top,(int)w,(int)h);
        }else{
            if(horizontal == true){//drawing the horizantal trees
//                System.out.println("Node Sum: "+node.sum);
//                System.out.println("weight"+w);
                float pixelsPerByte = w/node.sum;//get the number of bytes to display.
                float nextLeft = left;
                for(Node child:node.children){
                    float childWidth = (child.sum*pixelsPerByte);
                    float childLeft = nextLeft;
                    float childTop = top;
                    float childHeight = h;
                    paintDiagram(g1,childLeft,childTop,childWidth,childHeight,false,child,colorType);
                    nextLeft += childWidth;//increment to next area.
                }
            }else{//vetical
                float pixelsPerByte = h/node.sum;
                float nextTop = top;
                for(Node child:node.children) {
                    float childWidth = w;
                    float childLeft = left;
                    float childTop = nextTop;
                    float childHeight = (child.sum*pixelsPerByte);
                    paintDiagram(g1, childLeft,childTop,childWidth,childHeight,true,child,colorType);
                    nextTop += childHeight;
                }
            }
        }
    }


    public Node rectangleContains(Point p){
        boolean result = false;
        int counter = 0;
        for(Rectangle rect:rectangles){
            if(rect.contains(p)){//checks the rectangels on the TreeMap
                break;
            }
            if(counter<(rectangles.size()-1)){//moving to next file.
                counter++;
            }
        }
//        System.out.println("Counter: "+counter+" file: "+file.size());
        if(!file.isEmpty()){//return the file found
            return file.get(counter);
        }else{
            return null;
        }
    }

    public Graphics oldestFileColor(File file,Graphics g){
        Date currentDate = new Date();
        Date testDate = new Date(file.lastModified());
        long difference = currentDate.getTime() - testDate.getTime();
        long differenceOfDays = ((difference / (1000 * 60 * 60 * 24)) % 365);
        System.out.println(""+differenceOfDays);
        if(differenceOfDays>=548){//more than a year and a half
            g.setColor(Color.RED);
        }else if(365<=differenceOfDays && differenceOfDays<548){//more than a year
            g.setColor(Color.magenta);
        }else if(182<=differenceOfDays && differenceOfDays<365){//6 months to a year
            g.setColor(Color.BLUE);
        }else if(91<=differenceOfDays && differenceOfDays<182){//3 months to 6 moths
            g.setColor(Color.GREEN);
        }else if(60<=differenceOfDays && differenceOfDays<91){//2 months to 3 months
            g.setColor(Color.yellow);
        }else if(30<=differenceOfDays && differenceOfDays<60){//1 month to 2 months
            g.setColor(Color.ORANGE);
        }else if(15<=differenceOfDays && differenceOfDays<30){//15 days to 1 month
            g.setColor(Color.CYAN);
        }else{//less than 15 days
            g.setColor(Color.WHITE);
        }
        return g;
    }

    public Graphics fileTypeColor(File file, Graphics g){
        String fileName = file.toString();
        String extension;
        int index = fileName.lastIndexOf('.');
        if(index > 0) {
            extension = fileName.substring(index + 1);
        }else{
            extension = "extra";
        }
//        System.out.println("File extension is " + extension);
//        System.out.println(fileName);
        if(extension.equals("doc")||
            extension.equals("docx")) {//document
//            System.out.println("hit");
            g.setColor(Color.BLUE);
            return g;
        }else if(extension.equals("txt")){//raw text
            g.setColor(Color.GREEN);
            return g;
        }else if(extension.equals("xlsx")||
                extension.equals("xls")||
                extension.equals("xlsm")) {//spreadsheets
            g.setColor(Color.RED);
//            System.out.println("hit 2");
            return g;
        }else if(extension.equals("pdf")||
                extension.equals(("pptx"))||
                extension.equals("pptm")||
                extension.equals("ppt")){//slideshow
            g.setColor(Color.YELLOW);
            return g;
        }else if(extension.equals("jpeg")||
                extension.equals(("jpg"))||
                extension.equals("png")||
                extension.equals("gif")||
                extension.equals("raw")||
                extension.equals("")){//images
            g.setColor(Color.ORANGE);
            return g;
        }else if(extension.equals("m4a")||
                extension.equals("flac")||
                extension.equals("mp4")||
                extension.equals("mp3")){//Audio
            g.setColor(Color.MAGENTA);
            return g;
        }else if(extension.equals("exe")||
                extension.equals("bin")||
                extension.equals("py")||
                extension.equals("apk")||
                extension.equals("zip")||
                extension.equals("url")||
                extension.equals("jar")){//executable
            g.setColor(Color.CYAN);
            return g;
        }if(extension.equals("java")||
                extension.equals("c")||
                extension.equals("h")||
                extension.equals("html")||
                extension.equals("css")||
                extension.equals("js")||
                extension.equals("cpp")){//source code
            g.setColor(Color.LIGHT_GRAY);
            return g;
        }else if(extension.equals("class")||
                extension.equals("cpp")||
                extension.equals("csv")||
                extension.equals("o")){//object code
            g.setColor(Color.darkGray);
            return g;
        }else{
            g.setColor(Color.white);
            return g;
        }
    }

}
