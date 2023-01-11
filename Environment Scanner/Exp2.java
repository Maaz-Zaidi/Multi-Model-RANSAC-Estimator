//Maaz Zaidi
//Student Number: 300246507
//Date: December 5, 2022

import java.io.File;
import java.util.List;

public class Exp2 {

    //Experiment 2: Time Computation for KD/Lin
    public static void main(String[] args) {

        //Argument length Validation
        if (args.length != 4){
            System.out.println("Error: Invalid number of arguments for main (" + String.valueOf(args.length) + "/6)");
            return;
        }

        //assigning string inputs
        String speed = args[0].toLowerCase(), input_eps = args[1], filename = args[2], input_step = args[3];

        //Argument checks

        if(!speed.equals("lin") && !speed.equals("kd")){
            System.out.println("Error [args 0]: Invalid search method.");
            return;
        }

        if(!isDouble(input_eps)){
            System.out.println("Error [args 1]: Invalid double value.");
            return;
        }

        File file = new File("./" + filename);
        if (!file.isFile()){
            System.out.println("Error [args 2]: Invalid File name/directory.");
            return;
        }

        if(!isInt(input_step) ){
            System.out.println("Error [args 3]: Invalid integer value.");
            return;
        }

        //Variable assignment for their respective types
        boolean FAST = (speed.equals("lin"))?false:true;
        double eps = Double.parseDouble(input_eps); 
        int step = Integer.parseInt(input_step);

        //Computing runtime for chosen method 
        long avarage = timeComputation(FAST, eps, filename, step)/1000000;

        //Printing results
        String method = FAST?"KD":"Linear";
        System.out.println("Avarage time compiled given file (" + filename + ") for method (" + method + "): " + String.valueOf(avarage) + "ms");
    }

    //Time computation method
    public static long timeComputation(boolean FAST, double eps, String filename, int step){

        //Creating list from file
        List<Point3D> points = DBScan.read("./" + filename);

        //Choosing method to use
        NN input = FAST?new NearestNeighborsKD(points):new NearestNeighbors(points);
        long divisor = 1;
        long duration = 0;

        //Running it for each point
        for(int i=0; i <points.size(); i+=step){
            Point3D pt = points.get(i);

            long startTime = System.nanoTime();
            input.rangeQuery(eps, pt);
            long endTime = System.nanoTime();

            duration += (endTime - startTime); // in milliseconds.
        }
        
        return duration / divisor;
    }

    //Method for double validity
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

     static boolean isInt(String s){
        if (s == null) {
            return false;
        }
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
     }
}
