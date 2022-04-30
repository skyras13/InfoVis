package src.com.company;
import javax.swing.*;

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
        var file = new JMenu("Data Set");

        //first menu option and query
        var option1 = new JMenuItem("cis2012");
        option1.addActionListener(contents);
        option1.setActionCommand("cis2012");//use action command to send query to vis.
        file.add(option1);

        //second menu option and query
        JMenuItem option2 = new JMenuItem("cis2019");
        option2.addActionListener(contents);
        option2.setActionCommand("cis2019");
        file.add(option2);

        //third menu option and query
        JMenuItem option3 = new JMenuItem("marathon");
        option3.addActionListener(contents);
        option3.setActionCommand("marathon");
        file.add(option3);
//
        //adding everything to the menu bar
        mb.add(file);
//        mb.add(chart);
        return mb;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
