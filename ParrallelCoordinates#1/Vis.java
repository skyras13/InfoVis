package src.com.company;

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
import java.util.List;

public class Vis extends JPanel implements ActionListener, MouseInputListener {

    private List<Double> xValues, xRatios;
    private List<Double> yValues, yRatios;
    private List<Ellipse2D> points;
    private List<String> tempArray;
    private List<Axis> axes;
    private List<HyrumPolyline> hpList;
    private Rectangle box;
    private Point mouseDown;
    private Point mouseUp;
    private Ellipse2D scat;
    private Double xMax;
    private Double yMax;
    private boolean genderShow;
    private int numAxes;
    private HyrumPolyline hp;

    public Vis() {
        xValues = new ArrayList<>();
        yValues = new ArrayList<>();
        xRatios = new ArrayList<>();
        yRatios = new ArrayList<>();
        tempArray = new ArrayList<>();
        axes = new ArrayList<>();
        hpList = new ArrayList<>();
        addMouseListener(this);
        addMouseMotionListener(this);
//        spencer  = new Ellipse2D.Double(10,10,50,50);
//        scat = new Ellipse2D.Double(0,0,5,5);
//        hp = new HyrumPolyline();
        numAxes = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tempArray.clear();// clears the data in the tempArray used to store coo
        String sql = e.getActionCommand();
        String sqlConnection;
        String sqlQuery;
        //the action commands switch the databases for the parallel coordinates
        if (sql.equals("cis2012")) {
            sqlConnection = "jdbc:derby:pollster_2012";
            sqlQuery = "SELECT * FROM cis2012";
        } else if(sql.equals("cis2019")){
            sqlConnection = "jdbc:derby:pollster";
            sqlQuery = "SELECT * FROM cis2019";
        } else{
            sqlConnection = "jdbc:derby:cs490R";
            sqlQuery = "SELECT * FROM marathon";
        }
        //connecting to the different databases
        try {
            axes.clear();
            numAxes = 0;
            Connection conn = DriverManager.getConnection(sqlConnection);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            ResultSetMetaData meta = rs.getMetaData();
            //getting the number of axes
            int n = meta.getColumnCount();
            numAxes = n;
            //getting the column names and setting the axis types.
            for (int i = 1; i <= n; i++) {
                Axis xis;
                String lab = meta.getColumnLabel(i);
                int skyler = meta.getColumnType(i);
                if (skyler == Types.CHAR || skyler == Types.VARCHAR) {
                    xis = new Axis(lab, Axis.ColumnType.TEXT);
                } else {
                    xis = new Axis(lab, Axis.ColumnType.NUMERIC);
                }
                axes.add(xis);
            }

//            for (Axis x : axes) {
////                    System.out.println(""+x.columnName + " Type: "+x.type);
//            }

            //getting all of the column data for each axis
            while (rs.next()) {
                for (Axis ax : axes) {
                    //storing numeric data
                    if (ax.type == Axis.ColumnType.NUMERIC) {
                        Double info = rs.getDouble(ax.columnName);
                        ax.addNumData(info);
                    }
                    //storing string data
                    if (ax.type == Axis.ColumnType.TEXT) {
                        String info = rs.getString(ax.columnName);
                        ax.addStringData(info);
                    }
                }
            }
            conn.close();
            //TODO have each axis calculate its min/max values

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //getting max and mins of numeric axes
        for (Axis x:axes){
            if (x.type == Axis.ColumnType.NUMERIC){
                Double maxed = 0.0;
                Double mins = 100.0;
                for (Double data: x.numberData){
                    if(maxed<data){
                        maxed = data;
                    }
                    if(mins>data){
                        mins = data;
                    }
                }
                x.setMax(maxed);
                x.setMin(mins);
            }
            //getting labels for String axes
            if (x.type == Axis.ColumnType.TEXT){
                for(String str:x.stringData){
                    if(tempArray.contains(str) == false){
                        tempArray.add(str);
                        x.addLabelStringData(str);
                    }
                }
                x.setStringCount(tempArray.size());
                tempArray.clear();
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g1) {
        //clearing out old hpList
        for(HyrumPolyline hpFirst: hpList){
            hpFirst.reset();
        }
        Graphics2D g = (Graphics2D)g1;
        int x = 50;
        int h = getHeight();
        int w = getWidth();
        g.setColor(Color.white);
        g.fillRect(0,0,w,h);
        g.setColor(Color.BLACK);
        //drawing the bottom line.
        g.drawLine(30,h-25,w-35,h-25);

        //drawing the axes lines
        int xbuffer = 30;
        int[] axisCoordinates = new int[numAxes];
        g.setFont(new Font("TimesRoman", Font.PLAIN, 7));
        for (int i = 0; i < axisCoordinates.length; i++){
            axisCoordinates[i] = (xbuffer + (((w - (xbuffer * 2)) / ((axisCoordinates.length) - 1)) * i));
            g.drawString("" +(axes.get(i).columnName), axisCoordinates[i]-8, h - 10);
            g.drawLine(axisCoordinates[i],25,axisCoordinates[i],h-25);
        }

        //drawing the axes labels
        int[] labelCoordinates;
        int axisCount = 0;
        for(Axis axe: axes) {
//            System.out.println("axisCount: " + axisCount);
            int ybuffer = 25;//used to help correct borders of graph
            if(axe.type == Axis.ColumnType.TEXT){//determines how many labels depending on text vs. numeric
                labelCoordinates = new int[axe.getStringCount()];
            }
            else{
                labelCoordinates = new int[5];
            }
            //setting font and color to fit the screen.
            g.setFont(new Font("TimesRoman", Font.PLAIN, 9));
            g.setColor(Color.RED);
            DecimalFormat df=new DecimalFormat("#.##");

            //printing the numeric labels
            for (int i = 0; i < labelCoordinates.length; i++) {
                if(axe.type == Axis.ColumnType.NUMERIC) {
                    labelCoordinates[i] = (h-(ybuffer + (((h - (ybuffer * 2)) / ((labelCoordinates.length) - 1)) * i)));
                    g.drawString("" + df.format(axe.getMin() + (((axe.getMax() - axe.getMin()) / 4) * i)),
                            axisCoordinates[axisCount]+1, labelCoordinates[i]);
                }else{//printing the string labels.
                    labelCoordinates[i] = (ybuffer + (((h - (ybuffer * 2)) / ((labelCoordinates.length)-1)) * i));
                    g.drawString("" + axe.labelStringData.get(i), axisCoordinates[axisCount] +1, labelCoordinates[i]);
                }
            }

            //setting the parallel coordinate lines
            int rowID = 0;
            if(axe.type == Axis.ColumnType.NUMERIC) {//caluclating points on numeric axes
                for (Double d : axe.numberData) {
                    System.out.println("Max: " + axe.getMax() + " Min: " + axe.getMin());
                    double ratio = (d - axe.getMin()) / (axe.getMax() - axe.getMin());
                    double yPoint = h - (25 + ((h - 50) * ratio));

                    hp = new HyrumPolyline();
                    hp.addPoint(axisCoordinates[axisCount], yPoint);

                    hpList.add(hp);
                    hpList.get(rowID).addPoint(axisCoordinates[axisCount], yPoint);

                    rowID++;
                }
            }else{// calculating points for string axes
                for (String s : axe.stringData) {
                    System.out.println("Max: " + axe.getMax() + " Min: " + axe.getMin());
                    int labelID = 0;
                    double yPoint = 0.0;
                    for(String ls:axe.labelStringData){
                        if(ls.equals(s)){
                            yPoint = labelCoordinates[labelID];
                        }
                        labelID++;
                    }
                    hp = new HyrumPolyline();
                    hp.addPoint(axisCoordinates[axisCount], yPoint);
                    hpList.add(hp);
                    hpList.get(rowID).addPoint(axisCoordinates[axisCount], yPoint);
                    rowID++;
                }
            }
            axisCount++;
        }
        //printing out the different hplines.
        for(HyrumPolyline hpFin:hpList){
            hpFin.draw(g,Color.BLACK);
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
    }

    @Override
    public void mouseReleased(MouseEvent e) {
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
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
