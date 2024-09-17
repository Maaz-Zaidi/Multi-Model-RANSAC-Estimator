//Maaz Zaidi
//Date: December 5, 2022

import java.util.List;
import java.io.*;

public class Exp1 {
    
    //Experiment 1: Validation
    public static void main(String[] args) {

        //Checks for argument length
        if (args.length != 6){
            System.out.println("Error: Invalid number of arguments for main (" + String.valueOf(args.length) + "/6)");
            return;
        }

        //Assigns proper variables to relative arguments
        String speed = args[0].toLowerCase(), input_eps = args[1], filename = args[2];
        String xString = args[3], yString = args[4], zString = args[5];

        //Argument Validation checks

        if(!speed.equals("lin") && !speed.equals("kd")){
            System.out.println("Error [args 0]: Invalid search method.");
            return;
        }

        if(!isDouble(input_eps)){
            System.out.println("Error [args 1]: Invalid epsilon value.");
            return;
        }

        File file = new File("./" + filename);
        if (!file.isFile()){
            System.out.println("Error [args 2]: Invalid File name/directory.");
            return;
        }

        if(!isDouble(xString) || !isDouble(yString) || !isDouble(zString)){
            System.out.println("Error [args 3 => 5]: Invalid query point sequence in main.");
            return;
        }
        
        //Assigning method to be used
        boolean FAST = (speed.equals("lin"))?false:true;
        double eps = Double.parseDouble(input_eps), x = Double.parseDouble(xString), y = Double.parseDouble(yString), z = Double.parseDouble(zString);

        //Main test function to be used and then displayed
        List<Point3D> output = validation(FAST, eps, filename, x, y, z);
        display(output);
    }

    //Main function to be used with argument inputs
    public static List<Point3D> validation(boolean FAST, double eps, String filename, double x, double y, double z){
        //Reads File
        List<Point3D> points = DBScan.read("./" + filename);
        Point3D p = new Point3D(x, y, z);

        //Assigns the neighbor class through interface
        NN input = FAST?new NearestNeighborsKD(points):new NearestNeighbors(points);
        
        //Returns rangeQuery list of points
        return input.rangeQuery(0.05, p);
    }

    //Displays 
    public static void display(List<Point3D> N){
        System.out.println("Number of neighbors found: " + String.valueOf(N.size()));
        for(Point3D p: N){
            System.out.println(p.printPoint());
        }
    }

    //Method to find double validity
    static boolean isDouble(String s){
        if (s == null) {
            return false;
        }
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
     }
}
