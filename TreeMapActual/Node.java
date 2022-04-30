package com.company;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Node {
    File file;
    Rectangle rect;
    long sum;
    List<Node> children;
//    private ArrayList<Rectangle> hits;


    public Node(File f){
        file = f;
        rect = new Rectangle();
        children = new ArrayList<Node>();
//        hits = new ArrayList<Rectangle>();
        if(f.isFile()){//building the next part of the from a parent node.
            sum = f.length();
            System.out.println("File Name :"+f.getAbsolutePath());
        }else{
            for (File file:f.listFiles()){//creating children nodes for each subfolder and file.
                Node fileNode = new Node(file);
                sum += fileNode.sum;
//                System.out.println("Total Sum: "+sum);
                children.add(fileNode);
            }
        }
    }
}

