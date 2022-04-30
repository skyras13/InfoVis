package src.com.company;

import java.util.ArrayList;

public class Axis {

    //the two types of axis
    enum ColumnType {
        NUMERIC,
        TEXT
    }

    String columnName;
    ColumnType type;

    //label string data is used to store distinct string axis data
    ArrayList<String> labelStringData;
    //stores all string data
    ArrayList<String> stringData;
    //stores all number data
    ArrayList<Double> numberData;

    //saving the Max, min, and the number of Distinct Strings
    Double max;
    Double min;
    int stringCount;

    public Axis(String name, ColumnType t) {
        columnName = name;
        type = t;
        stringData = new ArrayList<>();
        numberData = new ArrayList<>();
        labelStringData = new ArrayList<>();
    }

    //used to set the Max Variable
    public void setMax(Double m){
        max = m;
    }
    //returns max variable back to the Vis Class
    public Double getMax(){
        return max;
    }
    //used to set the min value
    public void setMin(Double mi){
        min = mi;
    }
    //returns the min value of each axis to vis class.
    public Double getMin(){
        return min;
    }
    //sets the number of distinct strings
    public void setStringCount(int c){
        stringCount = c;
    }
    //returns the string count
    public int getStringCount(){
        return stringCount;
    }
    //adds the double column data for each axis
    public void addNumData(Double d){
        numberData.add(d);
    }
    //adds the string column data for each axis
    public void addStringData(String s){
        stringData.add(s);
    }
    //adds the distinct string data values. e
    public void addLabelStringData(String sl){
        labelStringData.add(sl);
    }



    //TODO add getter methods for type/name
}
